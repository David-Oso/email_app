//package com.mail.mini_mailing_app.spring.boot.config.security;
//
//import com.mail.mini_mailing_app.spring.boot.config.security.jwtToken.JwtTokenRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class LogoutService implements LogoutHandler {
//    private final JwtTokenRepository jwtTokenRepository;
//
//    @Override
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        if(authentication == null || !authHeader.startsWith("Bearer "))return;
//        jwt = authHeader.substring(7);
//        var storedToken = jwtTokenRepository.findByJwtToken(jwt)
//                .orElse(null);
//        if(storedToken != null){
////            storedToken.setExpired(true);
////            storedToken.setRevoked(true);
////            jwtTokenRepository.save(storedToken);
//            jwtTokenRepository.deleteAll();
//            SecurityContextHolder.clearContext();
//        }
//    }
//}
