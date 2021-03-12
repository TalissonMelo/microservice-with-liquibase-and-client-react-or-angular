package com.talissonmelo.service;

import com.talissonmelo.model.Course;
import com.talissonmelo.model.Transaction;
import com.talissonmelo.repository.CourseReposiotry;
import com.talissonmelo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements  CourseService{

    @Autowired
    private CourseReposiotry reposiotry;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Course> allCourses() {
        return reposiotry.findAll();
    }

    @Override
    public Course findCourseById(Long courseId) {
        return reposiotry.findById(courseId).orElse(null);
    }

    @Override
    public List<Transaction> findTransactionsOfUser(Long userId) {
        return transactionRepository.findAllByCourseId(userId);
    }

    @Override
    public List<Transaction> findTransactionsOfCourse(Long courseId) {
        return transactionRepository.findAllByCourseId(courseId);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
