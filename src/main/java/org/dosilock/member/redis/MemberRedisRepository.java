package org.dosilock.member.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRedisRepository extends CrudRepository<MemberRedis, String> {
	MemberRedis findByLink(String link);
}
