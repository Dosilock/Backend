package org.dosilock.clazz.entity;

import java.time.LocalDateTime;

import org.dosilock.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clazz_personnel")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClazzPersonnel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clazz_id", referencedColumnName = "id")
	private Clazz clazz;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	private int roleStatus;//방장/멤버

	private int acceptedStatus;//가입 수락/거절

	private LocalDateTime createdAt;
}
