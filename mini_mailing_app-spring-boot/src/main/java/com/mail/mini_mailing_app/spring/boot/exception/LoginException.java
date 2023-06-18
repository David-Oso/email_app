package com.mail.mini_mailing_app.spring.boot.exception;

public class LoginException extends EmailAppException{
    public LoginException(String message) {
        super(message);
    }
}
