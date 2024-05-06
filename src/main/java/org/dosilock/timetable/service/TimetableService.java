package org.dosilock.timetable.service;

import java.util.Arrays;

import org.dosilock.timetable.entity.Timetable;
import org.dosilock.timetable.repository.PeriodRepository;
import org.dosilock.timetable.repository.TimetableRepository;
import org.dosilock.timetable.response.PeriodResponse;
import org.dosilock.timetable.response.TimetableResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimetableService {

	private final TimetableRepository timetableRepository;
	private final PeriodRepository periodRepository;

	public TimetableResponse getTimetableInfo(Long timetableId) {

		Timetable timetable = timetableRepository.findById(timetableId)
			.orElseThrow(() -> new NullPointerException("없는 시간표 입니다."));

		TimetableResponse timetableResponse = new TimetableResponse(timetable);

		timetableResponse.setPeriodRequests(
			periodRepository.findByTimetableId(timetableId).stream().map(PeriodResponse::new).toList());

		timetableResponse.setTimetableDays(
			Arrays.stream(timetable.getTimetableDays().split(",")).map(Integer::parseInt).toList());

		return timetableResponse;
	}
}
