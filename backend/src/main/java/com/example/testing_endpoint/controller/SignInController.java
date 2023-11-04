package com.example.testing_endpoint.controller;

import com.example.testing_endpoint.dto.LoginDto;
import com.example.testing_endpoint.dto.UserDto;
import com.example.testing_endpoint.exception.ValidationException;
import com.example.testing_endpoint.model.User;
import com.example.testing_endpoint.service.AuthenticationService;
import com.example.testing_endpoint.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

// Class to handle user sign-in requests
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class SignInController {
    @Autowired
    private AuthenticationService authenticationService; // Service to handle authentication logic.


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            // Attempting to authenticate the user with the provided credentials.
            String token = authenticationService.authenticate(loginDto.getEmail(), loginDto.getPassword());
            // Preparing the response object with the token and a success message.
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login successful"); // Confirmation message of successful login.
            return ResponseEntity.ok(response);
        } catch (AuthenticationService.AuthenticationException e) {
            // Preparing an error response object with the exception message.
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());// The error message describing why authentication failed.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}