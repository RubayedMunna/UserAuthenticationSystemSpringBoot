package com.example.LoginRegistrationSystem.service;


import com.example.LoginRegistrationSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;



    public void sendVerificationEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("noreply@domain.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/api/auth/confirm-account?token=" + user.getConfirmationToken());

        mailSender.send(mailMessage);
    }
}
