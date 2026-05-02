package com.example.suko.repository;

import com.example.suko.entity.Puzzle;
import com.example.suko.entity.SaveGame;
import com.example.suko.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaveGameRepository extends JpaRepository<SaveGame, Long> {
    Optional<SaveGame> findByUserAndPuzzleAndCompletedFalse(User user, Puzzle puzzle);
    List<SaveGame> findByUserAndCompletedFalse(User user);
}
