package com.fitnesstracker.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitnesstracker.domain.WorkoutPlan;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
	List<WorkoutPlan> findByUserId(Long userId);

	@Query("SELECT work.user.id FROM WorkoutPlan AS work WHERE work.id=:workoutId")
	Long findUserIdById(Long workoutId);

	@Modifying
	@Query("DELETE FROM WorkoutPlan w WHERE w.id=:planId")
	int deleteWorkoutById(Long planId);
}
