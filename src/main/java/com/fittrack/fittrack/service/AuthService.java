package com.fittrack.fittrack.service;

import com.fittrack.fittrack.domain.Role;
import com.fittrack.fittrack.dto.request.LoginRequest;
import com.fittrack.fittrack.dto.request.RegisterRequest;
import com.fittrack.fittrack.dto.response.AuthResponse;
import com.fittrack.fittrack.entity.Users;
import com.fittrack.fittrack.repository.UserRepository;
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
		
		Users users = new Users();
		
		users.setUsername(request.username());
		users.setEmail(request.email());
		users.setPassword(passwordEncoder.encode(request.password()));
		users.setRole(Role.STUDENT);
		users.setActive(true);

		userRepository.save(users);
		
		String token = jwtService.generateToken(users);
		
		return new AuthResponse(token);
		
	}
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);
		
		Users user = userRepository.findByEmail(request.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
		
		String token = jwtService.generateToken(user);
		return new AuthResponse(token);
	}
}
