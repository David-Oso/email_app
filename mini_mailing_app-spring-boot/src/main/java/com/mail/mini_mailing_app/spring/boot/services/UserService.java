package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.VerificationRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.User;
import jakarta.validation.Valid;

public interface UserService {
    String registerUser(RegisterUserRequest request);
    String verifyUser(VerificationRequest verificationRequest);
    AuthenticationResponse login(String email, String poneNumber);

    String resendVerificationToken(String phoneNumber);

    User getUserById(Long userId);
}
