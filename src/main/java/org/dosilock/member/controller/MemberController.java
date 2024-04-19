package org.dosilock.member.controller;

import org.dosilock.member.service.v1.MemberService;
import org.dosilock.request.RequestMemberDto;
import org.dosilock.response.ResponseMemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/api/v1/signup")
	public ResponseEntity<ResponseMemberDto> signUp(@Valid @RequestBody RequestMemberDto requestMemberDto) {
		return ResponseEntity.ok(memberService.signUp(requestMemberDto));
	}
}
