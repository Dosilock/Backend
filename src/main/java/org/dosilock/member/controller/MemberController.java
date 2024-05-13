package org.dosilock.member.controller;

import org.dosilock.jwt.JwtToken;
import org.dosilock.jwt.JwtTokenProvider;
import org.dosilock.member.request.RequestMemberDto;
import org.dosilock.member.request.RequestMemberEmailDto;
import org.dosilock.member.request.RequestMemberSigninDto;
import org.dosilock.member.response.ResponseMemberDto;
import org.dosilock.member.response.ResponseMemberEmailDto;
import org.dosilock.member.service.v1.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@Operation(summary = "이메일 로그인 API", description = "이메일 로그인")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@PostMapping(value = "signin")
	public ResponseEntity<Void> signin(HttpServletResponse httpServletResponse,
		@RequestBody RequestMemberSigninDto requestMemberSigninDto) {
		JwtToken jwtToken = memberService.signin(requestMemberSigninDto);
		jwtTokenProvider.createCookieAccessToken(jwtToken.getAccessToken(), httpServletResponse);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "회원가입 이메일 요청 API", description = "회원가입 할 이메일에 인증 메일 전달")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@PostMapping(value = "signup")
	public ResponseEntity<Void> signup(@RequestBody @Valid RequestMemberEmailDto requestMemberEmailDto) {
		memberService.signup(requestMemberEmailDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "회원가입 링크 유효성 검사 API", description = "인증 메일에 대한 링크값이 존재하는지 검색")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@GetMapping(value = "signup/link/{link}")
	public ResponseEntity<ResponseMemberEmailDto> signup(@PathVariable String link) {
		return ResponseEntity.ok(memberService.linkVerify(link));
	}

	@Operation(summary = "회원가입 이메일 승인 후 저장 API", description = "이메일 회원가입 승인 후 저장")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@PostMapping(value = "signup/confirm")
	public ResponseEntity<Void> confirmEmailVerification(@RequestBody @Valid RequestMemberDto requestMemberDto) {
		memberService.confirmEmailVerification(requestMemberDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "비밀번호 변경 승인 이메일 요청 API", description = "비밀번호 변경을 위한 이메일 승인 요청")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@PostMapping(value = "password")
	public ResponseEntity<Void> changePassword(@RequestBody RequestMemberEmailDto requestMemberEmailDto) {
		memberService.changePassword(requestMemberEmailDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "내 정보 API", description = "자신의 정보 확인")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseMemberDto.class)))
	})
	@PostMapping(value = "mypage")
	public ResponseEntity<ResponseMemberDto> myPage() {
		return ResponseEntity.ok(memberService.myPage());
	}

	@Operation(summary = "비밀번호 변경 이메일 요청 API", description = "비밀번호 변경 이메일 승인 요청")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@PutMapping(value = "password/confirm")
	public ResponseEntity<Void> confirmChangePassword(@RequestBody RequestMemberDto requestMemberDto) {
		memberService.confirmChangePassword(requestMemberDto);
		return ResponseEntity.ok().build();
	}
}
