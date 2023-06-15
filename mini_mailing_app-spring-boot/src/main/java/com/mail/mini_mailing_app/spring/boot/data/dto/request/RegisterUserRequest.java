package com.mail.mini_mailing_app.spring.boot.data.dto.request;

import com.mail.mini_mailing_app.spring.boot.data.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private Gender gender;
    private String email;
    private String password;
}
