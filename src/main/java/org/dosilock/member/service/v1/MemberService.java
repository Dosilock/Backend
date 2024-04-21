package org.dosilock.member.service.v1;

import static org.dosilock.utils.CommonUtils.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.dosilock.member.entity.Member;
import org.dosilock.member.redis.MemberRedis;
import org.dosilock.member.repository.MemberRedisRepository;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.request.RequestMemberDto;
import org.dosilock.response.ResponseMemberDto;
import org.dosilock.utils.CommonUtils;
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

	public void signUp(RequestMemberDto requestMemberDto) {
		requestMemberDto.encodePassword(CommonUtils::hashPassword);

		String randomLinkCode = RandomStringUtils.randomAlphabetic(10);
		emailUtils.sendSingupMessage(requestMemberDto.getEmail(), randomLinkCode);

		try {
			MemberRedis memberRedis = MemberRedis.builder()
				.userData(new ObjectMapper().writeValueAsString(requestMemberDto))
				.isEmailVerified(false)
				.link(randomLinkCode)
				.build();
			memberRedisRepository.save(memberRedis);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public void confirmEmailVerification(String link) {
		MemberRedis memberRedis = memberRedisRepository.findByLink(link);
		try {
			Member member = new Member(new ObjectMapper().readValue(memberRedis.getUserData(), RequestMemberDto.class));
			memberRepository.save(member);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
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
