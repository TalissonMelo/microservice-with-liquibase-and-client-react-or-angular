package com.talissonmelo.controller;

import com.talissonmelo.feign.UserFeign;
import com.talissonmelo.model.Transaction;
import com.talissonmelo.service.CourseService;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private CourseService service;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment environment;

    @Value("${spring.application.name}")
    private String nameMicroservice;

    @GetMapping(value = "/port")
    public String getPort() {
        return "Serviço rodando na Porta: " + environment.getProperty("local.server.port");
    }

    @GetMapping(value = "/instances")
    public ResponseEntity<?> getIntances(){
        return ResponseEntity.ok(discoveryClient.getInstances(nameMicroservice));
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> findTransactionsOfUser(@PathVariable Long userId){
        return ResponseEntity.ok().body(service.findTransactionsOfUser(userId));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAllCourses(){
        return ResponseEntity.ok().body(service.allCourses());
    }

    @PostMapping(value = "/enroll")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction){
        transaction.setDateOfIssue(LocalDateTime.now());
        transaction.setCourse(service.findCourseById(transaction.getCourse().getId()));
        return new ResponseEntity<>(service.saveTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping(value = "/course/{courseId}")
    public ResponseEntity<?> findStudentsOfCourse(@PathVariable Long courseId){
        List<Transaction> transactions = service.findTransactionsOfCourse(courseId);
        if(CollectionUtils.isEmpty(transactions)){
            return ResponseEntity.notFound().build();
        }

        List<Long> userIdList = transactions.parallelStream().map(t -> t.getUserId()).collect(Collectors.toList());
        List<String> students = userFeign.getUserNames(userIdList);
        return ResponseEntity.ok().body(students);
    }


}
