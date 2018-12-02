package com.example.springboot_study.service;

import com.example.springboot_study.entity.Instructor;
import com.example.springboot_study.entity.InstructorDetail;
import com.example.springboot_study.repository.InstructorDetailRepository;
import com.example.springboot_study.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        instructorDetail.setHobby("kintore");
        instructorDetail.setYoutubeChannel("amekenfitness");

        Instructor instructor = new Instructor();
        instructor.setEmail("fuwafuwa@gmail.com");
        instructor.setFirstName("fuwafuwa");
        instructor.setLastName("fugafuwa");
        instructor.setInstructorDetail(instructorDetail);

        return this.instructorRepository.save(instructor);
    }

    public String deleteInstructor() {
        this.instructorRepository.deleteById(6L);
        return "deleted";
    }

    public Optional<InstructorDetail> getInstructorDetail() {
        Optional<InstructorDetail> instructorDetail = this.instructorDetailRepository.findById(1L);
        return instructorDetail;

    }
}
