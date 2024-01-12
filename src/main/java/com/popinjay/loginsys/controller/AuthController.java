package com.popinjay.loginsys.controller;


import com.popinjay.loginsys.controller.errors.UsernameUniqueException;
import com.popinjay.loginsys.models.User;
import com.popinjay.loginsys.models.dtos.auth.LoginDTO;
import com.popinjay.loginsys.models.dtos.auth.RegisterDTO;
import com.popinjay.loginsys.models.dtos.auth.RegisterResponseDTO;
import com.popinjay.loginsys.services.TokenService;
import com.popinjay.loginsys.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final UserService userService;
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginDTO body) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(body.username(), body.password());
    var auth = authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((User) auth.getPrincipal());
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("access_token", token));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO body) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
    User newUser = new User(body, encryptedPassword);
    try {
      RegisterResponseDTO createdUser = this.userService.createUser(newUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    } catch (UsernameUniqueException e) {
      Map<String, String> errorMsg = Map.of("message", "Já existe um usuário com este username!");
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMsg);
    } catch (Exception e) {
      log.error("Erro ao criar usuario");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário!");
    }
  }
}
