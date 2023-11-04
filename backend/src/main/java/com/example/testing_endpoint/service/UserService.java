package com.example.testing_endpoint.service;

import com.example.testing_endpoint.dto.UserDto;
import com.example.testing_endpoint.model.User;
import com.example.testing_endpoint.exception.ValidationException;
import com.example.testing_endpoint.model.User;
import com.example.testing_endpoint.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean emailExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void validateUser(UserDto userDto) throws ValidationException {
        if (emailExists(userDto.getEmail())) {
            throw new ValidationException("Email already exists!");
        }
        // Add other validation checks as needed...
    }


    // TODO: 9/19/2023 USE SPRING SECURITY FOR HASHING PASSWORD
    public User saveUser(UserDto userDto) throws ValidationException {
        //First check if the user/email already exist
        validateUser(userDto);

        // Convert UserDto to User
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //Hash the password bcrypt
        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());

        //set user password to hashed
        user.setHashedPassword(hashedPassword);

        //save user information to the database
        return userRepository.save(user);



    }
}