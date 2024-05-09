package org.dosilock.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "이메일 로그인 DTO")
public class RequestMemberSigninDto {

	@Schema(description = "이메일", required = true)
	private String email;

	@Schema(description = "비밀번호", required = true)
	private String password;
}
