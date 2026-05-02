package com.example.suko.service;

import com.example.suko.entity.Level;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    public int calculateScore(Level level, int elapsedSeconds, boolean usedResume) {
        int base = 1000;
        int difficultyMultiplier = level.getDifficultyWeight();
        int timePenalty = Math.max(0, elapsedSeconds / 5);
        int resumePenalty = usedResume ? 50 : 0;
        return Math.max(100, base * difficultyMultiplier - timePenalty - resumePenalty);
    }
}
