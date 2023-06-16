package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
import com.mail.mini_mailing_app.spring.boot.data.model.User;

public interface UserService {
    String registerUser(RegisterUserRequest request);

    String resendVerificationToken(String phoneNumber, String message);

    User getUserById(Long userId);
}
