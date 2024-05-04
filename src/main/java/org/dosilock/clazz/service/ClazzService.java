package org.dosilock.clazz.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dosilock.clazz.entity.Clazz;
import org.dosilock.clazz.entity.ClazzPersonnel;
import org.dosilock.clazz.repository.ClazzPersonnelRepository;
import org.dosilock.clazz.repository.ClazzRepository;
import org.dosilock.clazz.request.ClazzRequest;
import org.dosilock.clazz.response.ClazzListResponse;
import org.dosilock.clazz.response.ClazzResponse;
import org.dosilock.timetable.entity.Day;
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
	private final ClazzPersonnelRepository personnelRepository;
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

		ClazzPersonnel clazzPersonnel = ClazzPersonnel.builder()
			.clazz(clazz)
			.createdAt(LocalDateTime.now())
			.build();

		personnelRepository.save(clazzPersonnel);

		Timetable timetable = Timetable.builder()
			.timetableName(clazzRequest.getTimetableRequest().getTimetableName())
			.day(dayNames(clazzRequest.getTimetableRequest().getDays()))
			.createdAt(LocalDateTime.now())
			.build();

		timetableRepository.save(timetable);

		clazzRequest.getTimetableRequest().getPeriodRequests().forEach(per -> {
			Period period = Period.builder()
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

	//고민 사항 인트 배열로 요일을 요청 받았을 때 어떻게 디비에 저장할지 이넘이든 문자열이든

	public String convertDaysToString(List<Day> dayList) {
		return dayList.stream()
			.map(Enum::name)
			.collect(Collectors.joining(", "));
	}

	public List<ClazzListResponse> getClazzList(Long memberId) throws Exception {
		List<Clazz> clazzes = clazzRepository.findByMemberId(memberId);
		List<ClazzListResponse> clazzListResponses = new ArrayList<>();
		for(Clazz clazz : clazzes) {
			ClazzListResponse clazzListResponse = new ClazzListResponse();
			clazzListResponse.setClazzName(clazz.getClazzTitle());
			//clazz.getMember().getId().equals(memberId); 현재 접속한 사용자, 반 생성 사용자 비교 후 boolean 리턴
			clazzListResponse.setMemberCount(
				periodRepository.countByClazzAndMember(clazz, memberId)
			);//memberId랑 클레스랑 넘겨줘서 카운트 새기
			clazzListResponses.add(clazzListResponse);
		}
		return clazzListResponses;
	}

	public void getClazzLink() {
	}
}
