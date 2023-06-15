package com.mail.mini_mailing_app.spring.boot.exception;

public class SmsSenderException extends RuntimeException{
    public SmsSenderException(String message){
        super(message);
    }
}
