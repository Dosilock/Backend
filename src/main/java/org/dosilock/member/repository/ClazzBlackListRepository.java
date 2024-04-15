package org.dosilock.member.repository;

import org.dosilock.member.entity.ClazzBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClazzBlackListRepository extends JpaRepository<ClazzBlackList, Long> {
}
