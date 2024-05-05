package org.dosilock.member.entity;

import java.time.LocalDateTime;
import java.util.function.Function;

import org.dosilock.member.request.RequestMemberDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
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
	private AuthEnum loginType;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public Member(RequestMemberDto requestMemberDto) {
		this.email = requestMemberDto.getEmail();
		this.password = requestMemberDto.getPassword();
		this.nickname = requestMemberDto.getNickname();
		this.profileImg = requestMemberDto.getProfileImg();
		this.loginType = AuthEnum.EMAIL;
	}

	public Member update(String name, String picture) {
		this.nickname = name;
		this.profileImg = picture;
		return this;
	}

	public Member updatePassword(String password, Function<String, String> passwordEncoder) {
		this.password = passwordEncoder.apply(password);
		return this;
	}
}
