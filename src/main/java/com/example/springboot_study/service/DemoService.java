package com.example.springboot_study.service;

import com.example.springboot_study.entity.Instructor;
import com.example.springboot_study.entity.InstructorDetail;
import com.example.springboot_study.repository.InstructorDetailRepository;
import com.example.springboot_study.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final InstructorRepository instructorRepository;
    private final InstructorDetailRepository instructorDetailRepository;

    @Autowired
    public DemoService(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorDetailRepository = instructorDetailRepository;
    }


    public Instructor saveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setEmail("test@gmail.com");
        instructor.setFirstName("hoge");
        instructor.setLastName("fuga");
        return this.instructorRepository.save(instructor);
    }
}
