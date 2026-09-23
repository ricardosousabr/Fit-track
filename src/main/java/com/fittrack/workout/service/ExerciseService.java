package com.fittrack.workout.service;

import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.workout.domain.Exercise;
import com.fittrack.workout.domain.ExerciseCategory;
import com.fittrack.workout.domain.WorkoutLog;
import com.fittrack.workout.domain.WorkoutSetLog;
import com.fittrack.workout.dto.ExerciseHistoryResponse;
import com.fittrack.workout.dto.ExerciseRequest;
import com.fittrack.workout.dto.ExerciseResponse;
import com.fittrack.workout.dto.SetHistoryResponse;
import com.fittrack.workout.mapper.ExerciseMapper;
import com.fittrack.workout.repository.ExerciseRepository;
import com.fittrack.workout.repository.WorkoutSetLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {
	private final ExerciseRepository exerciseRepository;
	private final UserRepository userRepository;
	private final ExerciseMapper exerciseMapper;
	private final WorkoutSetLogRepository workoutSetLogRepository;
	
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
	
	public ExerciseResponse findExercise(UUID id) {
		Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));
		
		return exerciseMapper.toExerciseResponse(exercise);
	}
	
	public List<ExerciseResponse> findByCategory(ExerciseCategory request) {
		List<Exercise> listCategory = exerciseRepository.findByCategory(request);
		
		return listCategory.stream().map(exerciseMapper::toExerciseResponse).toList();
	}
	
	public List<ExerciseHistoryResponse> getExerciseHistory(UUID exerciseId) {
		User user = getUserAuthenticated();
		List<WorkoutSetLog> sets = workoutSetLogRepository
				.findByWorkoutExercise_Exercise_IdAndWorkoutLog_User_Id(exerciseId, user.getId());
		
		Map<WorkoutLog, List<WorkoutSetLog>> grouped = sets.stream()
				.collect(Collectors.groupingBy(s -> s.getWorkoutLog()));
		
		return grouped.entrySet().stream().map(entry -> {
			WorkoutLog log = entry.getKey();
			List<WorkoutSetLog> logSets = entry.getValue();
			
			List<SetHistoryResponse> setResponses = logSets.stream().map(set ->
					new SetHistoryResponse(
							set.getSetNumber(),
							set.getWeightDone(),
							set.getRepsDone(),
							set.getRpe(),
							set.getCompleted()
					)
			).toList();
			
			return new ExerciseHistoryResponse(log.getLoggedAt(), log.getNotes(), setResponses);
		}).toList();
	}
}
