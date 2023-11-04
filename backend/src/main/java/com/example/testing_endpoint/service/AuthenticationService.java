package com.example.testing_endpoint.service;

import com.example.testing_endpoint.repository.UserRepository;
import com.example.testing_endpoint.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID; // Used for generating a simple session token

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public String authenticate(String email, String password) throws AuthenticationException {
        // Check if user exists by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User not found with email: " + email));

        // Verify that the hashed password matches
        if (!BCrypt.checkpw(password, user.getHashedPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        // Generate a token or session key to return to the client
        // Here we're generating a simple UUID token for testing purposes
        // In a real application, you'd generate a JWT or another secure token
        return UUID.randomUUID().toString();
    }

    // Exception class for handling authentication related exceptions
    public static class AuthenticationException extends Exception {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}