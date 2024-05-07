package org.dosilock.member.controller;

import org.dosilock.jwt.JwtToken;
import org.dosilock.jwt.JwtTokenProvider;
import org.dosilock.member.request.RequestMemberDto;
import org.dosilock.member.request.RequestMemberEmailDto;
import org.dosilock.member.response.ResponseMemberDto;
import org.dosilock.member.service.v1.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@CrossOrigin
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@Operation(summary = "로그인 API", description = "이메일 로그인")
	@PostMapping(value = "signin")
	public ResponseEntity<Void> signin(HttpServletResponse httpServletResponse,
		@RequestBody RequestMemberDto requestMemberDto) {
		JwtToken jwtToken = memberService.signin(requestMemberDto);
		jwtTokenProvider.createCookieAccessToken(jwtToken.getAccessToken(), httpServletResponse);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "회원가입 API", description = "이메일 회원가입")
	@PostMapping(value = "signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody RequestMemberEmailDto requestMemberEmailDto) {
		memberService.signup(requestMemberEmailDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "회원가입 승인 이메일 전달 API", description = "이메일 회원가입 승인 이메일 전달")
	@PostMapping(value = "signup/confirm")
	public ResponseEntity<Void> confirmEmailVerification(@Valid @RequestBody RequestMemberDto requestMemberDto) {
		memberService.confirmEmailVerification(requestMemberDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "내 정보 API", description = "자신의 정보 확인")
	@PostMapping(value = "mypage")
	public ResponseEntity<ResponseMemberDto> myPage() {
		return ResponseEntity.ok(memberService.myPage());
	}

	@Operation(summary = "비밀번호 변경 승인 이메일 요청 API", description = "비밀번호 변경을 위한 이메일 승인 요청")
	@PostMapping(value = "password")
	public ResponseEntity<Void> changePassword(@RequestBody RequestMemberEmailDto requestMemberEmailDto) {
		memberService.changePassword(requestMemberEmailDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "비밀번호 변경 이메일 요청 API", description = "비밀번호 변경 이메일 승인 요청")
	@PutMapping(value = "password/confirm")
	public ResponseEntity<Void> confirmChangePassword(@RequestBody RequestMemberDto requestMemberDto) {
		memberService.confirmChangePassword(requestMemberDto);
		return ResponseEntity.ok().build();
	}
}
