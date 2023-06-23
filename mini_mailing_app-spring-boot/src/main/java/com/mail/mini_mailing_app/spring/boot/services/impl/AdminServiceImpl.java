package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.AdminLoginRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.Admin;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.Role;
import com.mail.mini_mailing_app.spring.boot.data.repository.AdminRepository;
import com.mail.mini_mailing_app.spring.boot.services.AdminService;
import com.mail.mini_mailing_app.spring.boot.services.AppUserService;
import com.mail.mini_mailing_app.spring.boot.services.JwtTokenService;
import com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Value("${adminPassword}")
    private String adminPassword;
    @Value("${adminPhoneNumber}")
    private String adminPhoneNumber;

//    @PostConstruct
    private void registerAdmin(){
        String encodedPassword = passwordEncoder.encode(adminPassword);
        AppUser appUser = AppUser.builder()
                .email(MailAppUtils.APP_EMAIL)
                .firstName("Email")
                .lastName("App")
                .isBlocked(false)
                .isEnabled(true)
                .phoneNumber(adminPhoneNumber)
                .password(encodedPassword)
                .role(Role.ADMIN)
                .build();
        Admin admin = new Admin();
        admin.setUserDetails(appUser);
        admin.setIdentity("MailApp_101");
        adminRepository.save(admin);
    }


    @Override
    public AuthenticationResponse login(AdminLoginRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            AppUser appUser = appUserService.getUserByEmail(request.getEmail());
            Admin admin = adminRepository.findByUserDetails(appUser).orElse(null);
            if(admin != null && admin.getIdentity().equals(request.getIdentity())){
                String message = "Authentication Successful";
                return jwtTokenService.getAuthenticationResponse(appUser, message);
            }
        }catch (AuthenticationException exception){
            throw new RuntimeException("Incorrect password", exception);
        }
        throw new RuntimeException("Authentication Failure");
    }
}
