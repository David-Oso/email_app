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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private final JwtService jwtService;
    private final JwtTokenRepository jwtTokenRepository;
    private final AppUserService appUserService;

    @Override
    public void saveAppUserToken(AppUser appUser, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .appUser(appUser)
                .jwtToken(jwtToken)
                .isExpired(false)
                .isRevoked(false)
                .build();
        jwtTokenRepository.save(token);
    }

    @Override
    public void revokeAllTokens(AppUser appUser) {
        var validUserTokens = jwtTokenRepository.findAllValidTokenByAppUser(appUser.getId());
        if(validUserTokens.isEmpty())return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer "))return;
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var user = this.appUserService.getUserByEmail(userEmail);
            if(jwtService.isTokenValid(refreshToken, user)){
                String accessToken = jwtService.generateAccessToken(user);
                revokeAllTokens(user);
                saveAppUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public AuthenticationResponse getAuthenticationResponse(AppUser appUser, String message) {
        String accessToken = jwtService.generateAccessToken(appUser);
        String refreshToken = jwtService.generateRefreshToken(appUser);
        revokeAllTokens(appUser);
        saveAppUserToken(appUser, accessToken);
        return AuthenticationResponse.builder()
                .message(message)
                .isSuccess(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
