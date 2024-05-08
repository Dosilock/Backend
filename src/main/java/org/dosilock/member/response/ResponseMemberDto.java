package org.dosilock.member.response;

import org.dosilock.member.entity.AuthEnum;
import org.dosilock.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "회원 응답 DTO")
@AllArgsConstructor
@Getter
@Builder
public class ResponseMemberDto {
	
	@Schema(description = "이메일")
	private String email;

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "프로필 사진")
	private String profileImg;

	@Schema(description = "로그인 타입")
	private AuthEnum loginType;

	public ResponseMemberDto(Member member) {
		this.email = member.getEmail();
		this.nickname = member.getNickname();
		this.profileImg = member.getProfileImg();
		this.loginType = member.getLoginType();
	}
}
