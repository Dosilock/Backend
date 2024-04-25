package org.dosilock.member.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Getter;

@RedisHash(value = "member", timeToLive = 900)
@Builder
@Getter
public class MemberRedis {
	@Id
	private String email;
	private String userData;
	@Indexed
	private String link;
}
