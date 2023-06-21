package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterAdminRequest;

public interface AdminService {
    String registerAdmin(RegisterAdminRequest request);
}
