package org.dosilock.clazz.repository;

import java.util.List;

import org.dosilock.clazz.entity.Clazz;
import org.dosilock.clazz.entity.FocusTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FocusTimeRepository extends JpaRepository<FocusTime, Long> {

	List<FocusTime> findByClazz(Clazz clazz);

}
