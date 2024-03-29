package com.popinjay.loginsys.controller;


import com.popinjay.loginsys.config.TestConfig;
import com.popinjay.loginsys.models.UserRole;
import com.popinjay.loginsys.models.dtos.user.UserByIdDTO;
import com.popinjay.loginsys.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import(TestConfig.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @Test
  @WithMockUser(roles = "ADMIN")
  public void shouldReturnUserWhenIdIsProvided() throws Exception {
    UUID randomId = UUID.randomUUID();
    UserByIdDTO foundUsuario = UserByIdDTO.builder()
                                          .username("mitz")
                                          .roles(UserRole.USER)
                                          .id(randomId)
                                          .name("Leonardo")
                                          .build();

    when(this.userService.getUserById(randomId)).thenReturn(foundUsuario);

    this.mvc.perform(
                get("/user/{id}", randomId.toString()))
            .andExpect(status().isFound())
            .andExpect(jsonPath("$.id").value(randomId.toString()));
  }
}
