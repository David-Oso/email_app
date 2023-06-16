package com.mail.mini_mailing_app.spring.boot.data.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isBlocked = true;
    private boolean isEnabled = false;
}
