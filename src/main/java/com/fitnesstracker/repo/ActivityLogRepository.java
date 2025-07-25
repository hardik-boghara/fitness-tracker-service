package com.fitnesstracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitnesstracker.domain.ActivityLog;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
//	List<ActivityLog> findByUserId(Long userId);

	@Modifying
	@Query("DELETE FROM ActivityLog l WHERE l.id=:logId")
	int deleteActivityLogById(Long logId);
}
