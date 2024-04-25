package org.dosilock.member.service.v1;

import org.apache.commons.lang3.RandomStringUtils;
import org.dosilock.jwt.JwtToken;
import org.dosilock.jwt.JwtTokenProvider;
import org.dosilock.member.entity.Member;
import org.dosilock.member.redis.MemberRedis;
import org.dosilock.member.repository.MemberRedisRepository;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.request.RequestMemberDto;
import org.dosilock.utils.EmailUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final MemberRedisRepository memberRedisRepository;
	private final EmailUtils emailUtils;

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public JwtToken signin(RequestMemberDto requestMemberDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			requestMemberDto.getEmail(),
			requestMemberDto.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		return jwtTokenProvider.generateToken(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		return memberRepository.findByEmail(email)
			.map(user -> User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.build())
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
	}

	public void signup(RequestMemberDto requestMemberDto) {
		requestMemberDto.encodePassword(passwordEncoder::encode);

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

	public void myPage() {

	}

	public void changePassword() {

	}
}
