package org.dosilock.member.service.v1;

import static org.dosilock.utils.CommonUtils.*;

import org.dosilock.member.entity.Member;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.request.RequestMemberDto;
import org.dosilock.response.ResponseMemberDto;
import org.dosilock.utils.EmailUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final EmailUtils emailUtils;

	public ResponseMemberDto signUp(RequestMemberDto requestMemberDto) {
		Member member = new Member(requestMemberDto);
		member.updatePassword(hashPassword(requestMemberDto.getPassword()));
		sendVerificationEmail(requestMemberDto.getEmail());
		return new ResponseMemberDto(memberRepository.save(member));
	}

	public void sendVerificationEmail(String email) {
		emailUtils.sendSimpleMessage("hpky123@gmail.com", "Title TEST", "Context TEST");
	}

	public void confirmEmailVerification(String email) {
		// 이메일 인증 성공 할 경우
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
