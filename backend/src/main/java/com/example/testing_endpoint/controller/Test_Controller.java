package com.example.testing_endpoint.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test_Controller {

    @CrossOrigin(origins = "http://localhost:3000") // Allowing CORS from React app running on port 3000
    @GetMapping("/message")
    public String testMessage() {
        return "Hello from Medical Platform!";
    }
}