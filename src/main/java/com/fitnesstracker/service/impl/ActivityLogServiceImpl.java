package com.fitnesstracker.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fitnesstracker.domain.ActivityLog;
import com.fitnesstracker.domain.User;
import com.fitnesstracker.domain.WorkoutPlan;
import com.fitnesstracker.exception.ResourceNotFoundException;
import com.fitnesstracker.model.ActivityLogDTO;
import com.fitnesstracker.repo.ActivityLogRepository;
import com.fitnesstracker.repo.UserRepository;
import com.fitnesstracker.repo.WorkoutPlanRepository;
import com.fitnesstracker.service.ActivityLogService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

	private final ActivityLogRepository activityLogRepository;
	private final UserRepository userRepository;
	private final WorkoutPlanRepository workoutPlanRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ActivityLogDTO createActivityLog(ActivityLogDTO activityProxy) {
		Long userId = workoutPlanRepository.findUserIdById(activityProxy.getWorkoutPlanId());

		if (userId == null) {
			throw new ResourceNotFoundException("User not found with workout-plan id: " + activityProxy.getWorkoutPlanId());
		} else if (userId != activityProxy.getUserId()) {
			throw new ResourceNotFoundException("User not found with id: " + activityProxy.getUserId());
		}

		ActivityLog activityLog = new ActivityLog(); 
		BeanUtils.copyProperties(activityProxy, activityLog);
		activityLog.setUser(User.builder().id(activityProxy.getUserId()).build());
		activityLog.setWorkoutPlan(WorkoutPlan.builder().id(activityProxy.getWorkoutPlanId()).build());

		activityLogRepository.save(activityLog);

		return activityProxy;
	}

	@Override
	public ActivityLogDTO getActivityLogById(Long id) {
		ActivityLog activity = activityLogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Activity Log not found"));

		ModelMapper mapper = new ModelMapper();
		ActivityLogDTO activityProxy = mapper.map(activity, ActivityLogDTO.class);
		return activityProxy;
	}

	@Override
	public List<ActivityLogDTO> getAllActivityLogs() {
		List<ActivityLog> activityList = activityLogRepository.findAll();

		if (activityList == null || activityList.isEmpty()) {
			throw new ResourceNotFoundException("Activity is Not available");
		}

		List<ActivityLogDTO> activityProxyList = new ArrayList<>(activityList.size());
		ModelMapper mapper = new ModelMapper();

		activityList.forEach(activity -> {
			ActivityLogDTO logProxy = mapper.map(activity, ActivityLogDTO.class);
			activityProxyList.add(logProxy);
		});

		return activityProxyList;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ActivityLogDTO updateActivityLog(Long id, ActivityLogDTO activityProxy) {
		ActivityLog log = activityLogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Activity Log not found"));

		User user = userRepository.findById(activityProxy.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		WorkoutPlan plan = workoutPlanRepository.findById(activityProxy.getWorkoutPlanId())
				.orElseThrow(() -> new ResourceNotFoundException("Workout Plan not found"));

		log.setActivityDate(activityProxy.getActivityDate());
		log.setActivityDescription(activityProxy.getActivityDescription());
		log.setDurationInMinutes(activityProxy.getDurationInMinutes());
		log.setUser(user);
		log.setWorkoutPlan(plan);

		activityLogRepository.save(log);
		return activityProxy;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String deleteActivityLog(Long id) {
		int deletedRows = activityLogRepository.deleteActivityLogById(id);
		if (deletedRows > 0) {
			return "Activity Deleted";
		}
		return "No Matching Activity Found";
	}
}
