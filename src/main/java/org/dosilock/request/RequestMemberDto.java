package org.dosilock.request;

import java.util.function.Function;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestMemberDto {
	@NotNull(message = "이메일을 작성해주세요.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
	private String password;
	@NotNull(message = "별명을 작성해주세요.")
	private String nickname;
	private String profileImg;

	public void encodePassword(Function<String, String> passwordEncoder) {
		this.password = passwordEncoder.apply(password);
	}
}
