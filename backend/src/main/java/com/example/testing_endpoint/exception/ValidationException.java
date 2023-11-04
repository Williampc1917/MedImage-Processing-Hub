package com.example.testing_endpoint.exception;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    // You can also add more constructors if needed
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}