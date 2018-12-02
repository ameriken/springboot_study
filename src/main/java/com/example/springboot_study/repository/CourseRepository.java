package com.example.springboot_study.repository;

import com.example.springboot_study.entity.Course;
import com.example.springboot_study.entity.InstructorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
