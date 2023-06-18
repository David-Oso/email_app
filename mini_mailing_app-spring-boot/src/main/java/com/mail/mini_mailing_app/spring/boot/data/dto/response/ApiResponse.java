package com.mail.mini_mailing_app.spring.boot.data.dto.response;

import lombok.*;

@Builder
@Getter
public class ApiResponse {
    private String message;
    private boolean isSuccess;
}
