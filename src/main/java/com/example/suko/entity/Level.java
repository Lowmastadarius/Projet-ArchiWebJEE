package com.example.suko.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 16)
    private LevelCode code;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "difficulty_weight", nullable = false)
    private Integer difficultyWeight;

    @Column
    private String description;

    public Long getId() {
        return id;
    }

    public LevelCode getCode() {
        return code;
    }

    public Integer getDifficultyWeight() {
        return difficultyWeight;
    }
}
