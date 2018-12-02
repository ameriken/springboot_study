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

        InstructorDetail instructorDetail = new InstructorDetail();
        instructorDetail.setHobby("game");
        instructorDetail.setYoutubeChannel("kanekinfitness");

        Instructor instructor = new Instructor();
        instructor.setEmail("hogehoge@gmail.com");
        instructor.setFirstName("hogehoge");
        instructor.setLastName("fugafuga");
        instructor.setInstructorDetail(instructorDetail);

        return this.instructorRepository.save(instructor);
    }
}
