package org.dosilock.clazz.controller;

import java.util.List;

import org.dosilock.clazz.request.ClazzRequest;
import org.dosilock.clazz.response.ClazzListResponse;
import org.dosilock.clazz.response.ClazzResponse;
import org.dosilock.clazz.service.ClazzService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clazz/")
@CrossOrigin
public class ClazzController {

	private final ClazzService clazzService;

	@Operation(summary = "방 생성(링크 포함) API", description = "반 만들기(이름, 아이콘, 설명), 템플릿, 시간표(이름, 요일, 시간:교시:휴식, 출첵 여부), 세부 설정(교시 이름, 출첵 여부, 학습법) 후 반에 대한 링크 응답")
	@PostMapping()
	public ResponseEntity<ClazzResponse> addClazz(@Valid @RequestBody ClazzRequest clazzRequest) throws Exception {
		ClazzResponse clazzResponse = clazzService.addClazz(clazzRequest);
		return ResponseEntity.ok(clazzResponse);
	}

	@Operation(summary = "반 목록 API", description = "반목록, 참여중인 인원 수")
	@GetMapping()
	public ResponseEntity<List<ClazzListResponse>> getClazzList(Long memberId) throws Exception {
		List<ClazzListResponse> clazzListResponses = clazzService.getClazzList(memberId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "반 가입 API", description = "반에 대한 링크 확인 후 일치하면 입실")
	@GetMapping(value = "link")
	public ResponseEntity<Object> getLink() {
		clazzService.getClazzLink();
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "반 멤버 가져오기 API", description = "")
	@GetMapping(value = "memberInfo")
	public ResponseEntity<Object> getMemberInfo() {

		return ResponseEntity.ok().build();
	}

}
