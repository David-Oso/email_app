package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.*;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.ApiResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.MailResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.UpdateUserResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.Draft;
import com.mail.mini_mailing_app.spring.boot.data.model.Inbox;
import com.mail.mini_mailing_app.spring.boot.data.model.Sent;
import com.mail.mini_mailing_app.spring.boot.data.model.User;

public interface UserService {
    String registerUser(RegisterUserRequest request);
    String verifyUser(VerificationRequest verificationRequest);
    AuthenticationResponse login(String email, String password);
    User getUserById(Long userId);
    MailResponse sendMail(MailRequest mailRequest);
    MailResponse draftMail(MailRequest mailRequest);
    Inbox getInboxById(long userId, long inboxId);
    Sent getSentMailById(long userId, long sentMailId);
    Draft getDraftedMailById(long userId, long draftId);
    UpdateUserResponse updateUser(UpdateUserRequest request);
    ApiResponse sendResetPasswordSms(String phoneNumber);
    UpdateUserResponse resetPassword(ResetPasswordRequest request);
    String resendVerificationToken(String phoneNumber);
//    String delete();

}
