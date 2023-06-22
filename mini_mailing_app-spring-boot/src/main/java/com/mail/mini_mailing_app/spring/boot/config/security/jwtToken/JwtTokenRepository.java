package com.mail.mini_mailing_app.spring.boot.config.security.jwtToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    @Query(value = """
      select t from JwtToken  t inner join  AppUser  u\s
      on t.appUser.id = u.id\s
      where u.id = :id and (t.isExpired = false or t.isRevoked = false)\s
      """)
    List<JwtToken> findAllValidTokenByAppUser(Long id);
//    List<JwtToken> findAllByAppUser(AppUser appUse);
    Optional<JwtToken> findByJwtToken(String token);
}
