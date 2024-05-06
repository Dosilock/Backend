package org.dosilock.timetable.repository;

import java.util.List;

import org.dosilock.timetable.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
	List<Period> findByTimetableId(Long timeTableId);
}
