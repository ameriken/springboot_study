package com.example.springboot_study.controller;

import com.example.springboot_study.entity.InstructorDetail;
import com.example.springboot_study.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
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
}
