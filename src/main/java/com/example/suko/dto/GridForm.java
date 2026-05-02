package com.example.suko.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GridForm {

    @NotNull
    private Long puzzleId;

    private Integer elapsedSeconds = 0;

    private Integer[] cells = new Integer[9];

    public Long getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    public Integer[] getCells() {
        return cells;
    }

    public void setCells(Integer[] cells) {
        this.cells = cells;
    }

    @Min(0)
    public Integer getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(Integer elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }
}
