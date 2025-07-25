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

import com.fitnesstracker.model.ActivityLogDTO;
import com.fitnesstracker.model.ApiResponse;
import com.fitnesstracker.service.ActivityLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/logs")
@RequiredArgsConstructor
public class ActivityLogController {

	private final ActivityLogService activityLogService;

	@PostMapping("/add-activity")
	public ResponseEntity<ApiResponse<ActivityLogDTO>> createLog(@Valid @RequestBody ActivityLogDTO activityProxy) {
		return ResponseEntity.ok(new ApiResponse<ActivityLogDTO>(true, "New Activity Added", activityLogService.createActivityLog(activityProxy)));
	}

	@GetMapping("/get-activity/{id}")
	public ResponseEntity<ApiResponse<ActivityLogDTO>> getLog(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(true, "Successfull get Log", activityLogService.getActivityLogById(id)));
	}

	@GetMapping("/get-all-activity")
	public ResponseEntity<ApiResponse<List<ActivityLogDTO>>> getAllLogs() {
		return ResponseEntity.ok(new ApiResponse<>(true, "Successfull get All Log", activityLogService.getAllActivityLogs()));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<ActivityLogDTO>> updateLog(@PathVariable Long id, @Valid @RequestBody ActivityLogDTO activityLog) {
		return ResponseEntity.ok(new ApiResponse<ActivityLogDTO>(true, "Activity Updated", activityLogService.updateActivityLog(id, activityLog)));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse<String>> deleteLog(@PathVariable Long id) {
		String message = activityLogService.deleteActivityLog(id);
		return ResponseEntity.ok(new ApiResponse<>(true, message));
	}
}
