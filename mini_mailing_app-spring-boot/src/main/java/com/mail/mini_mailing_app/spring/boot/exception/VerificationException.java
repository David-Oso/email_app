package com.mail.mini_mailing_app.spring.boot.exception;

public class VerificationException extends EmailAppException {
    public VerificationException(String message) {
        super(message);
    }
}
