package org.dosilock.member.response;

import org.dosilock.member.redis.MemberRedis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "이메일 응답 DTO")
public class ResponseMemberEmailDto {

	@Schema(description = "이메일", required = true)
	private String email;

	public ResponseMemberEmailDto(MemberRedis memberRedis) {
		this.email = memberRedis.getEmail();
	}
}
