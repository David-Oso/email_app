package com.mail.mini_mailing_app.spring.boot.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailRequest {
    private String email;
    private String subject;
    private String messageBody;
}
