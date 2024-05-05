package org.dosilock.clazz.repository;

import org.dosilock.clazz.entity.ClazzPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClazzPersonnelRepository extends JpaRepository<ClazzPersonnel, Long> {
	int countByMemberIdAndClazzId(long memberId, Long clazzId);

	@Query("SELECT CASE WHEN COUNT(cp) > 0 THEN true ELSE false END FROM ClazzPersonnel cp WHERE cp.clazz.id = :clazzId AND cp.member.id = :memberId")
	boolean findByClazzIdAndMemberId(long clazzId, long memberId);
}
