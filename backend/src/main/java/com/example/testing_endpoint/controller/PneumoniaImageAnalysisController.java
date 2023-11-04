package com.example.testing_endpoint.controller;


import com.example.testing_endpoint.service.PneumoniaImageAnalysisService;
import com.example.testing_endpoint.dto.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController


// RequestMapping annotation defines the base URL for all API endpoints in this controller
@RequestMapping("/api")
public class PneumoniaImageAnalysisController {

    private final PneumoniaImageAnalysisService pneumoniaImageAnalysisService;

    @Autowired
    public PneumoniaImageAnalysisController(PneumoniaImageAnalysisService pneumoniaImageAnalysisService) {
        this.pneumoniaImageAnalysisService = pneumoniaImageAnalysisService;
    }

    //
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<AnalysisResult> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("age") int age) throws IOException {

        // Call the service layer to analyze the image and return the re
        AnalysisResult result = pneumoniaImageAnalysisService.analyzeImage(image, name, age);
        // ResponseEntity is used to create an HTTP 200 OK response with the analysis result in the body
        return ResponseEntity.ok(result);
    }
}