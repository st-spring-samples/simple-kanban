package com.sudhirt.practice.easykanban.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "SWIMLANES")
public class Swimlane extends AuditableEntity {

	@EqualsAndHashCode.Include
	@Id
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String name;

	@Column(nullable = false)
	private int sortOrder;

	@ManyToOne
	@JoinColumn(name = "BOARD_ID", nullable = false)
	private Board board;

}