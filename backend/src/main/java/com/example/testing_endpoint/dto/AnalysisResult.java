package com.example.testing_endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class AnalysisResult {

    // This field holds the result of the analysis, e.g., whether a condition was detected or not.
    @JsonProperty("result")
    private String result;

    // This field represents the specific diagnosis that was determined by the analysis.
    @JsonProperty("diagnosis")
    private String diagnosis;

    //percentage indicating how reliable the diagnosis is.
    @JsonProperty("confidence")
    private double confidence;

    public AnalysisResult() {}

    // Parameterized constructor to create instances of this class with specified values.
    public AnalysisResult(String result, String diagnosis, double confidence) {
        this.result = result;
        this.diagnosis = diagnosis;
        this.confidence = confidence;
    }

}
