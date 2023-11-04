package com.example.testing_endpoint.service;

import com.example.testing_endpoint.dto.AnalysisResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.IOException;

@CrossOrigin
@Service
public class PneumoniaImageAnalysisService {

    //model api url
    private final String MODEL_API_URL = "http://127.0.0.1:5000/predict";
    public AnalysisResult analyzeImage(MultipartFile image, String name, int age) throws IOException {
        // Create a RestTemplate object to make HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        // Set up HTTP headers for the request. The content type is multipart/form-data since we're sending an image.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create a temporary file to store the MultipartFile
        File tempFile = File.createTempFile("uploaded-", ".tmp");
        image.transferTo(tempFile);

        // Prepare the request payload (body). In this case, it's the image file.
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(tempFile));

        // Create an HTTP Entity object which includes both the headers and the payload.
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Make an HTTP POST request to the Flask API using RestTemplate.
        // Expect a response of type AnalysisResult.
        ResponseEntity<AnalysisResult> response = restTemplate.postForEntity(MODEL_API_URL, requestEntity, AnalysisResult.class);

        // Delete the temporary file
        tempFile.delete();


        // Return the body of the response, which should be the analysis result.
        return response.getBody();

    }

}
