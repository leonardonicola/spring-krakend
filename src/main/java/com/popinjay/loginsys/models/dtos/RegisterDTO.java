package com.popinjay.loginsys.models.dtos;

import com.popinjay.loginsys.models.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterDTO(
        @NotNull String name,
        @NotNull String username,
        @NotNull List<String> stacks,
        @NotNull String password,

        UserRole role

) {

}
