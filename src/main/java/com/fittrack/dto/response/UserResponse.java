package com.fittrack.dto.response;

import com.fittrack.domain.Role;

import java.util.UUID;

public record UserResponse(UUID id, String username, String email, Role role, Boolean active) {
}
