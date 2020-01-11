package com.sudhirt.practice.easykanban.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "BOARDS")
public class Board extends AuditableEntity {

	@EqualsAndHashCode.Include
	@Id
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String name;

	@OneToMany(mappedBy = "board")
	private Set<Swimlane> swimlanes;

	public void add(Swimlane swimlane) {
		if (swimlanes == null) {
			swimlanes = new HashSet<>();
		}
		swimlane.setBoard(this);
		swimlanes.add(swimlane);
	}

}