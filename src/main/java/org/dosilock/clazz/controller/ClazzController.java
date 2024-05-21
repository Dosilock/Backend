package org.dosilock.clazz.controller;

import java.util.List;

import org.dosilock.clazz.request.ClazzRequest;
import org.dosilock.clazz.response.ClazzInfoResponse;
import org.dosilock.clazz.response.ClazzLinkResponse;
import org.dosilock.clazz.response.ClazzListResponse;
import org.dosilock.clazz.response.ClazzMemberInfoResponse;
import org.dosilock.clazz.service.ClazzService;
import org.dosilock.exception.Swagger401StandardResponseDto;
import org.dosilock.exception.Swagger500StandardResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clazz/")
@CrossOrigin
public class ClazzController {

	private final ClazzService clazzService;

	@Operation(summary = "방 생성(링크 포함) API", description = "반 만들기(이름, 아이콘, 설명), 템플릿, 시간표(이름, 요일, 시간:교시:휴식, 출첵 여부), 세부 설정(교시 이름, 출첵 여부, 학습법) 후 반에 대한 링크 응답")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "401", description = "유효한 인증 거부", content = @Content(schema = @Schema(implementation = Swagger401StandardResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = Swagger500StandardResponseDto.class)))
	})
	@PostMapping()
	public ResponseEntity<ClazzLinkResponse> addClazz(
		@Valid @RequestBody ClazzRequest clazzRequest) {
		ClazzLinkResponse clazzLinkResponse = clazzService.addClazz(clazzRequest);
		return ResponseEntity.ok(clazzLinkResponse);
	}

	@Operation(summary = "반 목록 API", description = "반목록, 참여중인 인원 수, 나중에 실시간으로 현재 접속자 수")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "401", description = "유효한 인증 거부", content = @Content(schema = @Schema(implementation = Swagger401StandardResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = Swagger500StandardResponseDto.class)))
	})
	@GetMapping(value = "list")
	public ResponseEntity<List<ClazzListResponse>> getClazzList() {
		List<ClazzListResponse> clazzListResponses = clazzService.getClazzList();
		return ResponseEntity.ok(clazzListResponses);
	}

	@Operation(summary = "반 정보 API", description = "반에 대한 정보를 반환")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "401", description = "유효한 인증 거부", content = @Content(schema = @Schema(implementation = Swagger401StandardResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = Swagger500StandardResponseDto.class)))
	})
	@GetMapping(value = "info/link/{clazzId}")
	public ResponseEntity<ClazzInfoResponse> getClazzInfo(
		@PathVariable(value = "clazzId") String link) {
		ClazzInfoResponse clazzInfoResponse = clazzService.getClazzInfo(link);
		return ResponseEntity.ok(clazzInfoResponse);
	}

	@Operation(summary = "(멤버) 반 가입 신청 API", description = "0 - 방장, 1 - 멤버, 2 - 가입 승인 중 반 가입 조건을 확인후 안되면 에러 리턴")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "401", description = "유효한 인증 거부", content = @Content(schema = @Schema(implementation = Swagger401StandardResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = Swagger500StandardResponseDto.class)))
	})
	@PostMapping(value = "check/link/{clazzId}")
	public ResponseEntity<Void> checkMember(@PathVariable(value = "clazzId") String link) {
		clazzService.checkMemberAndInvete(link);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "(반장)반 가입 수락/거절 API", description = "방장이 가입 신청을 한 멤버를 수락/거절")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "401", description = "유효한 인증 거부", content = @Content(schema = @Schema(implementation = Swagger401StandardResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = Swagger500StandardResponseDto.class)))
	})
	@PostMapping(value = "join/link/{clazzId}")
	public ResponseEntity<Void> getInvite(@PathVariable(value = "clazzId") String link,
		@RequestParam(value = "isAccepted") Boolean isAccepted) {
		clazzService.checkAccept(link, isAccepted);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "반 멤버 가져오기 API", description = "")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "401", description = "유효한 인증 거부", content = @Content(schema = @Schema(implementation = Swagger401StandardResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = Swagger500StandardResponseDto.class)))
	})
	@GetMapping(value = "member/link/{clazzId}")
	public ResponseEntity<List<ClazzMemberInfoResponse>> getMemberInfo(
		@PathVariable(value = "clazzId") String link) {
		List<ClazzMemberInfoResponse> memberInfoResponseList = clazzService.getMemberInfo(link);
		return ResponseEntity.ok(memberInfoResponseList);
	}
}
