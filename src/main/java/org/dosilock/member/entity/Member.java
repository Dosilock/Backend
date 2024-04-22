package org.dosilock.member.entity;

import java.time.LocalDateTime;

import org.dosilock.request.RequestMemberDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String email;

	@Column
	private String password;

	@Column(length = 30, nullable = false)
	private String nickname;

	@Column(length = 1024)
	private String profileImg;

	@Column(nullable = false)
	private Integer loginType;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public Member(RequestMemberDto requestMemberDto) {
		this.email = requestMemberDto.getEmail();
		this.password = requestMemberDto.getPassword();
		this.nickname = requestMemberDto.getNickname();
		this.profileImg = requestMemberDto.getProfileImg();
		this.loginType = requestMemberDto.getLoginType();
	}

	public void updateUser(RequestMemberDto requestMemberDto) {
		this.nickname = requestMemberDto.getNickname();
		this.profileImg = requestMemberDto.getProfileImg();
	}

	public void updatePassword(String password) {
		this.password = password;
	}
}
