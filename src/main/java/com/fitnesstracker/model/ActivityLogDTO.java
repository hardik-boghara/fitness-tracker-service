package com.fitnesstracker.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityLogDTO {

	private Long id;
	
	@NotNull(message = "Date is required")
	private LocalDate activityDate;

	@NotBlank(message = "Activity description is required")
	private String activityDescription;

	@NotNull(message = "Duration is required")
	private Integer durationInMinutes;

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotNull(message = "Workout Plan ID is required")
	private Long workoutPlanId;
}
