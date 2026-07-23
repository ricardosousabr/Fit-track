package com.fittrack.workout.service;

import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.workout.domain.Exercise;
import com.fittrack.workout.domain.ExerciseCategory;
import com.fittrack.workout.dto.ExerciseRequest;
import com.fittrack.workout.dto.ExerciseResponse;
import com.fittrack.workout.mapper.ExerciseMapper;
import com.fittrack.workout.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
	private final ExerciseRepository exerciseRepository;
	private final UserRepository userRepository;
	private final ExerciseMapper exerciseMapper;
	
	private User getUserAuthenticated() {
		UserDetails userAuthenticated = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(userAuthenticated.getUsername()).orElseThrow();
		
		return user;
	}
	
	public ExerciseResponse createExercise(ExerciseRequest request) {
		User user = getUserAuthenticated();
		Exercise newExercise = new Exercise();
		
		newExercise.setCreatedBy(user);
		newExercise.setName(request.name());
		newExercise.setCategory(request.category());
		newExercise.setDescription(request.description());
		
		exerciseRepository.save(newExercise);
		
		return exerciseMapper.toExerciseResponse(newExercise);
	}
	
	public List<ExerciseResponse> findAllExercises() {
		List<Exercise> list = exerciseRepository.findAll();
		
		return list.stream().map(exerciseMapper::toExerciseResponse).toList();
	}
	
	public List<ExerciseResponse> findByCategory(ExerciseCategory request) {
		List<Exercise> listCategory = exerciseRepository.findByCategory(request);
		
		return listCategory.stream().map(exerciseMapper::toExerciseResponse).toList();
	}
}
