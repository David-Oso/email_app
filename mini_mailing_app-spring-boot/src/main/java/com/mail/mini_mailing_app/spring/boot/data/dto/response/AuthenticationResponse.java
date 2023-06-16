package com.mail.mini_mailing_app.spring.boot.data.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResponse {
    private String message;
    private boolean isSuccess;
}
