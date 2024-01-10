package com.popinjay.loginsys.controller;

import com.popinjay.loginsys.services.AuthService;
import com.popinjay.loginsys.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
  private final UserService userService;
  private final AuthService authService;

  @GetMapping("/{username}")
  public ResponseEntity<?> getAllUsers(@PathVariable String username) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.loadUserByUsername(username));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
    try {
      userService.deleteUser(id);
      logger.info("User deleted with success: " + id);
      return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    } catch (EntityNotFoundException e) {
      logger.warn("User not found: " + id);
      Map<String, String> errorMsg = Map.of("message", "Usuário não encontrado");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
    } catch (Exception e) {
      logger.error("Error while deleting user with ID: " + id);
      throw new RuntimeException("Erro ao deletar usuário");
    }
  }
}
