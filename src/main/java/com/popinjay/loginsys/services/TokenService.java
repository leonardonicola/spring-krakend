package com.popinjay.loginsys.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.popinjay.loginsys.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String jwtSecret;

  public String generateToken(User user) throws JWTCreationException {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
      return JWT.create()
                .withIssuer("codas")
                .withSubject(user.getUsername())
                .withExpiresAt(genExpDate())
                .sign(algorithm);
    } catch (JWTCreationException e) {
      throw new RuntimeException("Erro ao gerar token!", e);
    }
  }

  public String validateToken(String jwtToken) throws JWTVerificationException {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
      return JWT.require(algorithm)
                .withIssuer("codas")
                .build()
                .verify(jwtToken)
                .getSubject();
    } catch (JWTVerificationException e) {
      return "";
    }
  }

  private Instant genExpDate() {
    return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
  }
}
