package org.dosilock.timetable.repository;

import org.dosilock.clazz.entity.Clazz;
import org.dosilock.timetable.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
	int countByClazzAndMember(Clazz clazz, Long memberId);
}
