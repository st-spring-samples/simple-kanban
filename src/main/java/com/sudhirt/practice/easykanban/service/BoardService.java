package com.sudhirt.practice.easykanban.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.sudhirt.practice.easykanban.entity.Board;
import com.sudhirt.practice.easykanban.exception.ResourceAlreadyExistsException;
import com.sudhirt.practice.easykanban.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public Board create(Board board) {
		if (board == null) {
			throw new IllegalArgumentException("Invalid argument 'null' provided.");
		}
		boardRepository.findByName(board.getName()).ifPresent(b -> {
			throw new ResourceAlreadyExistsException("Board", "name", board.getName());
		});
		return boardRepository.save(board);
	}

	public Optional<Board> get(long id) {
		return boardRepository.findById(id);
	}

}