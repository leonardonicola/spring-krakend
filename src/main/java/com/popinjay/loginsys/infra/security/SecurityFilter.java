package com.popinjay.loginsys.infra.security;

import com.popinjay.loginsys.services.AuthService;
import com.popinjay.loginsys.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final AuthService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String token = this.recoverToken(request);
    if (token != null) {
      var userUsername = tokenService.validateToken(token);
      log.debug("Token received: {}", token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(userUsername);
      if (userDetails != null) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    log.debug("Go to other filter");
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.trim().startsWith("Bearer ")) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }

}

