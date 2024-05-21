package org.dosilock.socket.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NamespaceRedisRepository extends CrudRepository<NamespaceRedis, String> {

}
