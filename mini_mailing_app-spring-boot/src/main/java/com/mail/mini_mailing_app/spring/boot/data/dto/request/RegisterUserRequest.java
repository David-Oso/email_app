package com.mail.mini_mailing_app.spring.boot.data.dto.request;

import com.mail.mini_mailing_app.spring.boot.data.model.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import static com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class
RegisterUserRequest {

    @NotNull(message = "field first name cannot be null")
    @NotEmpty(message = "field first name cannot be empty")
    @NotBlank(message = "field first name cannot be blank")
    @Pattern(message = "first name must be only letters", regexp = NAME_REGEX)
    private String firstName;

    @NotNull(message = "field middle name cannot be null")
    @NotEmpty(message = "field middle name cannot be empty")
    @NotBlank(message = "field middle name cannot be blank")
    @Pattern(message = "middle name must be only letters", regexp = NAME_REGEX)
    private String middleName;

    @NotNull(message = "field last name cannot be null")
    @NotEmpty(message = "field last name cannot be empty")
    @NotBlank(message = "field last name cannot be blank")
    @Pattern(message = "last name must be only letters", regexp = NAME_REGEX)
    private String lastName;

    @NotNull(message = "field date of birth cannot be null")
    @NotBlank(message = "field date of birth cannot  be blank")
    @NotEmpty(message = "field date of birth cannote be empty")
//    @Pattern(message = "date must be in the format dd/MM/yyyy", regexp = DATE_OF_BIRTH_REGEX)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    @Past(message = "Date must be in the past")
    private String dateOfBirth;


    @NotNull(message = "field phone number cannot be null")
    @NotEmpty(message = "field phone number cannot be empty")
    @NotBlank(message = "field phone number cannot be blank")
    @Pattern(message = "phone number must be a start with +234", regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;

    @NotNull(message = "field gender cannot be null")
//    @NotEmpty(message = "field gender cannot be empty")
//    @NotBlank(message = "field gender cannot be blank")
    private Gender gender;

    @NotNull(message = "field password cannot be null")
    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    @Pattern(message = "password must contain at least " +
            "one uppercase, one lower case one digit and at least one special character", regexp = PASSWORD_REGEX_STRING)
    private String password;
}
