package com.bodyTraining.fittrack.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AuthResponse(
		String token,
		String username) {
}
