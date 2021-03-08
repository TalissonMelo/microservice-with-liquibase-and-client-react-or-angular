package com.talissonmelo.repository;

import com.talissonmelo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReposiotry extends JpaRepository<Course, Long> {
}
