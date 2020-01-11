package com.sudhirt.practice.easykanban.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.sudhirt.practice.easykanban.entity.Task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class TaskServiceUpdateTests {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TestHelper helper;

	@Test
	public void should_update_task_name_only() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = helper.createSwimlane();
		taskService.create(task, swimlane.getId());
		var task1 = Task.builder().id(1l).name("Task 1 - updated").build();
		taskService.update(task1);
		Task dbTask = taskService.get(1l).orElseThrow(() -> new RuntimeException("Task not found...."));
		assertThat(dbTask.getName()).isEqualTo(task1.getName());
		assertThat(dbTask.getDescription()).isEqualTo(task.getDescription());
		assertThat(dbTask.getTargetDate()).isEqualTo(task.getTargetDate());
		assertThat(dbTask.getStatus()).isEqualTo(task.getStatus());
	}

	@Test
	public void should_update_task_description_only() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = helper.createSwimlane();
		taskService.create(task, swimlane.getId());
		var task1 = Task.builder().id(1l).description("Task 1 description - updated").build();
		taskService.update(task1);
		Task dbTask = taskService.get(1l).orElseThrow(() -> new RuntimeException("Task not found...."));
		assertThat(dbTask.getDescription()).isEqualTo(task1.getDescription());
		assertThat(dbTask.getName()).isEqualTo(task.getName());
		assertThat(dbTask.getTargetDate()).isEqualTo(task.getTargetDate());
		assertThat(dbTask.getStatus()).isEqualTo(task.getStatus());
	}

	@Test
	public void should_update_task_targetDate_only() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = helper.createSwimlane();
		taskService.create(task, swimlane.getId());
		var task1 = Task.builder().id(1l).targetDate(LocalDateTime.of(2020, 11, 30, 23, 59)).build();
		taskService.update(task1);
		Task dbTask = taskService.get(1l).orElseThrow(() -> new RuntimeException("Task not found...."));
		assertThat(dbTask.getTargetDate()).isEqualTo(task1.getTargetDate());
		assertThat(dbTask.getDescription()).isEqualTo(task.getDescription());
		assertThat(dbTask.getName()).isEqualTo(task.getName());
		assertThat(dbTask.getStatus()).isEqualTo(task.getStatus());
	}

	@Test
	public void should_update_task_status_only() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = helper.createSwimlane();
		taskService.create(task, swimlane.getId());
		var task1 = Task.builder().id(1l).status("IN PROGRESS").build();
		taskService.update(task1);
		Task dbTask = taskService.get(1l).orElseThrow(() -> new RuntimeException("Task not found...."));
		assertThat(dbTask.getStatus()).isEqualTo(task1.getStatus());
		assertThat(dbTask.getTargetDate()).isEqualTo(task.getTargetDate());
		assertThat(dbTask.getDescription()).isEqualTo(task.getDescription());
		assertThat(dbTask.getName()).isEqualTo(task.getName());
	}

	@Test
	public void should_update_task_name_description_targetDate_status() {
		var task = Task.builder().id(1l).name("Task 1").description("Task 1 description")
				.targetDate(LocalDateTime.of(2020, 12, 31, 23, 59)).build();
		var swimlane = helper.createSwimlane();
		taskService.create(task, swimlane.getId());
		var task1 = Task.builder().id(1l).name("Task 1 - updated").description("Task 1 description - updated")
				.targetDate(LocalDateTime.of(2020, 11, 30, 23, 59)).status("IN PROGRESS").build();
		taskService.update(task1);
		Task dbTask = taskService.get(1l).orElseThrow(() -> new RuntimeException("Task not found...."));
		assertThat(dbTask.getTargetDate()).isEqualTo(task1.getTargetDate());
		assertThat(dbTask.getDescription()).isEqualTo(task1.getDescription());
		assertThat(dbTask.getName()).isEqualTo(task1.getName());
		assertThat(dbTask.getStatus()).isEqualTo(task1.getStatus());
	}

}