package org.dosilock.member.repository;

import org.dosilock.member.redis.MemberRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRedisRepository extends CrudRepository<MemberRedis, String> {
	MemberRedis findByLink(String link);
}
