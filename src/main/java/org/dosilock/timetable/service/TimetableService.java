package org.dosilock.timetable.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.dosilock.timetable.entity.Period;
import org.dosilock.timetable.entity.Timetable;
import org.dosilock.timetable.repository.PeriodRepository;
import org.dosilock.timetable.repository.TimetableRepository;
import org.dosilock.timetable.request.TimetableRequest;
import org.dosilock.timetable.response.PeriodResponse;
import org.dosilock.timetable.response.TimetableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimetableService {

	private final TimetableRepository timetableRepository;
	private final PeriodRepository periodRepository;

	@Transactional(readOnly = true)
	public TimetableResponse getTimetableInfo(Long timetableId) {

		// 자신이 해당 시간표를 생성한 반 인원에 속해 있어야지 보여 줄 수 있어야함.

		Timetable timetable = timetableRepository.findById(timetableId)
			.orElseThrow(() -> new NullPointerException("없는 시간표 입니다."));

		TimetableResponse timetableResponse = new TimetableResponse(timetable);

		timetableResponse.setPeriodRequests(
			periodRepository.findByTimetableId(timetableId).stream().map(PeriodResponse::new).toList());

		timetableResponse.setTimetableDays(
			Arrays.stream(timetable.getTimetableDays().split(",")).map(Integer::parseInt).toList());

		return timetableResponse;
	}

	@Transactional
	public void deleteTimetable(Long timetableId) {
		timetableRepository.deleteById(timetableId);
	}

	@Transactional
	public void updateTimetable(TimetableRequest timetableRequest, Long timetableId) {

		List<Integer> dayValues = timetableRequest.getTimetableDays();
		String days = dayValues.stream()
			.map(String::valueOf)
			.collect(Collectors.joining(","));

		Timetable timetable = Timetable.builder()
			.clazz(timetableRepository.findById(timetableId).orElseThrow().getClazz())
			.timetableName(timetableRequest.getTimetableName())
			.timetableDays(days)
			.createdAt(LocalDateTime.now())
			.build();
		timetableRepository.deleteById(timetableId);
		periodRepository.deleteByTimetableId(timetableId);

		Timetable getTimetable = timetableRepository.save(timetable);

		timetableRequest.getPeriodRequests().forEach(per -> {
			Period period = Period.builder()
				.timetable(getTimetable)
				.periodName(per.getPeriodName())
				.periodStartTime(LocalTime.parse(per.getPeriodStartTime()))
				.periodDuration(per.getPeriodDuration())
				.attendanceRequired(per.isAttendanceRequired())
				.build();

			periodRepository.save(period);
		});
	}
}
