package com.example.suko.controller;

import com.example.suko.entity.LevelCode;
import com.example.suko.entity.User;
import com.example.suko.repository.GameAttemptRepository;
import com.example.suko.repository.LevelRepository;
import com.example.suko.service.GameService;
import com.example.suko.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final GameAttemptRepository gameAttemptRepository;
    private final LevelRepository levelRepository;
    private final GameService gameService;

    public ProfileController(UserService userService,
                             GameAttemptRepository gameAttemptRepository,
                             LevelRepository levelRepository,
                             GameService gameService) {
        this.userService = userService;
        this.gameAttemptRepository = gameAttemptRepository;
        this.levelRepository = levelRepository;
        this.gameService = gameService;
    }

    @GetMapping
    public String profile(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("attempts", gameAttemptRepository.findTop10ByUserOrderByCompletedAtDesc(user));
        model.addAttribute("easyPuzzles", gameService.listPlayable(levelRepository.findByCode(LevelCode.EASY).orElseThrow()));
        model.addAttribute("mediumPuzzles", gameService.listPlayable(levelRepository.findByCode(LevelCode.MEDIUM).orElseThrow()));
        model.addAttribute("hardPuzzles", gameService.listPlayable(levelRepository.findByCode(LevelCode.HARD).orElseThrow()));
        return "profile/index";
    }
}
