package com.fitnesstracker.service;

import java.util.List;

import com.fitnesstracker.model.ActivityLogDTO;

public interface ActivityLogService {
	ActivityLogDTO createActivityLog(ActivityLogDTO dto);

	ActivityLogDTO getActivityLogById(Long id);

	List<ActivityLogDTO> getAllActivityLogs();

	ActivityLogDTO updateActivityLog(Long id, ActivityLogDTO dto);

	String deleteActivityLog(Long id);
}
