package com.example.springboot_study.repository;

import com.example.springboot_study.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor,Long> {

    public void deleteById(Long id);
}
