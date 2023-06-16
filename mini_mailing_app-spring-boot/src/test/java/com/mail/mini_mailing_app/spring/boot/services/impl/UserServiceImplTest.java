package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.VerificationRequest;
import com.mail.mini_mailing_app.spring.boot.data.model.Gender;
import com.mail.mini_mailing_app.spring.boot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired UserService userService;
    private RegisterUserRequest registerUserRequest1;
    private RegisterUserRequest registerUserRequest2;
    private VerificationRequest verificationRequest1;
    private VerificationRequest verificationRequest2;
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
        verificationRequest1.setVerificationToken("NZr_ldqz");
        verificationRequest1.setEmail("ematemz001@gmail.com");

        verificationRequest2 = new VerificationRequest();
        verificationRequest2.setPhoneNUmber("+2349009876567");
        verificationRequest2.setVerificationToken("pRPCpMg1");
        verificationRequest2.setEmail("tolaniesther112@gmail.com");
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
//        String response1 = userService.verifyUser(verificationRequest1);
        String response2 = userService.verifyUser(verificationRequest2);

//        assertThat(response1).isEqualTo("Verification Successful");
        assertThat(response2).isEqualTo("Verification Successful");
    }

    @Test
    void getUserById() {
    }
}