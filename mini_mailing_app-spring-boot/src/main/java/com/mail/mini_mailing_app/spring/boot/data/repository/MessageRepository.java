package com.mail.mini_mailing_app.spring.boot.data.repository;

import com.mail.mini_mailing_app.spring.boot.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
