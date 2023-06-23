package com.mail.mini_mailing_app.spring.boot.services;

import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public interface JwtTokenService {
    void saveUserToken(AppUser appUser, String jwtToken);
    void revokeAllUserTokens(AppUser appUser);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    UserDetails getUserDetails(String email);
    AuthenticationResponse getAuthenticationResponse(AppUser appUser, String message);
}
