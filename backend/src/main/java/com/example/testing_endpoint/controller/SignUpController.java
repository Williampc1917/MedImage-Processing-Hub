package com.example.testing_endpoint.controller;

import com.example.testing_endpoint.dto.UserDto;
import com.example.testing_endpoint.exception.ValidationException;
import com.example.testing_endpoint.model.User;
import com.example.testing_endpoint.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class SignUpController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDto user) {
        // Validate user details, check if email already exists, etc.
        try {
            // Attempt to save the new user's information.
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        }catch (ValidationException ve){
            return ResponseEntity.badRequest().body(ve.getMessage());
        }

    }

    // Handle the case where argument validation fails.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Create a map to store field-specific error messages
        Map<String, String> errors = new HashMap<>();

        // Iterate over the field errors, extract field names and corresponding error messages.
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Return the error map with an HTTP 400 Bad Request status.
        return ResponseEntity.badRequest().body(errors);



    }


}