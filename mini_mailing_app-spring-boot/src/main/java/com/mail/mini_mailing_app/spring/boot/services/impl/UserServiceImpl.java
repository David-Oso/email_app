package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.SmsRequest;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.MyToken;
import com.mail.mini_mailing_app.spring.boot.data.model.Role;
import com.mail.mini_mailing_app.spring.boot.data.model.User;
import com.mail.mini_mailing_app.spring.boot.data.repository.TokenRepository;
import com.mail.mini_mailing_app.spring.boot.data.repository.UserRepository;
import com.mail.mini_mailing_app.spring.boot.exception.AlreadyExistsException;
import com.mail.mini_mailing_app.spring.boot.exception.EmailAppException;
import com.mail.mini_mailing_app.spring.boot.services.AppUserService;
import com.mail.mini_mailing_app.spring.boot.services.UserService;
import com.mail.mini_mailing_app.spring.boot.twilio.SmsSender;
import com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService {
    private final AppUserService appUserService;
    private final UserRepository userRepository;
    private final SmsSender smsSender;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;

    @Override
    public String registerUser(RegisterUserRequest request) {
        AppUser appUser = appUserService.getBy(request.getPhoneNumber()).orElse(null);
        if(appUser != null){
            return checkWhetherUserIsEnableAndNotLocked(appUser);            
        }else{
            AppUser userDetails = setAppUser(request);

            User savedUser = saveNewUser(request, userDetails);
            String token = generateAndSaveToken(savedUser.getUserDetails());
            String message = getVerificationMessage(savedUser.getUserDetails(), token);
            sendSms(savedUser.getPhoneNumber(), message);
            return """
                    An activation token has been sent to you account.
                    Please check your phone to input the token.
                    """;
        }
    }

    private User saveNewUser(RegisterUserRequest request, AppUser userDetails) {
        LocalDate dateOfBirth = convertToLocalDate(request.getDateOfBirth());
        int age = getAge(dateOfBirth);

        User user = new User();
        user.setUserDetails(userDetails);
        user.setDateOfBirth(dateOfBirth);
        user.setGender(request.getGender());
        user.setAge(age);
        return userRepository.save(user);
    }

    private AppUser setAppUser(RegisterUserRequest request) {
        AppUser userDetails = modelMapper.map(request, AppUser.class);
        userDetails.setRole(Role.USER);
        return userDetails;
    }

    private int getAge(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears();
    }

    private LocalDate convertToLocalDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, dateTimeFormatter);
    }

    private String checkWhetherUserIsEnableAndNotLocked(AppUser appUser) {
        String token = generateAndSaveToken(appUser);
        String message = getVerificationMessage(appUser, token);
        String to = appUser.getPhoneNumber();

        if(appUser.isEnabled() && !appUser.isBlocked())
            throw new AlreadyExistsException(String.format(
                    "user with email %s already exists", appUser.getEmail()));

        else if (appUser.isBlocked())
            throw new EmailAppException(
                    "Account registered with this email is blocked");

        else{
            return resendVerificationToken(to, message);
        }
    }

    private String generateAndSaveToken(AppUser appUser) {
        String generatedToken = MailAppUtils.generateRandomString(6);
        MyToken myToken = MyToken.builder()
                .appUser(appUser)
                .token(generatedToken)
                .build();
        tokenRepository.save(myToken);
        return generatedToken;
    }

    private static String getVerificationMessage(AppUser appUser, String token) {
        return String.format("""
                Dear %s, please enter the following characters\s
                to verify your phone number
                            %s
                Note: the token expires after 10 minutes
                """, appUser.getFirstName(), token);
    }

    private void sendSms(String phoneNumber, String message) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setRecipientPhoneNumber(phoneNumber);
        smsRequest.setMessage(message);
        smsSender.sendSms(smsRequest);
    }


    @Override
    public String resendVerificationToken(String phoneNumber, String message) {
        sendSms(phoneNumber, message);
        return """
                Another verification token has been sent to you phone
                Please, enter the verification token to enable your account.
                Note: The token will expired after 10 minutes
                """;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }
}
