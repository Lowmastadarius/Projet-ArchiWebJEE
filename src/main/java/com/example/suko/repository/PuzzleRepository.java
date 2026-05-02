package com.example.suko.repository;

import com.example.suko.entity.Level;
import com.example.suko.entity.Puzzle;
import com.example.suko.entity.PuzzleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {
    List<Puzzle> findByLevelAndStatus(Level level, PuzzleStatus status);
}
