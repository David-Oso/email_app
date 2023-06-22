package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.AdminLoginRequest;
import com.mail.mini_mailing_app.spring.boot.data.model.Admin;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.Role;
import com.mail.mini_mailing_app.spring.boot.data.repository.AdminRepository;
import com.mail.mini_mailing_app.spring.boot.services.AdminService;
import com.mail.mini_mailing_app.spring.boot.services.AppUserService;
import com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AppUserService appUserService;

    @Value("${adminPassword}")
    private String adminPassword;

//    @PostConstruct
    private void registerAdmin(){
        AppUser appUser = AppUser.builder()
                .email(MailAppUtils.APP_EMAIL)
                .firstName("Email")
                .lastName("App")
                .isBlocked(false)
                .isEnabled(true)
                .phoneNumber("+2348045342389")
                .password(adminPassword)
                .role(Role.ADMIN)
                .build();
        Admin admin = new Admin();
        admin.setUserDetails(appUser);
        admin.setIdentity("MailApp_101");
        adminRepository.save(admin);
    }

    @Override
    public String login(AdminLoginRequest request) {
        return null;
    }
}
