package org.dosilock.member.controller;

import org.dosilock.member.service.v1.MemberService;
import org.dosilock.request.RequestMemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<Void> signUp(@Valid @RequestBody RequestMemberDto requestMemberDto) {
		memberService.signUp(requestMemberDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup/link/{linkCode}")
	public ResponseEntity<Void> link(@PathVariable String linkCode) {
		memberService.confirmEmailVerification(linkCode);
		return ResponseEntity.ok().build();
	}
}
