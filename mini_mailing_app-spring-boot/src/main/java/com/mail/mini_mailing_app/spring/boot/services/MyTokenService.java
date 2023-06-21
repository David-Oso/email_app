package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.MyToken;

import java.util.Optional;

public interface MyTokenService {
    String generateAndSaveToken(AppUser appUser);
    Optional<MyToken> validateReceivedToken(String token, AppUser appUser);
    void deleteToken(MyToken myToken);
}
