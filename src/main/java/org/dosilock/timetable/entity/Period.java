package org.dosilock.timetable.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "period")
@Getter @Setter
public class Period {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "timetable_id", referencedColumnName = "id")
	private Timetable timetable;

	private String periodName;

	private LocalDateTime periodStartTime;

	private LocalDateTime periodEndTime;

	private LocalDateTime recessStartTime;

	private LocalDateTime recessEndTime;

	private boolean attendanceRequired;

}
