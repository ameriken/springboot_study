package com.example.springboot_study.repository;

import com.example.springboot_study.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
