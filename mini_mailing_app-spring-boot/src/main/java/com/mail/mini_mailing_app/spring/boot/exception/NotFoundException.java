package com.mail.mini_mailing_app.spring.boot.exception;

public class NotFoundException extends EmailAppException{
    public NotFoundException(String message) {
        super(message);
    }
}
