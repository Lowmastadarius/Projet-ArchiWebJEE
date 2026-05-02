package com.example.suko.service;

import com.example.suko.dto.GridForm;
import com.example.suko.entity.GameAttempt;
import com.example.suko.entity.Level;
import com.example.suko.entity.Puzzle;
import com.example.suko.entity.PuzzleConstraint;
import com.example.suko.entity.PuzzleStatus;
import com.example.suko.entity.SaveGame;
import com.example.suko.entity.User;
import com.example.suko.repository.GameAttemptRepository;
import com.example.suko.repository.PuzzleConstraintRepository;
import com.example.suko.repository.PuzzleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final PuzzleRepository puzzleRepository;
    private final PuzzleConstraintRepository puzzleConstraintRepository;
    private final ValidationService validationService;
    private final SaveGameService saveGameService;
    private final ScoreService scoreService;
    private final GameAttemptRepository gameAttemptRepository;
    private final UserService userService;

    public GameService(PuzzleRepository puzzleRepository,
                       PuzzleConstraintRepository puzzleConstraintRepository,
                       ValidationService validationService,
                       SaveGameService saveGameService,
                       ScoreService scoreService,
                       GameAttemptRepository gameAttemptRepository,
                       UserService userService) {
        this.puzzleRepository = puzzleRepository;
        this.puzzleConstraintRepository = puzzleConstraintRepository;
        this.validationService = validationService;
        this.saveGameService = saveGameService;
        this.scoreService = scoreService;
        this.gameAttemptRepository = gameAttemptRepository;
        this.userService = userService;
    }

    public Puzzle getPuzzle(Long puzzleId) {
        return puzzleRepository.findById(puzzleId).orElseThrow();
    }

    public List<Puzzle> listPlayable(Level level) {
        return puzzleRepository.findByLevelAndStatus(level, PuzzleStatus.ACTIVE);
    }

    public PuzzleConstraint getConstraint(Puzzle puzzle) {
        return puzzleConstraintRepository.findByPuzzle(puzzle).orElseThrow();
    }

    public ValidationResult check(GridForm form) {
        Puzzle puzzle = getPuzzle(form.getPuzzleId());
        return validationService.validatePartial(form.getCells(), getConstraint(puzzle));
    }

    @Transactional
    public SaveGame saveProgress(User user, GridForm form) {
        Puzzle puzzle = getPuzzle(form.getPuzzleId());
        String gridState = toGridState(form.getCells());
        return saveGameService.save(user, puzzle, puzzle.getLevel(), gridState, form.getElapsedSeconds());
    }

    @Transactional
    public int submit(User user, GridForm form) {
        Puzzle puzzle = getPuzzle(form.getPuzzleId());
        PuzzleConstraint constraint = getConstraint(puzzle);
        ValidationResult result = validationService.validateComplete(form.getCells(), constraint);
        if (!result.valid()) {
            throw new IllegalArgumentException(result.message());
        }

        boolean usedResume = saveGameService.loadActive(user, puzzle).isPresent();
        int score = scoreService.calculateScore(puzzle.getLevel(), form.getElapsedSeconds(), usedResume);

        GameAttempt attempt = new GameAttempt();
        attempt.setUser(user);
        attempt.setPuzzle(puzzle);
        attempt.setLevel(puzzle.getLevel());
        attempt.setFinalGridState(toGridState(form.getCells()));
        attempt.setCompletionSeconds(form.getElapsedSeconds());
        attempt.setScoreEarned(score);
        gameAttemptRepository.save(attempt);

        userService.addScore(user, score);
        saveGameService.markCompleted(user, puzzle);
        return score;
    }

    public GridForm preloadForm(User user, Long puzzleId) {
        Puzzle puzzle = getPuzzle(puzzleId);
        GridForm form = new GridForm();
        form.setPuzzleId(puzzleId);
        saveGameService.loadActive(user, puzzle).ifPresent(save -> {
            form.setCells(fromGridState(save.getGridState()));
            form.setElapsedSeconds(save.getElapsedSeconds());
        });
        return form;
    }

    private String toGridState(Integer[] cells) {
        StringBuilder builder = new StringBuilder();
        for (Integer cell : cells) {
            builder.append(cell == null ? 0 : cell);
        }
        return builder.toString();
    }

    private Integer[] fromGridState(String state) {
        Integer[] cells = new Integer[9];
        for (int i = 0; i < 9; i++) {
            char c = state.charAt(i);
            cells[i] = c == '0' ? null : Character.getNumericValue(c);
        }
        return cells;
    }
}
