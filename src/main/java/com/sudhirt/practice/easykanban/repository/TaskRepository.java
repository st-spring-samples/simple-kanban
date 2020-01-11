package com.sudhirt.practice.easykanban.repository;

import java.util.Optional;

import com.sudhirt.practice.easykanban.entity.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Optional<Task> findByNameAndStatusAndSwimlaneId(String name, String status, long swimlaneId);

}