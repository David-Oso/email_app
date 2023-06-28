package com.mail.mini_mailing_app.spring.boot.config.security;

import com.mail.mini_mailing_app.spring.boot.config.security.jwtToken.JwtTokenRepository;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private UserDetailsService userDetailsService;
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
//        if(request.getServletPath().equals("")){
//            filterChain.doFilter(request, response);
//            return;
//        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean isTokenValid = jwtTokenRepository.findByJwtToken(jwt)
                    .map(token -> !token.isExpired() && !token.isRevoked())
                    .orElse(false);
            if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
