package com.popinjay.loginsys.config;

import com.popinjay.loginsys.services.AuthService;
import com.popinjay.loginsys.services.TokenService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("test")
public class TestConfig {

  @Bean
  public TokenService tokenService() {
    return Mockito.mock(TokenService.class);
  }

  @Bean
  public AuthService authService() {
    return Mockito.mock(AuthService.class);
  }
}
