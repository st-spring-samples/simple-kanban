package com.sudhirt.practice.easykanban.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.sudhirt.practice.easykanban.entity.Board;
import com.sudhirt.practice.easykanban.entity.Swimlane;
import com.sudhirt.practice.easykanban.entity.Task;
import com.sudhirt.practice.easykanban.exception.ResourceAlreadyExistsException;
import com.sudhirt.practice.easykanban.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class TaskServiceTests {

	@Autowired
	private TaskService taskService;

	@Autowired
	private SwimlaneService swimlaneService;

	@Autowired
	private BoardService boardService;

	@Test
	public void should_throw_IllegalArgumentException_while_creating_task_with_null_parameter() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> taskService.create(null, 100l))
				.withMessage("Invalid argument 'null' provided.");
	}

	@Test
	public void create_should_throw_ResourceNotFoundException_when_swimlane_with_provided_id_not_found() {
		var task = new Task();
		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> taskService.create(task, 100l))
				.withMessage("Swimlane with identifier '100' not found");
	}

	@Test
	public void should_create_task_successfully() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var createdTask = taskService.create(task, createSwimlane().getId());
		assertThat(taskService.get(createdTask.getId())).isNotEmpty();
	}

	@Test
	public void should_throw_ResourceAlreadyExistsException_while_creating_task_with_duplicate_swimline_name_and_status() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = createSwimlane();
		taskService.create(task, swimlane.getId());
		var anotherTask = Task.builder().id(2l).name("Task 1").description("Task 2 description")
				.targetDate(LocalDateTime.of(2020, 11, 30, 23, 59)).build();
		assertThatExceptionOfType(ResourceAlreadyExistsException.class)
				.isThrownBy(() -> taskService.create(anotherTask, swimlane.getId()))
				.withMessage("Task with 'name' - 'Task 1' already exists");
	}

	@Test
	public void should_create_swimlane_with_same_swimline_name_but_different_status() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = createSwimlane();
		var dbTask = taskService.create(task, swimlane.getId());
		dbTask.setStatus("IN PROGRESS");
		dbTask = taskService.update(dbTask);
		var anotherTask = Task.builder().id(2l).name("Task 1").description("Task 2 description")
				.targetDate(LocalDateTime.of(2020, 11, 30, 23, 59)).build();
		assertThatCode(() -> {
			taskService.create(anotherTask, swimlane.getId());
		}).doesNotThrowAnyException();
	}

	@Test
	public void should_create_swimlane_with_same_name_status_but_different_swimline() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = createSwimlane();
		taskService.create(task, swimlane.getId());
		var anotherSwimline = createSwimlane(2l, "Swimline 2", 1l, "Board 1");
		var anotherTask = Task.builder().id(2l).name("Task 1").description("Task 2 description")
				.targetDate(LocalDateTime.of(2020, 11, 30, 23, 59)).build();
		assertThatCode(() -> {
			taskService.create(anotherTask, anotherSwimline.getId());
		}).doesNotThrowAnyException();
	}

	private Swimlane createSwimlane() {
		return createSwimlane(1l, "Swimlane 1", 1l, "Board 1");
	}

	private Swimlane createSwimlane(long id, String name, long boardId, String boardName) {
		createBoard(boardId, boardName);
		return swimlaneService.create(Swimlane.builder().id(id).name(name).build(), boardId);
	}

	private Board createBoard(long id, String name) {
		return boardService.get(id).orElseGet(() -> {
			return boardService.create(Board.builder().id(id).name(name).build());
		});
	}

}