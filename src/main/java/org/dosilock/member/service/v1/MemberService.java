package org.dosilock.member.service.v1;

import static org.dosilock.utils.CommonUtils.*;

import org.dosilock.member.entity.Member;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.request.RequestMemberDto;
import org.dosilock.response.ResponseMemberDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public ResponseMemberDto signUp(RequestMemberDto requestMemberDto) {
		Member member = new Member(requestMemberDto);
		member.updatePassword(hashPassword(requestMemberDto.getPassword()));
		return new ResponseMemberDto(memberRepository.save(member));
	}

	public void sendVerificationEmail(String email) {
		// 이메일 확인
	}

	public ResponseMemberDto updateUser(RequestMemberDto requestMemberDto) {
		Member member = memberRepository.findByEmail(requestMemberDto.getEmail());
		member.updateUser(requestMemberDto);
		return new ResponseMemberDto(member);
	}

	public ResponseMemberDto deleteUser(RequestMemberDto requestMemberDto) {
		return null;
	}

	public ResponseMemberDto myPage(RequestMemberDto requestMemberDto) {
		return new ResponseMemberDto(memberRepository.findByEmail(requestMemberDto.getEmail()));
	}

	public ResponseMemberDto modifyPassword(RequestMemberDto requestMemberDto) {
		Member member = memberRepository.findByEmail(requestMemberDto.getEmail());
		member.updatePassword(hashPassword(requestMemberDto.getPassword()));
		return new ResponseMemberDto(member);
	}
}
