package org.dosilock.timetable.controller;

import org.dosilock.timetable.request.TimetableRequest;
import org.dosilock.timetable.response.TimetableResponse;
import org.dosilock.timetable.service.TimetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timetable/")
public class TimetableController {

	private final TimetableService timetableService;

	@Operation(summary = "시간표 보기 API", description = "시간표의 정보와 시간표 모든 교시를 보여줍니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@GetMapping(value = "info/{timetableId}")
	public ResponseEntity<TimetableResponse> getTimetableInfo(
		@Parameter(description = "시간표 아이디") @PathVariable Long timetableId) {
		return ResponseEntity.ok(timetableService.getTimetableInfo(timetableId));
	}

	@Operation(summary = "시간표 편집 API", description = "시간표를 편집 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@PutMapping(value = "modify/{timetableId}")
	public ResponseEntity<Void> updateTimetable(@RequestBody TimetableRequest timetableRequest,
		@Parameter(description = "시간표 아이디") @PathVariable Long timetableId) {
		timetableService.updateTimetable(timetableRequest, timetableId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "시간표 삭제 API", description = "시간표를 삭제 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공")
	})
	@DeleteMapping(value = "delete/{timetableId}")
	public ResponseEntity<Void> deleteTimetable(@Parameter(description = "시간표 아이디") @PathVariable Long timetableId) {
		timetableService.deleteTimetable(timetableId);
		return ResponseEntity.ok().build();
	}
}
