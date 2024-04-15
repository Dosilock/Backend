package org.dosilock.member.repository;

import org.dosilock.member.entity.MemberAbsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAbsentRepository extends JpaRepository<MemberAbsent, Long> {
}
