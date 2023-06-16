package com.mail.mini_mailing_app.spring.boot.data.model;

import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MyToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private AppUser appUser;
    @Column(unique = true)
    private String token;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expirationTime = createdAt.plusMinutes(5L);

//    public MyToken(AppUser appUser, String token){
//        this.appUser = appUser;
//        this.token = token;
//    }
}
