package org.dosilock.clazz.repository;

import java.util.List;

import org.dosilock.clazz.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {

	List<Clazz> findByMemberId(Long memberId);

	Clazz findByClazzLink(String link);
}
