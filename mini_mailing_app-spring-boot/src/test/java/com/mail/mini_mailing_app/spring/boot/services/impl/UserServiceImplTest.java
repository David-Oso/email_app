package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.*;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.ApiResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.MailResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.UpdateUserResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.*;
import com.mail.mini_mailing_app.spring.boot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils.TEST_IMAGE_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired UserService userService;
    private RegisterUserRequest registerUserRequest1;
    private RegisterUserRequest registerUserRequest2;
    private VerificationRequest verificationRequest1;
    private VerificationRequest verificationRequest2;
    private MailRequest mailRequest;
    private MailRequest draftRequest;
    private UpdateUserRequest updateuserRequest;
    private ResetPasswordRequest resetPasswordRequest;
    @BeforeEach
    void setUp() {
        registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setGender(Gender.MALE);
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setLastName("Temz");
        registerUserRequest1.setFirstName("Ema");
        registerUserRequest1.setMiddleName("Bolunle");
        registerUserRequest1.setPhoneNumber("+2349030400837");
        registerUserRequest1.setDateOfBirth("21/03/2000");

        registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setGender(Gender.FEMALE);
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstName("Tolani");
        registerUserRequest2.setLastName("Esther");
        registerUserRequest2.setMiddleName("Glory");
        registerUserRequest2.setPhoneNumber("+2349009876567");
        registerUserRequest2.setDateOfBirth("21/07/2002");

        verificationRequest1 = new VerificationRequest();
        verificationRequest1.setPhoneNUmber("+2349030400837");
        verificationRequest1.setVerificationToken("-qwaoeGZ");
        verificationRequest1.setEmail("ematemz001@gmail.com");

        verificationRequest2 = new VerificationRequest();
        verificationRequest2.setPhoneNUmber("+2349009876567");
        verificationRequest2.setVerificationToken("THqh2CMA");
        verificationRequest2.setEmail("tolaniesther112@gmail.com");

        mailRequest = new MailRequest();
        mailRequest.setUserId(1L);
        mailRequest.setEmail("tolaniesther112@gmail.com");
        mailRequest.setSubject("Testing Mail");
        mailRequest.setMessageBody("I am testing whether this mail will be sent");

        draftRequest = new MailRequest();
        draftRequest.setUserId(2L);
        draftRequest.setSubject("Drafting a mail");
        draftRequest.setMessageBody("Testing from my app on draft message");

        updateuserRequest = new UpdateUserRequest();
        updateuserRequest.setUserId(1L);
        updateuserRequest.setFirstName("UpdatedFirstName");
        updateuserRequest.setLastName("UpdatedLastName");
        updateuserRequest.setMiddleName("UpdatedMiddleName");
        updateuserRequest.setImage(uploadImage(TEST_IMAGE_LOCATION));
        updateuserRequest.setPassword("password");

        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setToken("KZ3IhWMU");
        resetPasswordRequest.setPhoneNumber("+2349030400837");
        resetPasswordRequest.setNewPassword("newPassword1");
        resetPasswordRequest.setConfirmNewPassword("newPassword1");
    }

    private MultipartFile uploadImage(String imageLocation){
        try{
        MultipartFile file = new MockMultipartFile("test_image",
                new FileInputStream(imageLocation));
        return file;
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }


    @Test
    void registerUser() {
        String response1 = userService.registerUser(registerUserRequest1);
        String response2 = userService.registerUser(registerUserRequest2);
        String result = """
                An activation token has been sent to you account.
                Please check your phone to input the token.
                """;
        assertThat(response1).isEqualTo(result);
        assertThat(response2).isEqualTo(result);
    }

    @Test
    void verify() {
        String response1 = userService.verifyUser(verificationRequest1);
        String response2 = userService.verifyUser(verificationRequest2);

        assertThat(response1).isEqualTo("Verification Successful");
        assertThat(response2).isEqualTo("Verification Successful");
    }

    @Test
    void loginTest(){
        AuthenticationResponse response = userService.login("ematemz001@gmail.com", "password");
        assertThat(response.getMessage()).isEqualTo("Authentication Successful");

    }

    @Test
    void sendMailTest(){
        MailResponse response = userService.sendMail(mailRequest);
        assertThat(response.getMessage()).isEqualTo("Mail sent successfully");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void draftMailTest(){
        MailResponse response = userService.draftMail(draftRequest);
        assertThat(response.getMessage()).isEqualTo("Mail drafted successfully");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void getInboxByIdTest(){
        Inbox inbox = userService.getInboxById(2L, 1L);
        assertThat(inbox.getMessage().getSubject()).isEqualTo("Testing Mail");
    }

    @Test
    void getSentByIdTest(){
        Sent sent = userService.getSentMailById(1L, 1L);
        assertThat(sent.getMessage().getSubject()).isEqualTo("Testing Mail");
    }

    @Test
    void getDraftByIdTest(){
        Draft draft = userService.getDraftedMailById(2L, 1L);
        assertThat(draft.getMessage().getSubject()).isEqualTo("Drafting a mail");
    }

    @Test
    void getUserById() {
        User user = userService.getUserById(1L);
        assertThat(user.getUserDetails().getFirstName()).isEqualTo(registerUserRequest1.getFirstName());
    }

    @Test
    void updateUserTest(){
        UpdateUserResponse response = userService.updateUser(updateuserRequest);
        assertThat(response.getMessage()).isEqualTo("User Update Successful");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void sentResetPasswordSmsTest(){
        ApiResponse response = userService.sendResetPasswordSms("+2349030400837");
        assertThat(response.getMessage()).isEqualTo("Check your phone for the token to reset your password");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void resetPasswordTest(){
        UpdateUserResponse response = userService.resetPassword(resetPasswordRequest);
        assertThat(response.getMessage()).isEqualTo("Password changed successfully");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void deleteInboxByIdTest(){
        Long inboxCount = userService.inboxCount();
        assertThat(inboxCount).isEqualTo(2L);
        userService.deleteInboxById(2L, 1L);
        assertThat(inboxCount).isEqualTo(1L);
    }

    @Test
    void deleteSentMessageByIdTest(){
        Long sentCount = userService.sentCount();
        assertThat(sentCount).isEqualTo(2L);
        userService.deleteSentMailById(1L, 1L);
        assertThat(sentCount).isEqualTo(1L);
    }

    @Test
    void deleteDraftByIdTest(){
        Long draftCount = userService.draftCount();
        assertThat(draftCount).isEqualTo(2L);
        userService.deleteDraftById(2L, 1L);
        assertThat(draftCount).isEqualTo(1L);
    }
    
    @Test
    void deleteAllInboxTest(){
        userService.deleteAllInbox(2L);
        assertThat(userService.inboxCount()).isEqualTo(0L);
    }

    @Test
    void deleteAllSentTest(){
        userService.deleteAllSent(1L);
        assertThat(userService.sentCount()).isEqualTo(0L);
    }
    
    @Test
    void deleteAllDraftsTest(){
        userService.deleteAllDrafts(2L);
        assertThat(userService.draftCount()).isEqualTo(0L);
    }
}
