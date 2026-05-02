package com.example.suko.service;

import com.example.suko.entity.Level;
import com.example.suko.entity.Puzzle;
import com.example.suko.entity.SaveGame;
import com.example.suko.entity.User;
import com.example.suko.repository.SaveGameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SaveGameService {

    private final SaveGameRepository saveGameRepository;

    public SaveGameService(SaveGameRepository saveGameRepository) {
        this.saveGameRepository = saveGameRepository;
    }

    public SaveGame save(User user, Puzzle puzzle, Level level, String gridState, int elapsedSeconds) {
        SaveGame save = saveGameRepository.findByUserAndPuzzleAndCompletedFalse(user, puzzle).orElseGet(SaveGame::new);
        if (save.getId() == null) {
            save.setUser(user);
            save.setPuzzle(puzzle);
            save.setLevel(level);
            save.setStartedAt(LocalDateTime.now());
        }
        save.setGridState(gridState);
        save.setElapsedSeconds(elapsedSeconds);
        save.setSavedAt(LocalDateTime.now());
        save.setCompleted(false);
        return saveGameRepository.save(save);
    }

    public Optional<SaveGame> loadActive(User user, Puzzle puzzle) {
        return saveGameRepository.findByUserAndPuzzleAndCompletedFalse(user, puzzle);
    }

    public void markCompleted(User user, Puzzle puzzle) {
        saveGameRepository.findByUserAndPuzzleAndCompletedFalse(user, puzzle).ifPresent(save -> {
            save.setCompleted(true);
            save.setSavedAt(LocalDateTime.now());
            saveGameRepository.save(save);
        });
    }
}
