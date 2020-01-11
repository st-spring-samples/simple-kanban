package com.sudhirt.practice.easykanban.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.sudhirt.practice.easykanban.entity.Board;
import com.sudhirt.practice.easykanban.entity.Swimlane;
import com.sudhirt.practice.easykanban.exception.ResourceAlreadyExistsException;
import com.sudhirt.practice.easykanban.exception.ResourceNotFoundException;
import com.sudhirt.practice.easykanban.repository.SwimlaneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwimlaneService {

    @Autowired
    private SwimlaneRepository swimlaneRepository;

    @Autowired
    private BoardService boardService;

    @Transactional
    public Swimlane create(Swimlane swimlane, long boardId) {
        if (swimlane == null) {
			throw new IllegalArgumentException("Invalid argument 'null' provided.");
        }
        swimlaneRepository.findByBoardIdAndName(boardId, swimlane.getName()).ifPresent(b -> {
			throw new ResourceAlreadyExistsException("Swimlane", "name", swimlane.getName());
		});
        Optional<Board> boardHolder = boardService.get(boardId);
        Board board = boardHolder.orElseThrow(() -> new ResourceNotFoundException("Board", boardId));
        board.add(swimlane);
        return swimlaneRepository.save(swimlane);
    }

    public Optional<Swimlane> get(long id) {
		return swimlaneRepository.findById(id);
	}
}