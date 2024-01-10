package com.popinjay.loginsys.models.dtos;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull String username, @NotNull String password) {
}
