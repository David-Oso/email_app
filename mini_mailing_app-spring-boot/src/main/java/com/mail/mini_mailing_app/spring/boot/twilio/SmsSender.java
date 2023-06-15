package com.mail.mini_mailing_app.spring.boot.twilio;


import com.mail.mini_mailing_app.spring.boot.data.dto.request.SmsRequest;

public interface SmsSender {
    String sendSms(SmsRequest smsRequest);
}
