package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.RegisterResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterUserRequest request);
}
