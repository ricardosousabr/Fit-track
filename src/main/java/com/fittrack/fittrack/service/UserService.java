package com.fittrack.fittrack.service;

import com.fittrack.fittrack.dto.request.ChangePasswordRequest;
import com.fittrack.fittrack.dto.request.UpdateProfileRequest;
import com.fittrack.fittrack.dto.response.UserResponse;
import com.fittrack.fittrack.entity.Users;
import com.fittrack.fittrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	private Users getUserAuthenticated() {
		UserDetails userAuthenticated = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Users user = userRepository.findByEmail(userAuthenticated.getUsername()).orElseThrow();
		
		return user;
	}
	
	public UserResponse getProfile() {
		return new UserResponse(getUserAuthenticated().getId(), getUserAuthenticated().getUsername(), getUserAuthenticated().getEmail(), getUserAuthenticated().getRole(), getUserAuthenticated().getActive());
	}
	
	public UserResponse updateProfile(UpdateProfileRequest data) {
		getUserAuthenticated().setUsername(data.username());
		userRepository.save(getUserAuthenticated());
		
		return new UserResponse(getUserAuthenticated().getId(), getUserAuthenticated().getUsername(), getUserAuthenticated().getEmail(), getUserAuthenticated().getRole(), getUserAuthenticated().getActive());
	}
	
	public void changePassword(ChangePasswordRequest pass) {
		boolean validPassword = passwordEncoder.matches(pass.password(), getUserAuthenticated().getPassword());
		
		if (!validPassword) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
		}
		
			getUserAuthenticated().setPassword(passwordEncoder.encode(pass.newPassword()));
			userRepository.save(getUserAuthenticated());
	}
}
