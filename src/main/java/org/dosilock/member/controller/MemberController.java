package org.dosilock.member.controller;

import org.dosilock.jwt.JwtToken;
import org.dosilock.jwt.JwtTokenProvider;
import org.dosilock.member.request.RequestMemberDto;
import org.dosilock.member.response.ResponseMemberDto;
import org.dosilock.member.service.v1.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<Void> signin(HttpServletResponse httpServletResponse,
		@RequestBody RequestMemberDto requestMemberDto) {
		JwtToken jwtToken = memberService.signin(requestMemberDto);
		jwtTokenProvider.createCookieAccessToken(jwtToken.getAccessToken(), httpServletResponse);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody RequestMemberDto requestMemberDto) {
		memberService.signup(requestMemberDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/signup/link/{linkCode}")
	public ResponseEntity<Void> confirmEmailVerification(@PathVariable String linkCode) {
		memberService.confirmEmailVerification(linkCode);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/mypage")
	public ResponseEntity<ResponseMemberDto> myPage(@AuthenticationPrincipal UserDetails userDetails) {
		return ResponseEntity.ok(memberService.myPage(userDetails.getUsername()));
	}

	@PostMapping("/password")
	public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody RequestMemberDto requestMemberDto) {
		memberService.changePassword(userDetails.getUsername(), requestMemberDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/password/link/{linkCode}")
	public ResponseEntity<Void> confirmChangePassword(@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable String linkCode) {
		memberService.confirmChangePassword(userDetails.getUsername(), linkCode);
		return ResponseEntity.ok().build();
	}
}
