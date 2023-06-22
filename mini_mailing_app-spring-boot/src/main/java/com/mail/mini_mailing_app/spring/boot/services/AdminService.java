package com.mail.mini_mailing_app.spring.boot.services;


import com.mail.mini_mailing_app.spring.boot.data.dto.request.AdminLoginRequest;

public interface AdminService {
    String login(AdminLoginRequest request);

}
