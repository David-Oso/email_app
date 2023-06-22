package com.mail.mini_mailing_app.spring.boot.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminLoginRequest {
    private String identity;
    private String email;
    private String password;
}
