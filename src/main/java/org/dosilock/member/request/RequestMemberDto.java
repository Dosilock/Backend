package org.dosilock.member.request;

import java.util.function.Function;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "이메일 회원 가입 DTO")
public class RequestMemberDto {

	@Schema(description = "이메일", example = "example@gmail.com")
	private String email;

	@Schema(description = "비밀번호", example = "password")
	private String password;

	@Schema(description = "닉네임", example = "nickname")
	@NotNull(message = "닉네임을 작성해주세요.")
	private String nickname;

	@Schema(description = "프로필 사진", example = "https://example.com/image.png")
	private String profileImg;

	@Schema(description = "링크 (고유번호)", example = "link")
	@NotNull(message = "링크를 작성해주세요.")
	private String link;

	public void encodePassword(Function<String, String> passwordEncoder) {
		this.password = passwordEncoder.apply(password);
	}
}
