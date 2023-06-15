package com.mail.mini_mailing_app.spring.boot.data.model.token;

import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private AppUser appUser;
    private String token;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expirationTime = createdAt.plusMinutes(5L);
}
