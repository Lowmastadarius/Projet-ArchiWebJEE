package com.example.suko.repository;

import com.example.suko.entity.Puzzle;
import com.example.suko.entity.PuzzleConstraint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PuzzleConstraintRepository extends JpaRepository<PuzzleConstraint, Long> {
    Optional<PuzzleConstraint> findByPuzzle(Puzzle puzzle);
}
