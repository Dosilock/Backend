package org.dosilock.response;

import org.dosilock.member.entity.AuthEnum;
import org.dosilock.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ResponseMemberDto {
	private String email;
	private String nickname;
	private String profileImg;
	private AuthEnum loginType;

	public ResponseMemberDto(Member member) {
		this.email = member.getEmail();
		this.nickname = member.getNickname();
		this.profileImg = member.getProfileImg();
		this.loginType = member.getLoginType();
	}
}
