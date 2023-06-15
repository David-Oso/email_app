package com.mail.mini_mailing_app.spring.boot.data.repository;

import com.mail.mini_mailing_app.spring.boot.data.model.Sent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentRepository extends JpaRepository<Sent, Long> {
}
