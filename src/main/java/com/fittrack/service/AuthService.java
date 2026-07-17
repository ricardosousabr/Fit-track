package com.fittrack.service;

import com.fittrack.domain.Role;
import com.fittrack.dto.request.LoginRequest;
import com.fittrack.dto.request.RegisterRequest;
import com.fittrack.dto.response.AuthResponse;
import com.fittrack.entity.User;
import com.fittrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthResponse register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw  new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
		}
		
		User user = new User();
		
		user.setUsername(request.username());
		user.setEmail(request.email());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRole(Role.STUDENT);
		user.setActive(true);

		userRepository.save(user);
		
		String token = jwtService.generateToken(user);
		
		return new AuthResponse(token);
		
	}
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);
		
		User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
		
		String token = jwtService.generateToken(user);
		return new AuthResponse(token);
	}
}
