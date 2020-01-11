package com.sudhirt.practice.easykanban.repository;

import java.util.Optional;

import com.sudhirt.practice.easykanban.entity.Swimlane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwimlaneRepository extends JpaRepository<Swimlane, Long> {
    
    Optional<Swimlane> findByBoardIdAndName(long boardId, String name);
}