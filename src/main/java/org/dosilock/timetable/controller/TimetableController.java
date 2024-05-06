package org.dosilock.timetable.controller;

import org.dosilock.timetable.request.TimetableRequest;
import org.dosilock.timetable.response.TimetableResponse;
import org.dosilock.timetable.service.TimetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timetable/")
public class TimetableController {

	private final TimetableService timetableService;

	@Operation(summary = "시간표 보기 API", description = "시간표의 정보와 시간표 모든 교시를 보여줍니다.")
	@GetMapping(value = "info/{timetableId}")
	public ResponseEntity<TimetableResponse> getTimetableInfo(@PathVariable Long timetableId) {
		return ResponseEntity.ok(timetableService.getTimetableInfo(timetableId));
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

	@Operation(summary = "시간표 편집 API", description = "시간표를 편집 합니다.")
	@PutMapping(value = "modify/{timetableId}")
	public ResponseEntity<Object> updateTimetable(@RequestBody TimetableRequest timetableRequest,
		@PathVariable Long timetableId) {
		timetableService.updateTimetable(timetableRequest, timetableId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "시간표 삭제 API", description = "시간표를 삭제 합니다.")
	@DeleteMapping(value = "delete/{timetableId}")
	public ResponseEntity<Void> deleteTimetable(@PathVariable Long timetableId) {
		timetableService.deleteTimetable(timetableId);
		return ResponseEntity.ok().build();
	}
}
