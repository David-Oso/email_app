package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.mini_mailing_app.spring.boot.config.security.JwtService;
import com.mail.mini_mailing_app.spring.boot.config.security.jwtToken.JwtToken;
import com.mail.mini_mailing_app.spring.boot.config.security.jwtToken.JwtTokenRepository;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.services.AppUserService;
import com.mail.mini_mailing_app.spring.boot.services.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private final AppUserService appUserService;
    private final JwtTokenRepository jwtTokenRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public void saveUserToken(AppUser appUser, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .appUser(appUser)
                .jwtToken(jwtToken)
                .isExpired(false)
                .isRevoked(false)
                .build();
        jwtTokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(AppUser appUser) {
        var validUserTokens = jwtTokenRepository
                .findAllValidTokenByAppUser(appUser.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            return;
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            AppUser appUser = appUserService.getUserByEmail(userEmail); //I should correct this
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); //I should correct this
            if(jwtService.isTokenValid(refreshToken, userDetails)){
                String accessToken = jwtService.generateAccessToken(userDetails);
                revokeAllUserTokens(appUser);
                saveUserToken(appUser, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public UserDetails getUserDetails(String email) {
        return userDetailsService.loadUserByUsername(email);
    }
}
