package com.mail.mini_mailing_app.spring.boot.services;


import com.mail.mini_mailing_app.spring.boot.data.dto.request.AdminLoginRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;

public interface AdminService {
    AuthenticationResponse login(AdminLoginRequest request);

}
