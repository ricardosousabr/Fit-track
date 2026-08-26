package com.fittrack.workout.service;

import com.fittrack.auth.dto.RegisterRequest;
import com.fittrack.user.domain.Role;
import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.workout.domain.Exercise;
import com.fittrack.workout.domain.ExerciseCategory;
import com.fittrack.workout.dto.ExerciseRequest;
import com.fittrack.workout.dto.ExerciseResponse;
import com.fittrack.workout.mapper.ExerciseMapper;
import com.fittrack.workout.repository.ExerciseRepository;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {
	@Mock
	private ExerciseRepository exerciseRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ExerciseMapper exerciseMapper;
	
	private User authenticatedUser;
	
	@InjectMocks
	private ExerciseService exerciseService;
	
	public User newUser() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setUsername("Ricardo");
		user.setEmail("ricardo@gmail.com");
		user.setPassword("12345678");
		user.setRole(Role.STUDENT);
		
		return user;
	}
	
	public Exercise newExercise() {
		Exercise exercise = new Exercise();
		
		exercise.setId(UUID.randomUUID());
		exercise.setCreatedBy(newUser());
		exercise.setName("Squat");
		exercise.setCategory(ExerciseCategory.LEGS);
		exercise.setDescription("The squat is a fundamental lower-body strength exercise that involves lowering and raising the hips, simulating the act of sitting. It strengthens the thighs, glutes, and abdominals, requiring stability and proper technique to prevent injury.");
		
		return exercise;
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
	public void createExerciseSuccess() {
		User user = newUser();
		Exercise exercise = newExercise();
		ExerciseResponse expected = new ExerciseResponse(
				newExercise().getId(),
				"Squat",
				ExerciseCategory.LEGS,
				"The squat is a fundamental lower-body strength exercise that involves lowering and raising the hips, simulating the act of sitting. It strengthens the thighs, glutes, and abdominals, requiring stability and proper technique to prevent injury."
		);
		ExerciseRequest response = new ExerciseRequest(exercise.getName(), ExerciseCategory.LEGS, exercise.getDescription());
		
		when(userRepository.findByEmail("ricardo@gmail.com")).thenReturn(Optional.of(authenticatedUser));
		when(exerciseMapper.toExerciseResponse(any(Exercise.class))).thenReturn(expected);
		
		ExerciseResponse result = exerciseService.createExercise(response);
		
		assertEquals("Squat", result.name());
	}
	
	@Test
	public void findAllExercises_returnsEmptyList() {
		
		when(exerciseRepository.findAll()).thenReturn(List.of());
		
		List<ExerciseResponse> result = exerciseService.findAllExercises();
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void findExercise_notFound() {
		UUID id = UUID.randomUUID();
		
		when(exerciseRepository.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> {
			exerciseService.findExercise(id);
		});
	}
	
	@Test
	public void findByCategory_success() {
		Exercise exercise = newExercise();
		ExerciseResponse expected = new ExerciseResponse(exercise.getId(), exercise.getName(), exercise.getCategory(), exercise.getDescription());
		
		when(exerciseMapper.toExerciseResponse(any(Exercise.class))).thenReturn(expected);
		when(exerciseRepository.findByCategory(ExerciseCategory.LEGS)).thenReturn(List.of(exercise));
		
		List<ExerciseResponse> listCategory = exerciseService.findByCategory(ExerciseCategory.LEGS);
		
		assertFalse(listCategory.isEmpty());
	}
}
