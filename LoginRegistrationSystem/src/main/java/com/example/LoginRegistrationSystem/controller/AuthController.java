package com.example.LoginRegistrationSystem.controller;


import com.example.LoginRegistrationSystem.model.User;
import com.example.LoginRegistrationSystem.service.EmailService;
import com.example.LoginRegistrationSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email");
        }

        if (isWeakPassword(user.getPassword())) {
            return ResponseEntity.badRequest().body("Weak password");
        }

        User registeredUser = userService.registerUser(user);
        emailService.sendVerificationEmail(registeredUser);
        return ResponseEntity.ok("Registration successful, please check your email for verification.");
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        User user = userService.findByConfirmationToken(confirmationToken);
        if (user != null) {
            userService.enableUser(user);
            return ResponseEntity.ok("Account verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid confirmation token");
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isWeakPassword(String password) {
        return password.length() < 8;
    }
}
