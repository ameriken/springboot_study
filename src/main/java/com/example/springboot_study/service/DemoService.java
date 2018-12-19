package com.example.springboot_study.service;

import com.example.springboot_study.entity.Course;
import com.example.springboot_study.entity.Instructor;
import com.example.springboot_study.entity.InstructorDetail;
import com.example.springboot_study.entity.Review;
import com.example.springboot_study.repository.CourseRepository;
import com.example.springboot_study.repository.InstructorDetailRepository;
import com.example.springboot_study.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DemoService {

    private final InstructorRepository instructorRepository;
    private final InstructorDetailRepository instructorDetailRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public DemoService(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository,CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorDetailRepository = instructorDetailRepository;
        this.courseRepository = courseRepository;
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

    public String deleteInstructorDetail() {
        InstructorDetail detail = this.instructorDetailRepository.findById(2L).get();
        detail.getInstructor().setInstructorDetail(null);
        this.instructorDetailRepository.deleteById(2L);
        return "deleted";
    }

    public Instructor registerCourse() {
        Instructor instructor = this.instructorRepository.findById(1L).get();

        Course tmpCourse1 = new Course("Air Guitar - The Ultimate Guide");
        Course tmpCourse2 = new Course("The Pinball - Masterclass");

        instructor.add(tmpCourse1);
        instructor.add(tmpCourse2);

        return this.instructorRepository.save(instructor);

    }

    public List<Instructor> getInstructor() {

        List<Instructor> instructor = this.instructorRepository.findAllById(1L);
        return instructor;

    }

    public String deleteCourse() {
        this.courseRepository.deleteById(6L);
        return "deleted";
    }

    public String reviewCourse() {

        Course tmpCourse = new Course("Pacman - How To Score One Million Points");
        tmpCourse.addReview(new Review("GREAT!!!"));
        tmpCourse.addReview(new Review("COOL!!!"));
        tmpCourse.addReview(new Review("AWESOMME!!!"));
        this.courseRepository.save(tmpCourse);
        return "success";
    }
}
