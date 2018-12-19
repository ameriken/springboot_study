package com.example.springboot_study.controller;

import com.example.springboot_study.entity.Instructor;
import com.example.springboot_study.entity.InstructorDetail;
import com.example.springboot_study.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sample")
public class DemoController {

    private DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("")
    public String getDataAccess() {
        try {
            this.demoService.saveInstructor();
        } catch(Exception e) {
            return "false";
        }
        return "true";
    }

    @GetMapping("/delete")
    public String deleteDataAccess() {
        try {
            return this.demoService.deleteInstructor();
        } catch(Exception e) {
            return "false";
        }
    }

    @GetMapping("/delete/instructorDetail")
    public String deleteInstructorDetailDataAccess() {
        try {
            return this.demoService.deleteInstructorDetail();
        } catch(Exception e) {
            return "false";
        }
    }

    @GetMapping("/get")
    public Optional<InstructorDetail> getInstructorDetailDataAccess() {
        try {
            return this.demoService.getInstructorDetail();
        } catch(Exception e) {
            return null;
        }
    }
    @GetMapping("/course")
    public String registerCourseDataAccess() {
        try {
            this.demoService.registerCourse();
        } catch(Exception e) {
            return "false Course";
        }
        return "true course";
    }

    @GetMapping("/delete/course")
    public String deleteCourseDataAccess() {
        try {
            this.demoService.deleteCourse();
        } catch(Exception e) {
            return "false Course";
        }
        return "true course";
    }

    @GetMapping("/review")
    public String reviewDataAccess() {
        try {
            this.demoService.reviewCourse();
        } catch(Exception e) {
            return "false Review";
        }
        return "true Review";
    }
    @GetMapping("/eagar_lazy")
    public String eagarLazy() {
        try {
            List<Instructor> inst = this.demoService.getInstructor();
        } catch(Exception e) {
            return "false Review";
        }
        return "true Review";
    }

    @GetMapping("/course_student")
    public String registerCourseStudentDataAccess() {
        try {
            this.demoService.registerCourseStudent();
        } catch(Exception e) {
            return "false Course";
        }
        return "true course";
    }

    @GetMapping("/course_student_other")
    public String registerCourseStudentOtherDataAccess() {
        try {
            this.demoService.registerCourseStudentOther();
        } catch(Exception e) {
            return "false Course";
        }
        return "true course";
    }
}
