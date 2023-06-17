package com.mail.mini_mailing_app.spring.boot.exception;

public class MailException extends EmailAppException{
    public MailException(String message) {
        super(message);
    }
}
