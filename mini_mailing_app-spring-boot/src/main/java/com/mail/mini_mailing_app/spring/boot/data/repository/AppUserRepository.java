package com.mail.mini_mailing_app.spring.boot.data.repository;

import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByPhoneNumber(String phoneNumber);
}
