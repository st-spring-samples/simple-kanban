package com.sudhirt.practice.easykanban.repository;

import java.util.Optional;

import com.sudhirt.practice.easykanban.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Optional<Board> findByName(String name);

}