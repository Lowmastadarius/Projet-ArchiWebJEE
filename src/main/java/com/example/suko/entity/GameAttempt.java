package com.example.suko.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_attempts")
public class GameAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Column(name = "final_grid_state", nullable = false)
    private String finalGridState;

    @Column(name = "completion_seconds", nullable = false)
    private Integer completionSeconds;

    @Column(name = "score_earned", nullable = false)
    private Integer scoreEarned;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt = LocalDateTime.now();

    public Integer getScoreEarned() {
        return scoreEarned;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setFinalGridState(String finalGridState) {
        this.finalGridState = finalGridState;
    }

    public void setCompletionSeconds(Integer completionSeconds) {
        this.completionSeconds = completionSeconds;
    }

    public void setScoreEarned(Integer scoreEarned) {
        this.scoreEarned = scoreEarned;
    }
}
