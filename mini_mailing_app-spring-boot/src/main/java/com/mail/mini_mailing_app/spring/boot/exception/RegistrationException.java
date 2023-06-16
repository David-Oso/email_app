package com.mail.mini_mailing_app.spring.boot.exception;

public class RegistrationException extends EmailAppException {
    public RegistrationException(String message) {
        super(message);
    }
}
