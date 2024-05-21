package org.dosilock.exception;

public enum ErrorMessage {
	USER_NOT_FOUND("USER_NOT_FOUND", "유저를 찾을 수가 없습니다."),
	LINK_NOT_FOUND("LINK_NOT_FOUND", "링크를 찾을 수가 없습니다."),
	NOT_ROOM_OWNER("NOT_ROOM_OWNER", "방장이 아닙니다."),
	ROOM_OWNER("ROOM_OWNER", "방장입니다."),
	ALREADY_A_MEMBER("ALREADY_A_MEMBER", "이미 멤버입니다."),
	REGISTRATION_IN_PROGRESS("REGISTRATION_IN_PROGRESS", "가입 진행중입니다."),
	REJECTED("REJECTED", "거절된 상태입니다."),
	TIME_TABLE_NOT_FOUND("TIME_TABLE_NOT_FOUND", "시간표를 찾을 수가 없습니다."),
	ALREADY_NAMESPACE("ALREADY_NAMESPACE", "이미 방이 생성되어 있습니다.");

	private final String code;
	private final String message;

	ErrorMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
