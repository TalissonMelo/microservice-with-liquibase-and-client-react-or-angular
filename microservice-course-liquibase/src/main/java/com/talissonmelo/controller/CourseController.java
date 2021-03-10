package com.talissonmelo.controller;

import com.talissonmelo.feign.UserFeign;
import com.talissonmelo.model.Transaction;
import com.talissonmelo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private CourseService service;

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
