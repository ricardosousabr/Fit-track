package com.fittrack.fittrack.controller;

import com.fittrack.fittrack.dto.request.ChangePasswordRequest;
import com.fittrack.fittrack.dto.request.UpdateProfileRequest;
import com.fittrack.fittrack.dto.response.UserResponse;
import com.fittrack.fittrack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	public UserResponse getProfile() {
		return userService.getProfile();
	}
	
	@PutMapping("/me")
	public UserResponse update(@RequestBody @Valid UpdateProfileRequest data) {
		return userService.updateProfile(data);
	}
	
	@PutMapping("/me/password")
	public void changePass(@RequestBody @Valid ChangePasswordRequest pass) {
		userService.changePassword(pass);
	}
}
