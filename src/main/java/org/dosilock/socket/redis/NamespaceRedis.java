package org.dosilock.socket.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;

@RedisHash(value = "namespace")
@Builder
@Getter
public class NamespaceRedis {
	@Id
	private String name;
}
