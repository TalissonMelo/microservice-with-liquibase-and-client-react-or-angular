package com.talissonmelo.service;

import com.talissonmelo.model.Course;
import com.talissonmelo.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseService {

    List<Course> allCourses();
    Course findCourseById(Long courseId);
    List<Transaction> findTransactionsOfUser(Long userId);
    List<Transaction> findTransactionsOfCourse(Long courseId);
    Transaction saveTransaction(Transaction transaction);
}
