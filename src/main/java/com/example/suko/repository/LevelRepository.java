package com.example.suko.repository;

import com.example.suko.entity.Level;
import com.example.suko.entity.LevelCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByCode(LevelCode code);
}
