package com.popinjay.loginsys.services;

import com.popinjay.loginsys.controller.errors.UsernameUniqueException;
import com.popinjay.loginsys.models.User;
import com.popinjay.loginsys.models.dtos.auth.RegisterResponseDTO;
import com.popinjay.loginsys.models.dtos.user.UserByIdDTO;
import com.popinjay.loginsys.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

  private final AuthService userDetailsService;
  private final UserRepository userRepository;

  public RegisterResponseDTO createUser(User user) throws UsernameUniqueException {
    UserDetails findUser = userDetailsService.loadUserByUsername(user.getUsername());
    if (findUser != null) {
      throw new UsernameUniqueException("Já existe um usuário com este username!");
    }

    User createdUser = userRepository.save(user);
    return new RegisterResponseDTO(createdUser.getName(), createdUser.getUsername(), createdUser.getStacks(),
                                   createdUser.getRole(), createdUser.getId());
  }

  public UserByIdDTO getUserById(UUID id) throws EntityNotFoundException {
    return userRepository.findById(id)
                         .map(user -> new UserByIdDTO(user.getUsername(), user.getName(), user.getId(), user.getRole()))
                         .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

  }

  public void deleteUser(UUID id) throws EntityNotFoundException {
    userRepository.deleteById(id);
  }
}
