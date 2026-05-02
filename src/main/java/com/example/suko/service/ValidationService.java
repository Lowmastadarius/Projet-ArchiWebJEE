package com.example.suko.service;

import com.example.suko.entity.PuzzleConstraint;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ValidationService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ValidationResult validatePartial(Integer[] grid, PuzzleConstraint constraint) {
        if (grid == null || grid.length != 9) {
            return new ValidationResult(false, "Grille invalide");
        }
        Set<Integer> seen = new HashSet<>();
        for (Integer value : grid) {
            if (value == null || value == 0) {
                continue;
            }
            if (value < 1 || value > 9) {
                return new ValidationResult(false, "Les valeurs doivent etre entre 1 et 9");
            }
            if (!seen.add(value)) {
                return new ValidationResult(false, "Doublon detecte");
            }
        }

        Map<String, List<Integer>> circles = parseMap(constraint.getCircleCellMap());
        Map<String, Integer> targetSums = circleTargetSums(constraint);
        for (Map.Entry<String, List<Integer>> entry : circles.entrySet()) {
            int sum = 0;
            for (Integer idx : entry.getValue()) {
                Integer value = grid[idx];
                if (value != null && value > 0) {
                    sum += value;
                }
            }
            if (sum > targetSums.getOrDefault(entry.getKey(), Integer.MAX_VALUE)) {
                return new ValidationResult(false, "Somme partielle depassee pour " + entry.getKey());
            }
        }
        return new ValidationResult(true, "Validation partielle OK");
    }

    public ValidationResult validateComplete(Integer[] grid, PuzzleConstraint constraint) {
        ValidationResult partial = validatePartial(grid, constraint);
        if (!partial.valid()) {
            return partial;
        }
        Set<Integer> values = new HashSet<>();
        for (Integer value : grid) {
            if (value == null || value < 1 || value > 9) {
                return new ValidationResult(false, "Grille incomplete");
            }
            values.add(value);
        }
        if (values.size() != 9) {
            return new ValidationResult(false, "La grille doit contenir 1 a 9 sans repetition");
        }

        Map<String, List<Integer>> circles = parseMap(constraint.getCircleCellMap());
        Map<String, Integer> targetSums = circleTargetSums(constraint);
        for (Map.Entry<String, List<Integer>> entry : circles.entrySet()) {
            int sum = entry.getValue().stream().mapToInt(idx -> grid[idx]).sum();
            if (sum != targetSums.getOrDefault(entry.getKey(), -1)) {
                return new ValidationResult(false, "Somme invalide pour " + entry.getKey());
            }
        }
        return new ValidationResult(true, "Puzzle resolu");
    }

    private Map<String, Integer> circleTargetSums(PuzzleConstraint c) {
        Map<String, Integer> sums = new HashMap<>();
        sums.put("C1", c.getCircleSum1());
        sums.put("C2", c.getCircleSum2());
        sums.put("C3", c.getCircleSum3());
        sums.put("C4", c.getCircleSum4());
        return sums;
    }

    private Map<String, List<Integer>> parseMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception ex) {
            throw new IllegalStateException("Constraint JSON invalide", ex);
        }
    }
}
