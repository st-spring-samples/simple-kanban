package com.sudhirt.practice.easykanban.service;

import java.util.Optional;

import com.sudhirt.practice.easykanban.entity.Task;
import com.sudhirt.practice.easykanban.exception.ResourceAlreadyExistsException;
import com.sudhirt.practice.easykanban.exception.ResourceNotFoundException;
import com.sudhirt.practice.easykanban.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private SwimlaneService swimlaneService;

	public Task create(Task task, long swimlaneId) {
		if (task == null) {
			throw new IllegalArgumentException("Invalid argument 'null' provided.");
		}
		var swimlaneHolder = swimlaneService.get(swimlaneId);
		var swimlane = swimlaneHolder.orElseThrow(() -> new ResourceNotFoundException("Swimlane", swimlaneId));
		task.setStatus("OPEN");
		taskRepository.findByNameAndStatusAndSwimlaneId(task.getName(), task.getStatus(), swimlaneId).ifPresent(b -> {
			throw new ResourceAlreadyExistsException("Task", "name", task.getName());
		});
		swimlane.add(task);
		return taskRepository.save(task);
	}

	public Task update(Task task) {
		var dbTask = get(task.getId()).orElseThrow(() -> new ResourceNotFoundException("Task", task.getId()));
		dbTask.setName(task.getName() == null ? dbTask.getName() : task.getName());
		dbTask.setDescription(task.getDescription() == null ? dbTask.getDescription() : task.getDescription());
		dbTask.setTargetDate(task.getTargetDate() == null ? dbTask.getTargetDate() : task.getTargetDate());
		dbTask.setStatus(task.getStatus() == null ? dbTask.getStatus() : task.getStatus());
		return taskRepository.save(dbTask);
	}

	public Optional<Task> get(long id) {
		return taskRepository.findById(id);
	}

}