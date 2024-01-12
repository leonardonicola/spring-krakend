package com.popinjay.loginsys.models.dtos.auth;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull String username, @NotNull String password) {
}
