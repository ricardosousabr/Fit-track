package com.fittrack.user.service;

import com.fittrack.user.dto.ChangePasswordRequest;
import com.fittrack.user.dto.UpdateProfileRequest;
import com.fittrack.user.dto.UserResponse;
import com.fittrack.user.domain.User;
import com.fittrack.user.mapper.UserMapper;
import com.fittrack.user.repository.UserRepository;
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
	private final UserMapper userMapper;
	
	private User getUserAuthenticated() {
		UserDetails userAuthenticated = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(userAuthenticated.getUsername()).orElseThrow();
		
		return user;
	}
	
	public UserResponse getProfile() {
		return userMapper.toUserResponse(getUserAuthenticated());
	}
	
	public UserResponse updateProfile(UpdateProfileRequest data) {
		User user = getUserAuthenticated();
		user.setUsername(data.username());
		userRepository.save(user);

		return userMapper.toUserResponse(user);
	}
	
	public void changePassword(ChangePasswordRequest pass) {
		User user = getUserAuthenticated();
		boolean validPassword = passwordEncoder.matches(pass.password(), user.getPassword());

		if (!validPassword) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
		}

		user.setPassword(passwordEncoder.encode(pass.newPassword()));
		userRepository.save(user);
	}
}
