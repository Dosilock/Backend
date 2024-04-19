package org.dosilock.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class RequestMemberDto {
	@NotNull(message = "이메일을 작성해주세요.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
	private String password;
	@NotNull(message = "별명을 작성해주세요.")
	private String nickname;
	private String profileImg;
	@NotNull(message = "로그인 형식이 없습니다.")
	private Integer loginType;
}
