package com.bodyTraining.fittrack.service;

import com.bodyTraining.fittrack.dto.request.LoginRequest;
import com.bodyTraining.fittrack.dto.request.RegisterRequest;
import com.bodyTraining.fittrack.dto.response.AuthResponse;
import com.bodyTraining.fittrack.entity.Users;
import com.bodyTraining.fittrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthResponse register(RegisterRequest request) {
		Users users = new Users();
		
		users.setUsername(request.username());
		users.setEmail(request.email());
		users.setPassword(passwordEncoder.encode(request.password()));
		
		userRepository.save(users);
		
		String token = jwtService.generateToken(users);
		
		return new AuthResponse(token);
		
	}
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);
		
		Users user = userRepository.findByEmail(request.email()).orElseThrow();
		
		String token = jwtService.generateToken(user);
		return new AuthResponse(token);
	}
}
