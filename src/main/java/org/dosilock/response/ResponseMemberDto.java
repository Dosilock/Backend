package org.dosilock.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ResponseMemberDto {
	private String email;
	private String password;
	private String nickname;
	private String profileImg;
	private Integer LoginType;
}
