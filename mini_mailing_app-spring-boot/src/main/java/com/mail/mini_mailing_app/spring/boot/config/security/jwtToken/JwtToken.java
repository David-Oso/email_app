package com.mail.mini_mailing_app.spring.boot.config.security.jwtToken;

import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String jwtToken;
    @Enumerated(EnumType.STRING)
    private final JwtTokenType jwtTokenType = JwtTokenType.BEARER;
    private boolean isRevoked;
    private boolean isExpired;
//    @OneToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_jwt_id")
    private AppUser appUser;

}
