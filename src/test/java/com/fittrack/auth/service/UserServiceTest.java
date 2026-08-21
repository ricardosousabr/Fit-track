package com.fittrack.auth.service;

import com.fittrack.user.domain.Role;
import com.fittrack.user.domain.User;
import com.fittrack.user.dto.ChangePasswordRequest;
import com.fittrack.user.dto.UpdateProfileRequest;
import com.fittrack.user.dto.UserResponse;
import com.fittrack.user.mapper.UserMapper;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private UserMapper userMapper;
	
	@InjectMocks
	private UserService userService;
	
	private User authenticatedUser;
	
	public User newUser() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setUsername("Ricardo");
		user.setEmail("ricardo@gmail.com");
		user.setPassword("12345678");
		user.setRole(Role.STUDENT);
		
		return user;
	}
	
	@BeforeEach
	void setupSecurityContext() {
		authenticatedUser = newUser();
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(
						authenticatedUser,
						null,
						authenticatedUser.getAuthorities()
				)
		);
	}
	
	@AfterEach
	void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	void getProfile_success() {
		UserResponse expected = new UserResponse(
				authenticatedUser.getId(),
				"Ricardo",
				"ricardo@gmail.com",
				Role.STUDENT,
				true
		);
		
		when(userRepository.findByEmail("ricardo@gmail.com")).thenReturn(Optional.of(authenticatedUser));
		when(userMapper.toUserResponse(any(User.class))).thenReturn(expected);
		
		UserResponse result = userService.getProfile();
		
		assertEquals("ricardo@gmail.com", result.email());
	}
	
	@Test
	void updateProfile_success() {
		UserResponse expected = new UserResponse(
				authenticatedUser.getId(),
				"Marcos",
				"ricardo@gmail.com",
				Role.STUDENT,
				true
		);
		
		UpdateProfileRequest newData = new UpdateProfileRequest("Marcos");
		
		when(userRepository.findByEmail("ricardo@gmail.com")).thenReturn(Optional.of(authenticatedUser));
		when(userMapper.toUserResponse(any(User.class))).thenReturn(expected);
		
		UserResponse result = userService.updateProfile(newData);
		
		assertEquals("Marcos", result.username());
	}
	
	@Test
	void changePassword_wrongPassword() {
		ChangePasswordRequest newData = new ChangePasswordRequest("234234234", "5794256743");
		
		when(userRepository.findByEmail("ricardo@gmail.com")).thenReturn(Optional.of(authenticatedUser));
		when(passwordEncoder.matches("234234234", authenticatedUser.getPassword())).thenReturn(false);
		
		assertThatThrownBy(() -> userService.changePassword(newData))
				.isInstanceOf(ResponseStatusException.class)
				.hasMessageContaining("Current password is incorrect");
	}
	
}
