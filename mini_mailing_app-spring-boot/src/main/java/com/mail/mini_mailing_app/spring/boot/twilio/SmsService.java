package com.mail.mini_mailing_app.spring.boot.twilio;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final SmsSender sender;

    @Autowired
    public SmsService(@Qualifier("twilio") SmsSender sender){
        this.sender = sender;
    }
    public void sendSms(SmsRequest request){
        sender.sendSms(request);
    }
}
