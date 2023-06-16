package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.response.ApiResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AppUserService {
    ApiResponse uploadProfileImage(MultipartFile file);
    ApiResponse verifyAccountByEmail(String email);
    AppUser getUserByEmail(String email);
    Optional<AppUser> getUserBy(String email);
    AppUser getUserByPhoneNumber(String phoneNumber);
    Optional<AppUser> getBy(String phoneNumber);
}
