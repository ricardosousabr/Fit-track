package com.fittrack.auth.service;

import com.fittrack.auth.dto.LoginRequest;
import com.fittrack.auth.dto.RegisterRequest;
import com.fittrack.shared.security.JwtService;
import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private JwtService jwtService;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@InjectMocks
	private AuthService authService;
	
	
	@Test
	void registerUser_success() {
		RegisterRequest newUser = new RegisterRequest("Ricardo", "ricardo@gmail.com", "12345678");
		
		when(userRepository.existsByEmail(newUser.email())).thenReturn(false);
		when(passwordEncoder.encode(newUser.password())).thenReturn("senha_codificada");
		when(jwtService.generateToken(any())).thenReturn("token_falso");
		var  response = authService.register(newUser);
		
		assertThat(response.token()).isNotNull();
		assertThat((response.token())).isEqualTo("token_falso");
	
	}
	
	@Test
	void registerUser_emailAlreadyExists_throwsConflict() {
		RegisterRequest newUser = new RegisterRequest("Ricardo", "ricardo@gmail.com", "12345678");
		
		when(userRepository.existsByEmail(newUser.email())).thenReturn(true);
		
		assertThatThrownBy(() -> authService.register(newUser))
				.isInstanceOf(ResponseStatusException.class)
				.hasMessageContaining("Email already in use");
	}
	
	@Test
	void login_success() {
		LoginRequest newLogin = new LoginRequest("ricardo@gmail.com", "12345678");
		
		User user = new User();
		user.setEmail("ricardo@gmail.com");
		user.setUsername("Ricardo");
		
		when(userRepository.findByEmail(newLogin.email())).thenReturn(Optional.of(user));
		when(jwtService.generateToken(any())).thenReturn("token_falso");
		var response = authService.login(newLogin);
		
		assertThat(response.token()).isEqualTo("token_falso");
		
	}
	
	@Test
	void login_badCredentials() {
		LoginRequest request = new LoginRequest("ricardo@gmail.com", "12345678");
		
		doThrow(new BadCredentialsException("Bad credentials"))
				.when(authenticationManager).authenticate(any());
		
		assertThatThrownBy(() -> authService.login(request)).isInstanceOf(BadCredentialsException.class);
	}
}
