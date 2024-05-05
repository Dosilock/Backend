package org.dosilock.member.entity;

import java.time.LocalDateTime;

import org.dosilock.clazz.entity.Clazz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ClazzBlackList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Clazz clazz;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(nullable = false)
	private LocalDateTime createdAt;
}
