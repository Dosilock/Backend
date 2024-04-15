package org.dosilock.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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

	@Column(nullable = false)
	private LocalDateTime createdAt;
}
