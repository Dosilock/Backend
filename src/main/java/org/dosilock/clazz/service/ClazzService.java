package org.dosilock.clazz.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.dosilock.clazz.entity.Clazz;
import org.dosilock.clazz.entity.ClazzPersonnel;
import org.dosilock.clazz.repository.ClazzPersonnelRepository;
import org.dosilock.clazz.repository.ClazzRepository;
import org.dosilock.clazz.request.ClazzRequest;
import org.dosilock.clazz.response.ClazzInfoResponse;
import org.dosilock.clazz.response.ClazzLinkResponse;
import org.dosilock.clazz.response.ClazzListResponse;
import org.dosilock.clazz.response.ClazzMemberInfoResponse;
import org.dosilock.exception.ErrorMessage;
import org.dosilock.exception.ErrorResponseDto;
import org.dosilock.exception.UserErrorException;
import org.dosilock.member.entity.Member;
import org.dosilock.socket.SocketService;
import org.dosilock.timetable.entity.Period;
import org.dosilock.timetable.entity.Timetable;
import org.dosilock.timetable.repository.PeriodRepository;
import org.dosilock.timetable.repository.TimetableRepository;
import org.dosilock.utils.GetMember;
import org.dosilock.utils.InviteLink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private final SocketService socketService;

	private Member member() {
		return GetMember.getMember();
	}

	@Transactional
	public ClazzLinkResponse addClazz(ClazzRequest clazzRequest) {
		Clazz clazz = Clazz.builder()
			.clazzTitle(clazzRequest.getClazzName())
			.clazzDescription(clazzRequest.getClazzDescription())
			.member(member())
			.clazzIcon(clazzRequest.getClazzIcon())
			.clazzLink(inviteLink.createInveteLink())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		socketService.addNamespace(clazz.getClazzLink());
		Clazz getClazz = clazzRepository.save(clazz);

		ClazzPersonnel clazzPersonnel = ClazzPersonnel.builder()
			.clazz(getClazz)
			.member(member())
			.roleStatus(0)
			.createdAt(LocalDateTime.now())
			.build();

		personnelRepository.save(clazzPersonnel);

		List<Integer> dayValues = clazzRequest.getTimetableRequest().getDayOfWeeks();
		String days = dayValues.stream()
			.map(String::valueOf)
			.collect(Collectors.joining(","));

		Timetable timetable = Timetable.builder()
			.clazz(getClazz)
			.timetableName(clazzRequest.getTimetableRequest().getTimetableName())
			.timetableDays(days)
			.createdAt(LocalDateTime.now())
			.build();

		Timetable getTimetable = timetableRepository.save(timetable);

		clazzRequest.getTimetableRequest().getPeriodRequests().forEach(per -> {
			Period period = Period.builder()
				.timetable(getTimetable)
				.periodName(per.getPeriodName())
				.periodStartTime(LocalTime.parse(per.getPeriodStartTime()))
				.periodDuration(per.getPeriodDuration())
				.attendanceRequired(per.isAttendanceRequired())
				.build();

			periodRepository.save(period);
		});

		return new ClazzLinkResponse(clazz.getClazzLink());
	}

	@Transactional(readOnly = true)
	public List<ClazzListResponse> getClazzList() {
		List<Clazz> clazzes = clazzRepository.findByMemberId(member().getId());
		List<ClazzListResponse> clazzListResponses = new ArrayList<>();

		for (Clazz clazz : clazzes) {
			ClazzListResponse clazzListResponse = new ClazzListResponse();
			clazzListResponse.setClazzName(clazz.getClazzTitle());
			clazzListResponse.setOwned(clazz.getMember().getEmail().equals(member().getEmail()));
			clazzListResponse.setMemberCount(clazzPersonnelRepository.countByClazzId(clazz.getId()));
			clazzListResponse.setClazzIcon(clazz.getClazzIcon());
			clazzListResponse.setClazzLink(clazz.getClazzLink());
			clazzListResponses.add(clazzListResponse);
		}

		return clazzListResponses;
	}

	//반 이름, 반 아이콘, 반 인원 수
	@Transactional(readOnly = true)
	public ClazzInfoResponse getClazzInfo(String link) {
		Clazz clazz = clazzRepository.findByClazzLink(link);
		Long clazzId = clazz.getId();
		ClazzInfoResponse clazzInfoResponse = new ClazzInfoResponse();
		int memberCount = clazzPersonnelRepository.countByClazzId(clazzId);
		clazzInfoResponse.setMemberCount(memberCount);
		clazzInfoResponse.setClazzName(clazz.getClazzTitle());
		clazzInfoResponse.setClazzIcon(clazz.getClazzIcon());
		return clazzInfoResponse;
	}

	//가입 수락,거절 방장이어야하고
	@Transactional
	public void checkAccept(String link, Boolean isAccepted) {
		Long memberId = member().getId();
		Clazz clazz = clazzRepository.findByClazzLink(link);
		if (Objects.equals(memberId, clazz.getMember().getId())) {
			ClazzPersonnel clazzPersonnel = new ClazzPersonnel();
			if (isAccepted) {
				clazzPersonnel.setRoleStatus(1);
				clazzPersonnelRepository.save(clazzPersonnel);
			} else {
				clazzPersonnel.setRoleStatus(3);
				clazzPersonnelRepository.save(clazzPersonnel);
			}
		} else {
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.NOT_ROOM_OWNER));
		}
	}

	//가입된 멤버인가 체크 후 가입 신청 처리
	//방장인가? 블랙리스트인가? 가입 진행중인가? 이미 멤버인가?
	@Transactional
	public void checkMemberAndInvete(String link) {
		Long memberId = member().getId();
		Clazz clazz = clazzRepository.findByClazzLink(link);
		Long clazzId = clazz.getId();
		ClazzPersonnel clazzPersonnel = clazzPersonnelRepository.findByClazzIdAndMemberId(clazzId, memberId)
			.orElseGet(() -> {
				ClazzPersonnel inviteClazz = new ClazzPersonnel();
				inviteClazz.setRoleStatus(2);
				inviteClazz.setClazz(clazz);
				inviteClazz.setMember(member());
				inviteClazz.setCreatedAt(LocalDateTime.now());
				return clazzPersonnelRepository.save(inviteClazz);
			});

		if (Objects.equals(clazz.getMember().getId(), memberId)) {
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.ROOM_OWNER));
		} else if (Objects.equals(clazzPersonnel.getRoleStatus(), 1)) {
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.ALREADY_A_MEMBER));
		} else if (Objects.equals(clazzPersonnel.getRoleStatus(), 2)) {
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.REGISTRATION_IN_PROGRESS));
		} else if (Objects.equals(clazzPersonnel.getRoleStatus(), 3)) {
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.REJECTED));
		}
	}

	//닉네임, 프로필사진??
	@Transactional
	public List<ClazzMemberInfoResponse> getMemberInfo(String link) {
		List<ClazzMemberInfoResponse> memberInfoResponses = new ArrayList<>();
		Clazz clazz = clazzRepository.findByClazzLink(link);
		Long clazzId = clazz.getId();
		List<ClazzPersonnel> clazzPersonnelList = clazzPersonnelRepository.findByClazzId(clazzId);
		for (ClazzPersonnel clazzPersonnel : clazzPersonnelList) {
			ClazzMemberInfoResponse clazzMemberInfoResponse = new ClazzMemberInfoResponse();
			clazzMemberInfoResponse.setNinkname(clazzPersonnel.getMember().getNickname());
			clazzMemberInfoResponse.setProfileImg(clazzPersonnel.getMember().getProfileImg());
			memberInfoResponses.add(clazzMemberInfoResponse);
		}
		return memberInfoResponses;
	}
}
