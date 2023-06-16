package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterUserRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.SmsRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.request.VerificationRequest;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.MyToken;
import com.mail.mini_mailing_app.spring.boot.data.model.Role;
import com.mail.mini_mailing_app.spring.boot.data.model.User;
import com.mail.mini_mailing_app.spring.boot.data.repository.TokenRepository;
import com.mail.mini_mailing_app.spring.boot.data.repository.UserRepository;
import com.mail.mini_mailing_app.spring.boot.exception.AlreadyExistsException;
import com.mail.mini_mailing_app.spring.boot.exception.EmailAppException;
import com.mail.mini_mailing_app.spring.boot.exception.NotFoundException;
import com.mail.mini_mailing_app.spring.boot.exception.VerificationException;
import com.mail.mini_mailing_app.spring.boot.services.AppUserService;
import com.mail.mini_mailing_app.spring.boot.services.UserService;
import com.mail.mini_mailing_app.spring.boot.twilio.SmsSender;
import com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils;
import jakarta.validation.Valid;import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


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
            String to = savedUser.getUserDetails().getPhoneNumber();
            sendSms(to, message);
            return """
                    An activation token has been sent to you account.
                    Please check your phone to input the token.
                    """;
        }
    }

    @Override
    public String verifyUser(VerificationRequest verificationRequest) {
        AppUser appUser = appUserService.getBy
                (verificationRequest.getPhoneNUmber()).orElse(null);
        if (appUser == null)throw new VerificationException("Invalid phone number");
        Optional<MyToken> receivedToken = validateReceivedToken(verificationRequest.getVerificationToken(), appUser);
        User user = getUserByAppUser(appUser).orElse(null);
        if(user == null)throw new NotFoundException("User not found");
        else{
            saveVerifiedUser(verificationRequest.getEmail(), appUser, user);
//            send mail to the user
            tokenRepository.delete(receivedToken.get());
        }
        return "Verification Successful";
    }


    private Optional<User> getUserByAppUser(AppUser appUser) {
        return userRepository.findByUserDetails(appUser);
    }

    @Override
    public AuthenticationResponse login(String email, String poneNumber) {
        return null;
    }

    @Override
    public String resendVerificationToken(String phoneNumber) {
        AppUser appUser = appUserService.getBy(phoneNumber)
                .orElse(null);
        if (appUser == null)throw new VerificationException("Invalid phone number");
        String token = generateAndSaveToken(appUser);

        String message = getVerificationMessage(appUser, token);

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

    private LocalDate convertToLocalDate(String dateOfBirth) {
//        if(dateOfBirth == null)throw new
//                EmailAppException("Date of birth cannot be null");
//        else{
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateOfBirth, dateTimeFormatter);
            if(date.isAfter(LocalDate.now()))
                throw new EmailAppException("Date must be in the past");
            return date;
//        }
    }

    private String checkWhetherUserIsEnableAndNotLocked(AppUser appUser) {
        String to = appUser.getPhoneNumber();

        if(appUser.isEnabled() && !appUser.isBlocked())
            throw new AlreadyExistsException(String.format(
                    "user with email %s already exists", appUser.getEmail()));

        else if (appUser.isBlocked() && appUser.isEnabled())
            throw new EmailAppException(
                    "Account registered with this email is blocked");

        else{
            return resendVerificationToken(to);
        }
    }

    private String generateAndSaveToken(AppUser appUser) {
        Optional<MyToken> existingToken = tokenRepository.findMyTokenByAppUser(appUser);
        existingToken.ifPresent(tokenRepository::delete);
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


    private void saveVerifiedUser(String email, AppUser appUser, User user) {
        appUser.setEmail(email);
        appUser.setBlocked(false);
        appUser.setEnabled(true);
        user.setUserDetails(appUser);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    private Optional<MyToken> validateReceivedToken(String token, AppUser appUser) {
        Optional<MyToken> receivedToken = tokenRepository.findMyTokenByAppUserAndToken
                (appUser, token);
        if(receivedToken.isEmpty())throw new VerificationException("Invalid token");
        else if(receivedToken.get().getExpirationTime().isBefore(LocalDateTime.now())){
            tokenRepository.delete(receivedToken.get());
            throw new VerificationException("Token is expired");
        }
        return receivedToken;
    }


    private void sendSms(String phoneNumber, String message) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setRecipientPhoneNumber(phoneNumber);
        smsRequest.setMessage(message);
        smsSender.sendSms(smsRequest);
    }
}
