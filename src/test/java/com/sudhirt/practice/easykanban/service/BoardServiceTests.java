package com.sudhirt.practice.easykanban.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Optional;

import javax.transaction.Transactional;

import com.sudhirt.practice.easykanban.entity.Board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@Transactional
public class BoardServiceTests {

	@Autowired
	private BoardService boardService;

	@Test
	public void should_throw_IllegalArgumentException_while_creating_board_with_null_parameter() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> boardService.create(null))
				.withMessage("Invalid argument 'null' provided.");
	}

	@Test
	public void should_throw_conflict_exception_while_creating_board_with_duplicate_name() {
		Board board = Board.builder().id(1l).name("Board 1").build();
		boardService.create(board);
		Board anotherBoard = Board.builder().id(2l).name("Board 1").build();
		assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() -> boardService.create(anotherBoard))
				.withMessageStartingWith("409").withMessageContaining("Board with name 'Board 1' exists.");
	}

	@Test
	public void should_create_book_successfully() {
		Board board = Board.builder().id(1l).name("Board 1").build();
		Board createdBoard = boardService.create(board);
		assertThat(boardService.get(createdBoard.getId())).isNotEmpty();
	}

	@Test
	public void get_should_return_empty_optional_when_board_with_provided_id_is_not_available() {
		assertThat(boardService.get(100l)).isEmpty();
	}

	@Test
	public void get_should_return_matching_board_with_provided_id() {
		Board board = Board.builder().id(1l).name("Board 1").build();
		boardService.create(board);
		Optional<Board> dbBoard = boardService.get(1l);
		assertThat(dbBoard).isNotEmpty();
		assertThat(dbBoard.get().getId()).isEqualTo(board.getId());
		assertThat(dbBoard.get().getName()).isEqualTo(board.getName());
	}

}