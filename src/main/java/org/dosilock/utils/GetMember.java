package org.dosilock.utils;

import org.dosilock.exception.ErrorMessage;
import org.dosilock.exception.ErrorResponseDto;
import org.dosilock.exception.UserErrorException;
import org.dosilock.member.entity.Member;
import org.dosilock.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetMember {

	private static MemberRepository memberRepository;

	@Autowired
	public void setMemberRepository(MemberRepository memberRepository) {
		GetMember.memberRepository = memberRepository;
	}

	public static Member getMember() {
		return memberRepository.findByEmail(
			SecurityContextHolder.getContext()
				.getAuthentication().getName()
		).orElseThrow(() -> new UserErrorException(new ErrorResponseDto(ErrorMessage.USER_NOT_FOUND)));
	}
}
