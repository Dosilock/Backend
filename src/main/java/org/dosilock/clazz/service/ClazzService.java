package org.dosilock.clazz.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.dosilock.clazz.entity.Clazz;
import org.dosilock.clazz.repository.ClazzRepository;
import org.dosilock.clazz.request.ClazzRequest;
import org.dosilock.clazz.response.ClazzResponse;
import org.dosilock.timetable.entity.Period;
import org.dosilock.timetable.entity.Timetable;
import org.dosilock.timetable.repository.PeriodRepository;
import org.dosilock.timetable.repository.TimetableRepository;
import org.dosilock.timetable.request.PeriodRequest;
import org.dosilock.utils.InviteLink;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClazzService {

	private final ClazzRepository clazzRepository;
	private final TimetableRepository timetableRepository;
	private final PeriodRepository periodRepository;
	private final InviteLink inviteLink;

	@Transactional
	public ClazzResponse addClazz(ClazzRequest clazzRequest) throws Exception {
		Clazz clazz = Clazz.builder()
			.clazzTitle(clazzRequest.getClazzName())
			.clazzDescription(clazzRequest.getClazzDescription())
			.clazzLink("https://gongsilock.com/" + inviteLink.createInveteLink())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		clazzRepository.save(clazz);

		Timetable timetable = Timetable.builder()
			.timetableName(clazzRequest.getTimetableRequest().getTimetableName())
			.createdAt(LocalDateTime.now())
			.build();

		timetableRepository.save(timetable);

		clazzRequest.getTimetableRequest().getPeriodRequests().forEach(per -> {
			Period period = Period.builder()
				.day(per.getDay())
				.periodName(per.getPeriodName())
				.periodStartTime(LocalTime.parse(per.getPeriodStartTime()))
				.periodEndTime(LocalTime.parse(per.getPeriodEndTime()))
				.recessStartTime(LocalTime.parse(per.getRecessStartTime()))
				.recessEndTime(LocalTime.parse(per.getRecessEndTime()))
				.attendanceRequired(per.isAttendanceRequired())
				.periodType(per.getPeriodType())
				.build();

			periodRepository.save(period);
		});

		return new ClazzResponse(clazz.getClazzLink());
	}

	public void getClazz() {
	}

	public void getClazzLink() {
		
	}
}
