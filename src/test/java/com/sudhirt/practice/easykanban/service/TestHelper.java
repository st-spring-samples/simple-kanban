package com.sudhirt.practice.easykanban.service;

import com.sudhirt.practice.easykanban.entity.Board;
import com.sudhirt.practice.easykanban.entity.Swimlane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestHelper {

	@Autowired
	private BoardService boardService;

	@Autowired
	private SwimlaneService swimlaneService;

	public Board createBoard() {
		return createBoard(1L, "Board 1");
	}

	public Board createBoard(long id, String name) {
		return boardService.get(id).orElseGet(() -> {
			return boardService.create(Board.builder().id(id).name(name).build());
		});
	}

	public Swimlane createSwimlane() {
		return createSwimlane(1L, "Swimlane 1", 1L, "Board 1");
	}

	public Swimlane createSwimlane(long id, String name, long boardId, String boardName) {
		createBoard(boardId, boardName);
		return swimlaneService.create(Swimlane.builder().id(id).name(name).build(), boardId);
	}

}