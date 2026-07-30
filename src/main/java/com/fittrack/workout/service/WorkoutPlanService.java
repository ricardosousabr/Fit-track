package com.fittrack.workout.service;

import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.workout.domain.WorkoutPlan;
import com.fittrack.workout.dto.WorkoutPlanRequest;
import com.fittrack.workout.dto.WorkoutPlanResponse;
import com.fittrack.workout.mapper.WorkoutPlanMapper;
import com.fittrack.workout.repository.WorkoutPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutPlanService {
	private final UserRepository userRepository;
	private final WorkoutPlanRepository workoutPlanRepository;
	private final WorkoutPlanMapper workoutPlanMapper;
	
	private User getUserAuthenticated() {
		UserDetails userAuthenticated = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(userAuthenticated.getUsername()).orElseThrow();
		
		return user;
	}
	
	public WorkoutPlanResponse create(WorkoutPlanRequest request) {
		User user = getUserAuthenticated();
		WorkoutPlan workoutPlan = new WorkoutPlan();
		
		workoutPlan.setName(request.recordName());
		workoutPlan.setDescription(request.description());
		workoutPlan.setUser(user);
		workoutPlan.setStartDate(request.startDate());
		workoutPlan.setEndDate(request.endDate());
		
		
		workoutPlanRepository.save(workoutPlan);
		
		return workoutPlanMapper.toWorkoutPlanResponse(workoutPlan);
		
	}
}
