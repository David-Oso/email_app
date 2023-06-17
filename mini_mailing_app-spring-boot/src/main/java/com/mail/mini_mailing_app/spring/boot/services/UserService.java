package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.MailRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.VerificationRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.MailResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.Draft;
import com.mail.mini_mailing_app.spring.boot.data.model.Inbox;
import com.mail.mini_mailing_app.spring.boot.data.model.Sent;
import com.mail.mini_mailing_app.spring.boot.data.model.User;

public interface UserService {
    String registerUser(RegisterUserRequest request);
    String verifyUser(VerificationRequest verificationRequest);
    AuthenticationResponse login(String email, String poneNumber);
    MailResponse sendMail(MailRequest mailRequest);
    MailResponse draftMail(MailRequest mailRequest);
    Inbox getInboxById(long userId, long mailId);
    Sent getSentMailById(long userId, long mailId);
    Draft getDraftedMailById(long userId, long mailId);

    String resendVerificationToken(String phoneNumber);

    User getUserById(Long userId);
}
