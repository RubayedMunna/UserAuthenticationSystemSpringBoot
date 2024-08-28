package com.example.LoginRegistrationSystem.service;


import com.example.LoginRegistrationSystem.model.User;
import com.example.LoginRegistrationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmationToken(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public User findByConfirmationToken(String token) {
        return userRepository.findByConfirmationToken(token);
    }

    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}
