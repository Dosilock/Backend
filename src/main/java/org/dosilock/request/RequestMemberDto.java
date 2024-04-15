package org.dosilock.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestMemberDto {
	private String email;
	private String password;
	private String nickname;
	private String profileImg;
	private Integer loginType;
}
