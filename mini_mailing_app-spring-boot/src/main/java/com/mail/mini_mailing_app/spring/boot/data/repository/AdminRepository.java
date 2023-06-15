package com.mail.mini_mailing_app.spring.boot.data.repository;

import com.mail.mini_mailing_app.spring.boot.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
