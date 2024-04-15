package org.dosilock.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseMemberDto {
	private String email;
	private String password;
	private String nickname;
	private String profileImg;
	private Integer LoginType;
}
