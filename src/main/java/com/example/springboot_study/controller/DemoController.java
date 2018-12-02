package com.example.springboot_study.controller;

import com.example.springboot_study.entity.InstructorDetail;
import com.example.springboot_study.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        }
        return "true";
    }
}
