package com.mail.mini_mailing_app.spring.boot.exception;

public class AlreadyExistsException extends EmailAppException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
