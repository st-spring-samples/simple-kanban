package com.sudhirt.practice.easykanban.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TASKS", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "status", "swimlane_id" }))
public class Task extends AuditableEntity {

	@EqualsAndHashCode.Include
	@Id
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String name;

	private String description;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime targetDate;

	@Column(nullable = false)
	private String status;

	@ManyToOne
	@JoinColumn(name = "SWIMLANE_ID", nullable = false)
	private Swimlane swimlane;

}