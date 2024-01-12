package com.popinjay.loginsys.models.dtos.auth;

import com.popinjay.loginsys.models.UserRole;

import java.util.List;
import java.util.UUID;

public record RegisterResponseDTO(
        String name,
        String username,
        List<String> stacks,

        UserRole role,

        UUID id
) {

}
