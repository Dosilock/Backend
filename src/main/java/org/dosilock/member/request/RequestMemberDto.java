package org.dosilock.member.request;

import java.util.function.Function;

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
	private String email;
	private String password;
	@NotNull(message = "별명을 작성해주세요.")
	private String nickname;
	private String profileImg;
	@NotNull(message = "링크를 작성해주세요.")
	private String link;

	public void encodePassword(Function<String, String> passwordEncoder) {
		this.password = passwordEncoder.apply(password);
	}
}
