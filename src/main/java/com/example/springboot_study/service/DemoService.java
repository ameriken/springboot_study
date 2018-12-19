package com.example.springboot_study.service;

import com.example.springboot_study.entity.*;
import com.example.springboot_study.repository.CourseRepository;
import com.example.springboot_study.repository.InstructorDetailRepository;
import com.example.springboot_study.repository.InstructorRepository;
import com.example.springboot_study.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemoService {

    private final InstructorRepository instructorRepository;
    private final InstructorDetailRepository instructorDetailRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public DemoService(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository,CourseRepository courseRepository, StudentRepository studentRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorDetailRepository = instructorDetailRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
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

    public Course registerCourseStudent() {

        Course tmpCourse = new Course("Air Guitar - The Ultimate Guide");
        Student tempStudent1 = new Student("John", "Doe", "jon@luv2code.com");
        Student tempStudent2 = new Student("Mary", "public", "mary@luv2code.com");

        tmpCourse.addStudent(tempStudent1);
        tmpCourse.addStudent(tempStudent2);

        return this.courseRepository.save(tmpCourse);

    }

    public Course registerCourseStudentOther() {

        Student st = this.studentRepository.getOne(16);

        Course tmpCourse1 = new Course("Ribiki's Cube - how to speed cube");
        Course tmpCourse2 = new Course("Atari 2600 - game development");

        Student tempStudent1 = new Student("John", "Doe", "jon@luv2code.com");
        Student tempStudent2 = new Student("Mary", "public", "mary@luv2code.com");

        tmpCourse1.addStudent(st);
        tmpCourse2.addStudent(st);

        this.courseRepository.save(tmpCourse1);
        return this.courseRepository.save(tmpCourse2);

    }
}
