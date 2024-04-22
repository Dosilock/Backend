package org.dosilock.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ClazzBlackList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// FK Entity 추가 시 적용 필요
	private Long clazzId;

	@ManyToOne
	private Member member;

	@Column(nullable = false)
	private LocalDateTime createdAt;
}
