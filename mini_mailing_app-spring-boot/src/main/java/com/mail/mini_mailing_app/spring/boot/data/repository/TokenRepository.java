package com.mail.mini_mailing_app.spring.boot.data.repository;

import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.MyToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<MyToken, Long> {
    Optional<MyToken> findMyTokenByAppUserAndToken(AppUser appUser, String token);
    Optional<MyToken> findMyTokenByAppUser(AppUser appUser);
}
