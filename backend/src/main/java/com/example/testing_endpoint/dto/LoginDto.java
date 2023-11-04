package com.example.testing_endpoint.dto;

/**
 * Data Transfer Object for login requests.
 */
public class LoginDto {
    private String email;
    private String password;

    // Default constructor
    public LoginDto() {
    }

    // Constructor with fields
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "LoginDto{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" + // Never log passwords!
                '}';
    }
}