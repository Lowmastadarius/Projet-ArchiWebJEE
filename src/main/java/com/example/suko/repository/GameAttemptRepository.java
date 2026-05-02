package com.example.suko.repository;

import com.example.suko.entity.GameAttempt;
import com.example.suko.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameAttemptRepository extends JpaRepository<GameAttempt, Long> {
    List<GameAttempt> findTop10ByUserOrderByCompletedAtDesc(User user);
}
