package com.popinjay.loginsys.controller;

import com.popinjay.loginsys.models.dtos.user.UserByIdDTO;
import com.popinjay.loginsys.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserByUsername(@PathVariable UUID id) {

    try {
      UserByIdDTO foundUser = userService.getUserById(id);
      log.info(foundUser.toString());
      return ResponseEntity.status(HttpStatus.FOUND).body(foundUser);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(Map.of("message", "Usuário não existe!"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(Map.of("message", "Erro ao encontrar usuário!"));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
    try {
      userService.deleteUser(id);
      return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    } catch (EntityNotFoundException e) {
      Map<String, String> errorMsg = Map.of("message", "Usuário não encontrado");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(Map.of("message", "Erro ao deletar usuario"));
    }
  }
}
