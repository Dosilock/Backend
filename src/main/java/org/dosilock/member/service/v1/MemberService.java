package org.dosilock.member.service.v1;

import static org.dosilock.utils.CommonUtils.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.dosilock.member.entity.Member;
import org.dosilock.member.redis.MemberRedis;
import org.dosilock.member.repository.MemberRedisRepository;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.request.RequestMemberDto;
import org.dosilock.response.ResponseMemberDto;
import org.dosilock.utils.EmailUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberRedisRepository memberRedisRepository;
	private final EmailUtils emailUtils;

	public void signUp(RequestMemberDto requestMemberDto) throws JsonProcessingException {

		// 비밀번호 단방향 변경
		requestMemberDto.setPassword(hashPassword(requestMemberDto.getPassword()));

		// 메일 전달
		emailUtils.sendSingupMessage(requestMemberDto.getEmail());

		// Redis에 저장
		MemberRedis memberRedis = MemberRedis.builder()
			.userData(new ObjectMapper().writeValueAsString(requestMemberDto))
			.isEmailVerified(false)
			.link(RandomStringUtils.randomAlphabetic(10))
			.build();
		memberRedisRepository.save(memberRedis);
	}

	public void confirmEmailVerification(String link) throws JsonProcessingException {
		MemberRedis memberRedis = memberRedisRepository.findByLink(link);
		Member member = new Member(new ObjectMapper().readValue(memberRedis.getUserData(), RequestMemberDto.class));
		memberRepository.save(member);
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
