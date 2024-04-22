package org.dosilock.member.controller;

import java.io.UnsupportedEncodingException;

import org.dosilock.jwt.JwtToken;
import org.dosilock.jwt.JwtTokenProvider;
import org.dosilock.member.service.v1.MemberService;
import org.dosilock.request.RequestMemberDto;
import org.dosilock.response.ResponseMemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<Void> signIn(HttpServletResponse httpServletResponse, @RequestBody RequestMemberDto requestMemberDto) {
		JwtToken jwtToken = memberService.signin(requestMemberDto);
		jwtTokenProvider.createCookieAccessToken(jwtToken.getAccessToken(), httpServletResponse);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signUp(@Valid @RequestBody RequestMemberDto requestMemberDto) {
		memberService.signup(requestMemberDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup/link/{linkCode}")
	public ResponseEntity<Void> link(@PathVariable String linkCode) {
		memberService.confirmEmailVerification(linkCode);
		return ResponseEntity.ok().build();
	}
}
