package com.mail.mini_mailing_app.spring.boot.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterAdminRequest {
    @NotNull(message = "field first name cannot be null")
    @NotEmpty(message = "field first name cannot be empty")
    @NotBlank(message = "field first name cannot be blank")
    @Pattern(message = "first name must be only letters", regexp = NAME_REGEX)
    private String firstName;

    @NotNull(message = "field last name cannot be null")
    @NotEmpty(message = "field last name cannot be empty")
    @NotBlank(message = "field last name cannot be blank")
    @Pattern(message = "last name must be only letters", regexp = NAME_REGEX)
    private String lastName;

    @NotNull(message = "field phone number cannot be null")
    @NotEmpty(message = "field phone number cannot be empty")
    @NotBlank(message = "field phone number cannot be blank")
    @Pattern(message = "phone number must be a start with +234", regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;

    @NotNull(message = "field password cannot be null")
    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    @Pattern(message = "password must contain at least " +
            "one uppercase, one lower case one digit and at least one special character", regexp = PASSWORD_REGEX_STRING)
    private String password;
}
