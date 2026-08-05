package com.fittrack.workout.service;

import com.fittrack.user.domain.User;
import com.fittrack.user.repository.UserRepository;
import com.fittrack.workout.domain.WorkoutPlan;
import com.fittrack.workout.dto.WorkoutPlanRequest;
import com.fittrack.workout.dto.WorkoutPlanResponse;
import com.fittrack.workout.mapper.WorkoutPlanMapper;
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
public class WorkoutPlanService {
	private final UserRepository userRepository;
	private final WorkoutPlanRepository workoutPlanRepository;
	private final WorkoutPlanMapper workoutPlanMapper;
	
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
	
	public WorkoutPlanResponse create(WorkoutPlanRequest request) {
		User user = getUserAuthenticated();
		WorkoutPlan workoutPlan = new WorkoutPlan();
		
		workoutPlan.setName(request.recordName());
		workoutPlan.setDescription(request.description());
		workoutPlan.setUser(user);
		workoutPlan.setStartDate(request.startDate());
		workoutPlan.setEndDate(request.endDate());
		workoutPlan.setActive(true);
		
		workoutPlanRepository.save(workoutPlan);
		
		return workoutPlanMapper.toWorkoutPlanResponse(workoutPlan);
		
	}
	
	public List<WorkoutPlanResponse> findAll() {
		User user = getUserAuthenticated();
		List<WorkoutPlan> list = workoutPlanRepository.findByUserId(user.getId());
		
		return list.stream().map(workoutPlanMapper::toWorkoutPlanResponse).toList();
	}
	
	public WorkoutPlanResponse findById(UUID id) {
		User user = getUserAuthenticated();
		WorkoutPlan record = workoutPlanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
		checkOwnership(record, user);
		
		return workoutPlanMapper.toWorkoutPlanResponse(record);
	}
	
	public WorkoutPlanResponse update(UUID id, WorkoutPlanRequest request){
		User user = getUserAuthenticated();
		WorkoutPlan record = workoutPlanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
		checkOwnership(record, user);
		
		record.setName(request.recordName());
		record.setDescription(request.description());
		record.setStartDate(request.startDate());
		record.setEndDate(request.endDate());
		
		workoutPlanRepository.save(record);
		
		return workoutPlanMapper.toWorkoutPlanResponse(record);
	}
	
	public void deactivate(UUID id) {
		User user = getUserAuthenticated();
		WorkoutPlan record = workoutPlanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
		checkOwnership(record, user);
		
		record.setActive(false);
		
		workoutPlanRepository.save(record);
	}
}
