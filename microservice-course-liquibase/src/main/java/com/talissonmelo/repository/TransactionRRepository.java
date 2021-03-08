package com.talissonmelo.repository;

import com.talissonmelo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUserId(Long userId);
    List<Transaction> findAllByCourseId(Long courseId);
}
