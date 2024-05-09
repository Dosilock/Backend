package org.dosilock.member.service.v1;

import org.dosilock.jwt.JwtToken;
import org.dosilock.jwt.JwtTokenProvider;
import org.dosilock.member.entity.Member;
import org.dosilock.member.redis.MemberRedis;
import org.dosilock.member.repository.MemberRedisRepository;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.member.request.RequestMemberDto;
import org.dosilock.member.request.RequestMemberEmailDto;
import org.dosilock.member.request.RequestMemberSigninDto;
import org.dosilock.member.response.ResponseMemberDto;
import org.dosilock.utils.EmailUtils;
import org.dosilock.utils.GetMember;
import org.dosilock.utils.InviteLink;
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

	private final InviteLink inviteLink;

	private Member member() {
		return GetMember.getMember();
	}

	@Transactional(readOnly = true)
	public JwtToken signin(RequestMemberSigninDto requestMemberSigninDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			requestMemberSigninDto.getEmail(),
			requestMemberSigninDto.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		return jwtTokenProvider.generateToken(authentication);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) {
		return memberRepository.findByEmail(email)
			.map(user -> User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.build())
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
	}

	@Transactional
	public void signup(RequestMemberEmailDto requestMemberEmailDto) {
		String randomLinkCode = inviteLink.createInveteLink();

		emailUtils.sendSignupMessage(requestMemberEmailDto.getEmail(), randomLinkCode);

		MemberRedis memberRedis = MemberRedis.builder()
			.email(requestMemberEmailDto.getEmail())
			.link(randomLinkCode)
			.build();
		memberRedisRepository.save(memberRedis);
	}

	@Transactional
	public void confirmEmailVerification(RequestMemberDto requestMemberDto) {
		MemberRedis memberRedis = memberRedisRepository.findByLink(requestMemberDto.getLink());
		requestMemberDto.setEmail(memberRedis.getEmail());
		requestMemberDto.encodePassword(passwordEncoder::encode);
		Member member = new Member(requestMemberDto);
		memberRepository.save(member);
	}

	@Transactional(readOnly = true)
	public ResponseMemberDto myPage() {
		return new ResponseMemberDto(member());
	}

	@Transactional
	public void changePassword(RequestMemberEmailDto requestMemberEmailDto) {
		String randomLinkCode = inviteLink.createInveteLink();
		emailUtils.sendChangePasswordMessage(requestMemberEmailDto.getEmail(), randomLinkCode);

		MemberRedis memberRedis = MemberRedis.builder()
			.email(requestMemberEmailDto.getEmail())
			.link(randomLinkCode)
			.build();
		memberRedisRepository.save(memberRedis);
	}

	@Transactional
	public void confirmChangePassword(RequestMemberDto requestMemberDto) {
		MemberRedis memberRedis = memberRedisRepository.findByLink(requestMemberDto.getLink());
		Member member = memberRepository.findByEmail(memberRedis.getEmail())
			.orElseThrow(() -> new UsernameNotFoundException("회원을 못찾음."));

		member.updatePassword(requestMemberDto.getPassword(), passwordEncoder::encode);
	}
}
