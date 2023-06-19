package com.mail.mini_mailing_app.spring.boot.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils.EMAIL_REGEX_STRING;
import static com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils.NAME_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    @NotNull(message = "field user id cannot be null")
    private Long userId;

    @Pattern(message = "first name must be only letters", regexp = NAME_REGEX)
    private String firstName;

    @Pattern(message = "middle name must be only letters", regexp = NAME_REGEX)
    private String middleName;

    @Pattern(message = "last name must be only letters", regexp = NAME_REGEX)
    private String lastName;
}