package org.dosilock.timetable.entity;

import java.time.LocalDateTime;

import org.dosilock.clazz.entity.Clazz;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "timetable")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Timetable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clazz_id", referencedColumnName = "id")
	private Clazz clazz;

	private String timetableDays;

	private String timetableName;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
