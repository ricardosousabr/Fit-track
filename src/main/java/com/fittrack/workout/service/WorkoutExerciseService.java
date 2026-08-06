package com.fittrack.workout.service;

import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.workout.domain.Exercise;
import com.fittrack.workout.domain.WorkoutExercise;
import com.fittrack.workout.domain.WorkoutPlan;
import com.fittrack.workout.dto.WorkoutExerciseRequest;
import com.fittrack.workout.dto.WorkoutExerciseResponse;
import com.fittrack.workout.mapper.WorkoutExerciseMapper;
import com.fittrack.workout.repository.ExerciseRepository;
import com.fittrack.workout.repository.WorkoutExerciseRepository;
import com.fittrack.workout.repository.WorkoutPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {
	private final WorkoutExerciseRepository workoutExerciseRepository;
	private final WorkoutPlanRepository workoutPlanRepository;
	private final UserRepository userRepository;
	private final ExerciseRepository exerciseRepository;
	private final WorkoutExerciseMapper workoutExerciseMapper;
	
	private User getUserAuthenticated() {
		UserDetails userAuthenticated = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(userAuthenticated.getUsername()).orElseThrow();
		
		return user;
	}
	
	private void checkOwnership(WorkoutPlan record, User user) {
		if (!record.getUser().getId().equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
		}
	}
	
	public WorkoutExerciseResponse addExercise(UUID planId, UUID exerciseId, WorkoutExerciseRequest request) {
		User user = getUserAuthenticated();
		WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));
		checkOwnership(workoutPlan, user);
		Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));
		
		WorkoutExercise workoutExercise = new WorkoutExercise();
		
		workoutExercise.setExercise(exercise);
		workoutExercise.setReps(request.reps());
		workoutExercise.setSets(request.sets());
		workoutExercise.setRpe(request.rpe());
		workoutExercise.setWeight(request.weight());
		workoutExercise.setNotes(request.notes());
		workoutExercise.setOrderIndex(request.orderIndex());
		workoutExercise.setWorkoutPlan(workoutPlan);
		
		workoutExerciseRepository.save(workoutExercise);
		
		return workoutExerciseMapper.toWorkoutExercise(workoutExercise);
	}
	
	public void removeExercise(UUID planId, UUID exerciseId) {
		WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));
		User user = getUserAuthenticated();
		checkOwnership(workoutPlan, user);
		WorkoutExercise workoutExercise = workoutExerciseRepository
				.findByWorkoutPlanIdAndExerciseId(planId, exerciseId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found in this plan"));
		
		workoutExerciseRepository.delete(workoutExercise);
	}
	
	public List<WorkoutExerciseResponse> listExercises(UUID planId) {
		WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));
		User user = getUserAuthenticated();
		checkOwnership(workoutPlan, user);
		
		List<WorkoutExercise> list = workoutExerciseRepository.findByWorkoutPlanId(planId);
		
		return list.stream().map(workoutExerciseMapper::toWorkoutExercise).toList();
	}
}
