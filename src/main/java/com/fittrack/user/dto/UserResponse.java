package com.fittrack.user.dto;

import com.fittrack.user.domain.Role;

import java.util.UUID;

public record UserResponse(UUID id, String username, String email, Role role, Boolean active) {
}
