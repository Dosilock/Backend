package org.dosilock.member.service.v1;

import org.dosilock.exception.ErrorMessage;
import org.dosilock.exception.ErrorResponseDto;
import org.dosilock.exception.UserErrorException;
import org.dosilock.member.entity.Member;
import org.dosilock.member.redis.MemberRedis;
import org.dosilock.member.redis.MemberRedisRepository;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.member.request.RequestMemberDto;
import org.dosilock.member.request.RequestMemberEmailDto;
import org.dosilock.member.response.ResponseMemberDto;
import org.dosilock.member.response.ResponseMemberEmailDto;
import org.dosilock.utils.EmailUtils;
import org.dosilock.utils.GetMember;
import org.dosilock.utils.InviteLink;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberRedisRepository memberRedisRepository;

	private final EmailUtils emailUtils;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final PasswordEncoder passwordEncoder;

	private final InviteLink inviteLink;

	private Member member() {
		return GetMember.getMember();
	}

	@Transactional
	public void signup(RequestMemberEmailDto requestMemberEmailDto) {
		if (memberRepository.findByEmail(requestMemberEmailDto.getEmail()).stream().count() > 0) {
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.ALREADY_SIGN_UP_MEMBER));
		}

		String randomLinkCode = inviteLink.createInveteLink();

		emailUtils.sendSignupMessage(requestMemberEmailDto.getEmail(), randomLinkCode);

		MemberRedis memberRedis = MemberRedis.builder()
			.email(requestMemberEmailDto.getEmail())
			.link(randomLinkCode)
			.build();
		memberRedisRepository.save(memberRedis);
	}

	@Transactional(readOnly = true)
	public ResponseMemberEmailDto linkVerify(String link) {
		MemberRedis memberRedis = memberRedisRepository.findByLink(link);
		if (memberRedis == null)
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.LINK_NOT_FOUND));

		return new ResponseMemberEmailDto(memberRedis);
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
			.orElseThrow(() -> new UserErrorException(new ErrorResponseDto(ErrorMessage.USER_NOT_FOUND)));

		member.updatePassword(requestMemberDto.getPassword(), passwordEncoder::encode);
	}
}
