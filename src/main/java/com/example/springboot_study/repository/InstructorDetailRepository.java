package com.example.springboot_study.repository;

import com.example.springboot_study.entity.InstructorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorDetailRepository extends JpaRepository<InstructorDetail,Long> {
}
