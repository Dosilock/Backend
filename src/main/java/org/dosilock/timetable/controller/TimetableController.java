package org.dosilock.timetable.controller;

import org.dosilock.timetable.service.TimetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timetable/")
public class TimetableController {

	private final TimetableService timetableService;

	@Operation(summary = "시간표 보기 API", description = "")
	@GetMapping(value = "info")
	public ResponseEntity<Object> getTimetableInfo() {
		
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "시간표 관리 API", description = "")
	@PostMapping(value = "manage")
	public ResponseEntity<Object> getTimetableManage() {

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "시간표 생성 API", description = "")
	@PostMapping()
	public ResponseEntity<Object> getTimetalbeCreated() {

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "시간표 편집 API", description = "")
	@PutMapping()
	public ResponseEntity<Object> getTimetableUpdated() {

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "시간표 삭제 API", description = "")
	@DeleteMapping()
	public ResponseEntity<Object> getTimetableDeleted() {

		return ResponseEntity.ok().build();
	}
}
