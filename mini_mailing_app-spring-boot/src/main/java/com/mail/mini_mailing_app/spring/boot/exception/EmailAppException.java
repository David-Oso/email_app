package com.mail.mini_mailing_app.spring.boot.exception;

public class EmailAppException extends RuntimeException{
    public EmailAppException(String message){
        super(message);
    }
}
