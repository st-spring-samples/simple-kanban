package com.sudhirt.practice.easykanban.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import javax.transaction.Transactional;

import com.sudhirt.practice.easykanban.entity.Swimlane;
import com.sudhirt.practice.easykanban.exception.ResourceAlreadyExistsException;
import com.sudhirt.practice.easykanban.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class SwimlaneServiceTests {

	@Autowired
	private SwimlaneService swimlaneService;

	@Autowired
	private TestHelper helper;

	@Test
	public void should_throw_IllegalArgumentException_while_creating_swimlane_with_null_parameter() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> swimlaneService.create(null, 100L))
				.withMessage("Invalid argument 'null' provided.");
	}

	@Test
	public void create_should_throw_ResourceNotFoundException_when_board_with_provided_id_not_found() {
		var swimlane = new Swimlane();
		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> swimlaneService.create(swimlane, 100L))
				.withMessage("Board with identifier '100' not found");
	}

	@Test
	public void should_create_swimlane_successfully() {
		var swimlane = Swimlane.builder().id(1L).name("Swimlane 1").build();
		var createdSwimlane = swimlaneService.create(swimlane, helper.createBoard().getId());
		assertThat(swimlaneService.get(createdSwimlane.getId())).isNotEmpty();
	}

	@Test
	public void should_throw_ResourceAlreadyExistsException_while_creating_swimlane_with_duplicate_name() {
		var board = helper.createBoard();
		var swimlane1 = Swimlane.builder().id(1L).name("Swimlane 1").build();
		swimlaneService.create(swimlane1, board.getId());
		var swimlane2 = Swimlane.builder().id(1L).name("Swimlane 1").build();
		assertThatExceptionOfType(ResourceAlreadyExistsException.class)
				.isThrownBy(() -> swimlaneService.create(swimlane2, board.getId()))
				.withMessage("Swimlane with 'name' - 'Swimlane 1' already exists");
	}

	@Test
	public void should_create_swimlane_with_same_name_in_another_board() {
		var board = helper.createBoard();
		var swimlane1 = Swimlane.builder().id(1L).name("Swimlane 1").build();
		swimlaneService.create(swimlane1, board.getId());
		var board2 = helper.createBoard(2L, "Board 2");
		var swimlane2 = Swimlane.builder().id(2L).name("Swimlane 1").build();
		assertThatCode(() -> {
			swimlaneService.create(swimlane2, board2.getId());
		}).doesNotThrowAnyException();
		assertThat(swimlaneService.get(2L)).isNotEmpty();
	}

}