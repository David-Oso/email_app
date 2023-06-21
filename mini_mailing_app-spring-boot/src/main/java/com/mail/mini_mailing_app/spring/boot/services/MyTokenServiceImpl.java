package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.MyToken;
import com.mail.mini_mailing_app.spring.boot.data.repository.TokenRepository;
import com.mail.mini_mailing_app.spring.boot.exception.VerificationException;
import com.mail.mini_mailing_app.spring.boot.utilities.MailAppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MyTokenServiceImpl implements MyTokenService {
    private final TokenRepository tokenRepository;

    @Override
    public String generateAndSaveToken(AppUser appUser) {
        Optional<MyToken> existingToken = tokenRepository.findMyTokenByAppUser(appUser);
        existingToken.ifPresent(tokenRepository::delete);
        String generatedToken = MailAppUtils.generateRandomString(6);
        MyToken myToken = MyToken.builder()
                .appUser(appUser)
                .token(generatedToken)
                .build();
        tokenRepository.save(myToken);
        return generatedToken;    }

    @Override
    public Optional<MyToken> validateReceivedToken(String token, AppUser appUser) {
        Optional<MyToken> receivedToken = tokenRepository.findMyTokenByAppUserAndToken
                (appUser, token);
        if(receivedToken.isEmpty())throw new VerificationException("Invalid token");
        else if(receivedToken.get().getExpirationTime().isBefore(LocalDateTime.now())){
            tokenRepository.delete(receivedToken.get());
            throw new VerificationException("Token is expired");
        }
        return receivedToken;
    }

    @Override
    public void deleteToken(MyToken myToken) {
        tokenRepository.delete(myToken);
    }
}
