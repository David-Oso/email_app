package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.SmsRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.ApiResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.repository.AppUserRepository;
import com.mail.mini_mailing_app.spring.boot.exception.NotFoundException;
import com.mail.mini_mailing_app.spring.boot.services.AppUserService;
import com.mail.mini_mailing_app.spring.boot.twilio.SmsSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final SmsSender smsSender;
    @Override
    public ApiResponse uploadProfileImage(MultipartFile file) {
        return null;
    }

    @Override
    public ApiResponse verifyAccountByEmail(String email) {
        return null;
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("App User with this email not found"));
    }

    @Override
    public Optional<AppUser> getUserBy(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public AppUser getUserByPhoneNumber(String phoneNumber) {
        return appUserRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()-> new NotFoundException("User with this phone number not found"));
    }

    @Override
    public Optional<AppUser> getBy(String phoneNumber) {
        return appUserRepository.findByPhoneNumber(phoneNumber);
    }
    @Override
    public void sendSms(String phoneNumber, String message) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setRecipientPhoneNumber(phoneNumber);
        smsRequest.setMessage(message);
        smsSender.sendSms(smsRequest);
    }
}
