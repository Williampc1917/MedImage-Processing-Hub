package com.example.testing_endpoint.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 50, message = "First name should be between 1 to 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 50, message = "Last name should be between 1 to 50 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;

    // Getters, setters, and other methods...
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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


}