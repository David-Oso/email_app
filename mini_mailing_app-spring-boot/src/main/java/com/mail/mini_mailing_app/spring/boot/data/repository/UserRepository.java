package com.mail.mini_mailing_app.spring.boot.data.repository;

import com.mail.mini_mailing_app.spring.boot.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
