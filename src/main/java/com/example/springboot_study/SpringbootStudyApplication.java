package com.example.springboot_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootStudyApplication {


	@RequestMapping("/")
	public String home() {
		return "Hello test";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootStudyApplication.class, args);
	}
}
