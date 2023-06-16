package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
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
    private RegisterUserRequest registerUserRequest;
    @BeforeEach
    void setUp() {
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setGender(Gender.MALE);
        registerUserRequest.setPassword("password");
        registerUserRequest.setLastName("Temz");
        registerUserRequest.setFirstName("Ema");
        registerUserRequest.setMiddleName("Bolunle");
        registerUserRequest.setPhoneNumber("+2349087678906");
        registerUserRequest.setDateOfBirth("21/03/2000");
    }

    @Test
    void registerUser() {

        String response = userService.registerUser(registerUserRequest);
        String result = """
                An activation token has been sent to you account.
                Please check your phone to input the token.
                """;
        assertThat(response).isEqualTo(result);
    }

    @Test
    void resendVerificationToken() {
    }

    @Test
    void getUserById() {
    }
}