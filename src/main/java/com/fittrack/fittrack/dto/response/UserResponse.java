package com.fittrack.fittrack.dto.response;

import com.fittrack.fittrack.domain.Role;

import java.util.UUID;

public record UserResponse(UUID id, String username, String email, Role role, Boolean active) {
}
