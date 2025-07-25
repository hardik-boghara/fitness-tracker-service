package com.fitnesstracker.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fitnesstracker.domain.User;
import com.fitnesstracker.domain.WorkoutPlan;
import com.fitnesstracker.exception.ResourceNotFoundException;
import com.fitnesstracker.model.WorkoutPlanDTO;
import com.fitnesstracker.repo.WorkoutPlanRepository;
import com.fitnesstracker.service.WorkoutPlanService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

	private final WorkoutPlanRepository workoutPlanRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public WorkoutPlanDTO createWorkoutPlan(WorkoutPlanDTO workPlanProxy) {

		ModelMapper mapper = new ModelMapper();
		WorkoutPlan workPlan = mapper.map(workPlanProxy, WorkoutPlan.class);
		workPlan.setUser(User.builder().id(workPlanProxy.getUserId()).build());
		workoutPlanRepository.save(workPlan);
		return workPlanProxy;
	}

	@Override
	public WorkoutPlanDTO getWorkoutPlanById(Long id) {

		WorkoutPlan workoutPlan = workoutPlanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Workout Plan not found"));

		ModelMapper mapper = new ModelMapper();
		WorkoutPlanDTO workoutProxy = mapper.map(workoutPlan, WorkoutPlanDTO.class);

		return workoutProxy;
	}

	@Override
	public List<WorkoutPlanDTO> getAllWorkoutPlans() {
		List<WorkoutPlan> workoutList = workoutPlanRepository.findAll();

		if (workoutList == null || workoutList.isEmpty()) {
			throw new ResourceNotFoundException("WorkoutPlan data is Not available");
		}

		List<WorkoutPlanDTO> workoutProxyList = new ArrayList<>(workoutList.size());
		ModelMapper mapper = new ModelMapper();

		workoutList.forEach(workoutPlan -> {
			WorkoutPlanDTO WorkoutPlanProxy = mapper.map(workoutPlan, WorkoutPlanDTO.class);
			workoutProxyList.add(WorkoutPlanProxy);
		});

		return workoutProxyList;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public WorkoutPlanDTO updateWorkoutPlan(Long id, WorkoutPlanDTO workoutProxy) {
		WorkoutPlan workoutPlan = workoutPlanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Workout Plan not found"));

		workoutPlan.setName(workoutProxy.getName());
		workoutPlan.setDescription(workoutProxy.getDescription());
		workoutPlan.setDurationInDays(workoutProxy.getDurationInDays());
		workoutPlan.setUser(User.builder().id(workoutProxy.getUserId()).build());

		workoutPlanRepository.save(workoutPlan);
		return workoutProxy;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String deleteWorkoutPlan(Long id) {

		int deletedRows = workoutPlanRepository.deleteWorkoutById(id);
		if (deletedRows > 0) {
			return "Workout Plan Deleted";
		}
		return "No Matching Workout PlanFound";
	}
}
