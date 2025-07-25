package com.fitnesstracker.service;

import java.util.List;

import com.fitnesstracker.model.WorkoutPlanDTO;

public interface WorkoutPlanService {
	WorkoutPlanDTO createWorkoutPlan(WorkoutPlanDTO dto);

	WorkoutPlanDTO getWorkoutPlanById(Long id);

	List<WorkoutPlanDTO> getAllWorkoutPlans();

	WorkoutPlanDTO updateWorkoutPlan(Long id, WorkoutPlanDTO dto);

	String deleteWorkoutPlan(Long id);
}
