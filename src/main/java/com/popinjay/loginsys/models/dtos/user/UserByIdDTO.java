package com.popinjay.loginsys.models.dtos.user;


import com.popinjay.loginsys.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserByIdDTO {
  private String username;
  private String name;
  private UUID id;
  private UserRole roles;
}