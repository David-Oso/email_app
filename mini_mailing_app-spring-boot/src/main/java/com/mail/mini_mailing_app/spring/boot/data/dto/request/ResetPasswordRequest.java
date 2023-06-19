package com.mail.mini_mailing_app.spring.boot.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils.PASSWORD_REGEX_STRING;
import static com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils.PHONE_NUMBER_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordRequest {
    @NotNull(message = "field phone number cannot be null")
    @NotBlank(message = "field phone number cannot be blank")
    @NotEmpty(message = "field phone number cannot be empty")
    @Pattern(message = "Enter your registered phone number", regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;

    @NotNull(message = "field token cannot be null")
    @NotBlank(message = "field token cannot be blank")
    @NotEmpty(message = "field token cannot be empty")
    private String token;

    @NotNull(message = "field new password cannot be null")
    @NotEmpty(message = "field new password cannot be empty")
    @NotBlank(message = "field new password cannot be blank")
    @Pattern(message = "new password must contain at least " +
            "one uppercase, one lower case one digit and at least one special character", regexp = PASSWORD_REGEX_STRING)
    private String newPassword;
//
    @NotNull(message = "field confirm new password cannot be null")
    @NotEmpty(message = "field confirm new password cannot be empty")
    @NotBlank(message = "field confirm new password cannot be blank")
    @Pattern(message = "password must contain at least " +
            "one uppercase, one lower case one digit and at least one special character", regexp = PASSWORD_REGEX_STRING)
    private String confirmNewPassword;
}
