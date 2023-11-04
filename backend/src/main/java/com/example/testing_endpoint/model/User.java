package com.example.testing_endpoint.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


//user signup information (full name, email, password)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 50, message = "First name should be between 1 to 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 50, message = "Last name should be between 1 to 50 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String hashedPassword;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters and Setters for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getters and Setters for hashedPassword
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

}