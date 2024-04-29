package org.dosilock.timetable.entity;

import java.time.LocalDateTime;

import org.dosilock.clazz.entity.Clazz;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

	@OneToOne
	@JoinColumn(name = "clazz_id", referencedColumnName = "id")
	private Clazz clazz;

	private String timetableName;

	private LocalDateTime createdAt;
}
