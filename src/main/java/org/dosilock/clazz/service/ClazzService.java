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
		Clazz clazz = new Clazz();
		clazz.setClazzTitle(clazzRequest.getClazzName());
		clazz.setClazzDescription(clazzRequest.getClazzDescription());

		String link = inviteLink.createInveteLink();
		clazz.setClazzLink("https://gongsilock.com/" + link);
		clazz.setCreatedAt(LocalDateTime.now());
		clazz.setUpdatedAt(LocalDateTime.now());
		Clazz savedClazz = clazzRepository.save(clazz);

		Timetable timetable = new Timetable();
		timetable.setTimetableName(clazzRequest.getTimetableRequest().getTimetableName());
		timetable.setDay(clazzRequest.getTimetableRequest().getDay());
		timetable.setCreatredAt(LocalDateTime.now());
		timetableRepository.save(timetable);

		for(PeriodRequest per : clazzRequest.getTimetableRequest().getPeriodRequests()) {
			Period period = new Period();
			period.setPeriodName(per.getPeriodName());
			period.setPeriodStartTime(LocalTime.parse(per.getPeriodStartTime()));
			period.setPeriodEndTime(LocalTime.parse(per.getPeriodEndTime()));
			period.setRecessStartTime(LocalTime.parse(per.getRecessStartTime()));
			period.setRecessEndTime(LocalTime.parse(per.getRecessEndTime()));
			period.setAttendanceRequired(per.isAttendanceRequired());
			period.setPeriodType(per.getPeriodType());
			periodRepository.save(period);
		}

		ClazzResponse clazzResponse = new ClazzResponse();
		clazzResponse.setClazzLink(savedClazz.getClazzLink());

		return clazzResponse;
	}

	public void getClazz() {
	}

	public void getClazzLink() {
		
	}
}
