package com.mail.mini_mailing_app.spring.boot.utilities;

import java.security.SecureRandom;
import java.util.Base64;

public class MailAppUtils {
    public static final String SMS_MESSAGE_REGEX = "^(?s).*$";
    public static final String SMS_PHONE_NUMBER_REGEX = "^(\\+?234|0)[789]\\d{9}$";
    public static final String EMAIL_REGEX_STRING = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PASSWORD_REGEX_STRING = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String NAME_REGEX = "^[A-Z][a-zA-Z]{0,39}$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+?234)[789]\\d{9}$";
    public static final String DATE_OF_BIRTH_REGEX = "dd/MM/yyyy";
    public static final String APP_EMAIL = "noreply@mailingapp.net";
    public static final String APP_NAME = "mailing_app";
    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\User\\IdeaProjects\\mini_mailing_app(spring boot)\\mini_mailing_app-spring-boot\\src\\main\\resources\\static\\pic.jpg";


    public static String generateRandomString(int length){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
