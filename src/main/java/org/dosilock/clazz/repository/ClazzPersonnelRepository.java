package org.dosilock.clazz.repository;

import java.util.List;

import org.dosilock.clazz.entity.ClazzPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;

public interface ClazzPersonnelRepository extends JpaRepository<ClazzPersonnel, Long> {

	int countByClazzId(@Param("clazzId") Long clazzId);

	ClazzPersonnel findByClazzIdAndMemberId(Long clazzId, Long memberId);

	List<ClazzPersonnel> findByClazzId(Long clazzId);

	// @Query("SELECT CASE WHEN COUNT(cp) > 0 THEN true ELSE false END FROM ClazzPersonnel cp WHERE cp.clazz.id = :clazzId AND cp.member.id = :memberId")
	// boolean findByClazzIdAndMemberId(long clazzId, long memberId);
}
