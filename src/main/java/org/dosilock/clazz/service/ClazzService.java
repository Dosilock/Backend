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
import org.dosilock.clazz.response.ClazzInfoResponse;
import org.dosilock.clazz.response.ClazzListResponse;
import org.dosilock.clazz.response.ClazzLinkResponse;
import org.dosilock.timetable.entity.Day;
import org.dosilock.timetable.entity.Period;
import org.dosilock.timetable.entity.Timetable;
import org.dosilock.timetable.repository.PeriodRepository;
import org.dosilock.timetable.repository.TimetableRepository;
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
	private final ClazzPersonnelRepository clazzPersonnelRepository;

	@Transactional
	public ClazzLinkResponse addClazz(ClazzRequest clazzRequest) throws Exception {
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
			//현재 접속한 사용자 == 방장을 멤버에 저장
			.roleStatus(0)
			.acceptedStatus(0)
			.createdAt(LocalDateTime.now())
			.build();

		personnelRepository.save(clazzPersonnel);

		List<Integer> dayValues = clazzRequest.getTimetableRequest().getDays();

		Timetable timetable = Timetable.builder()
			.timetableName(clazzRequest.getTimetableRequest().getTimetableName())
			.createdAt(LocalDateTime.now())
			.build();

		timetableRepository.save(timetable);

		clazzRequest.getTimetableRequest().getPeriodRequests().forEach(per -> {
			Period period = Period.builder()
				.periodName(per.getPeriodName())
				.periodStartTime(LocalTime.parse(per.getPeriodStartTime()))
				.periodDuration(per.getPeriodDuration())
				.attendanceRequired(per.isAttendanceRequired())
				.build();

			periodRepository.save(period);
		});

		return new ClazzLinkResponse(clazz.getClazzLink());
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
			Long clazzId = clazz.getId();
			// clazzListResponse.setMemberCount(
			// 	periodRepository.countByClazzIdAndMemberId(clazzId, memberId)
			// );//memberId랑 클레스랑 넘겨줘서 카운트 새기
			clazzListResponses.add(clazzListResponse);
		}
		return clazzListResponses;
	}
	//반 이름, 반 아이콘, 반 인원 수
	public ClazzInfoResponse getClazzInfo(String link) {
		long memberId = 1;
		Clazz clazz = clazzRepository.findByClazzLink(link);
		Long clazzId = clazz.getId();
		ClazzInfoResponse clazzInfoResponse = new ClazzInfoResponse();
		int memberCount = clazzPersonnelRepository.countByMemberIdAndClazzId(memberId, clazzId);
		clazzInfoResponse.setMemberCount(memberCount);
		clazzInfoResponse.setClazzName(clazz.getClazzTitle());
		clazzInfoResponse.setClazzIcon(clazz.getClazzIcon());
		return clazzInfoResponse;
	}

	//가입 수락,,거절
	public void checkAccept() {
		//수락일 경우 1, 거절일 경우 0
		boolean accept = false;
		if(accept) {

		} else {
			throw new IllegalStateException("거절 완료");
		}
	}

	//가입된 멤버인가
	public void checkMember() {
		long memberId = 1;
		long clazzId = 1;
		boolean checkMemberId = clazzPersonnelRepository.findByClazzIdAndMemberId(clazzId, memberId);
		if(!checkMemberId) {
			throw new IllegalStateException("가입된 멤버가 아니다.");
		}
	}

	//영웅이랑 코드 맞추고 만들기
	public void getMemberInfo() {
	}
}
