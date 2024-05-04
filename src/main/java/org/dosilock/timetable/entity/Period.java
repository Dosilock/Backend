package org.dosilock.timetable.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Table(name = "period")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Period {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "timetable_id", referencedColumnName = "id")
	private Timetable timetable;

	private String periodName;

	private LocalTime periodStartTime;

	private LocalTime periodEndTime;

	private LocalTime recessStartTime;

	private LocalTime recessEndTime;

	private boolean attendanceRequired;

	private int periodType;

}
