package com.sudhirt.practice.easykanban.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "SWIMLANES", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "board_id" }))
public class Swimlane extends AuditableEntity {

	@EqualsAndHashCode.Include
	@Id
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "BOARD_ID", nullable = false)
	private Board board;

	@OneToMany(mappedBy = "swimlane")
	private Set<Task> tasks;

	public void add(Task task) {
		if (tasks == null) {
			tasks = new HashSet<>();
		}
		task.setSwimlane(this);
		tasks.add(task);
	}

}