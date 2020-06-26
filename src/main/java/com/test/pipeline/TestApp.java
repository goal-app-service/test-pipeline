package com.test.pipeline;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TestApp {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot12!";
    }

    }
