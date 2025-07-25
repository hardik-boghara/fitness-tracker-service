package com.fitnesstracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnesstracker.model.ApiResponse;
import com.fitnesstracker.model.WorkoutPlanDTO;
import com.fitnesstracker.service.WorkoutPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/workout/plans")
@RequiredArgsConstructor
public class WorkoutPlanController {

	private final WorkoutPlanService planService;

	@PostMapping
	public ResponseEntity<ApiResponse<WorkoutPlanDTO>> createPlan(@Valid @RequestBody WorkoutPlanDTO dto) {
		return ResponseEntity.ok(new ApiResponse<>(true, "New workout Added", planService.createWorkoutPlan(dto)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<WorkoutPlanDTO>> getPlan(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(true, "Successfully get workout Detail", planService.getWorkoutPlanById(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<WorkoutPlanDTO>>> getAllPlans() {
		return ResponseEntity.ok(new ApiResponse<>(true, "Successfully get all workout Details", planService.getAllWorkoutPlans()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<WorkoutPlanDTO>> updatePlan(@PathVariable Long id,
			@Valid @RequestBody WorkoutPlanDTO workoutProxy) {
		return ResponseEntity.ok(new ApiResponse<>(true, "Workout Plan Updated", planService.updateWorkoutPlan(id, workoutProxy)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deletePlan(@PathVariable Long id) {
		String message = planService.deleteWorkoutPlan(id);
		return ResponseEntity.ok(new ApiResponse<>(true, message));
	}
}
