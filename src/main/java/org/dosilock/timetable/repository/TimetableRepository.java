package org.dosilock.timetable.repository;

import org.dosilock.timetable.entity.Timetable;
import org.springframework.data.repository.CrudRepository;

public interface TimetableRepository extends CrudRepository<Timetable, Long> {
}
