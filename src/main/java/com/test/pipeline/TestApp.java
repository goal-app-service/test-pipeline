package com.test.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot12!";
    }

    }
