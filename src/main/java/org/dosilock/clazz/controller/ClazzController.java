package org.dosilock.clazz.controller;

import org.dosilock.clazz.request.ClazzRequest;
import org.dosilock.clazz.response.ClazzResponse;
import org.dosilock.clazz.service.ClazzService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	@Operation(summary = "방 생성 API", description = "반 만들기(이름, 아이콘, 설명), 템플릿, 시간표(이름, 요일, 시간:교시:휴식, 출첵 여부), 세부 설정(교시 이름, 출첵 여부, 학습법)")
	@PostMapping()
	public ResponseEntity<ClazzResponse> addClazz(@Valid @RequestBody ClazzRequest clazzRequest) throws Exception {
		ClazzResponse clazzResponse = clazzService.addClazz(clazzRequest);
		return ResponseEntity.ok(clazzResponse);
	}

	@Operation(summary = "반 목록 API", description = "반목록, 참여중인 인원 수")
	@GetMapping()
	public ResponseEntity<Object> getClazz() {
		clazzService.getClazz();
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "반 초대 링크 API", description = "반에 대한 링크 생성")
	@GetMapping("/link")
	public ResponseEntity<Object> getLink() {
		clazzService.getClazzLink();
		return ResponseEntity.ok().build();
	}

}
