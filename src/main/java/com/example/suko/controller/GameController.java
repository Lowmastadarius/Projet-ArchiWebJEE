package com.example.suko.controller;

import com.example.suko.dto.GridForm;
import com.example.suko.entity.Puzzle;
import com.example.suko.entity.User;
import com.example.suko.service.GameService;
import com.example.suko.service.UserService;
import com.example.suko.service.ValidationResult;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping("/{puzzleId}")
    public String play(@PathVariable("puzzleId") Long puzzleId, Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        Puzzle puzzle = gameService.getPuzzle(puzzleId);
        GridForm gridForm = gameService.preloadForm(user, puzzleId);
        model.addAttribute("gridForm", gridForm);
        model.addAttribute("puzzle", puzzle);
        model.addAttribute("constraint", gameService.getConstraint(puzzle));
        return "game/play";
    }

    @PostMapping("/check")
    public String check(@ModelAttribute GridForm gridForm, Model model) {
        Puzzle puzzle = gameService.getPuzzle(gridForm.getPuzzleId());
        ValidationResult result = gameService.check(gridForm);
        model.addAttribute("gridForm", gridForm);
        model.addAttribute("puzzle", puzzle);
        model.addAttribute("constraint", gameService.getConstraint(puzzle));
        model.addAttribute(result.valid() ? "successMessage" : "errorMessage", result.message());
        return "game/play";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute GridForm gridForm, Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        gameService.saveProgress(user, gridForm);
        Puzzle puzzle = gameService.getPuzzle(gridForm.getPuzzleId());
        model.addAttribute("gridForm", gridForm);
        model.addAttribute("puzzle", puzzle);
        model.addAttribute("constraint", gameService.getConstraint(puzzle));
        model.addAttribute("successMessage", "Partie sauvegardee");
        return "game/play";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute GridForm gridForm, Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        try {
            int score = gameService.submit(user, gridForm);
            model.addAttribute("score", score);
            model.addAttribute("elapsedSeconds", gridForm.getElapsedSeconds());
            return "game/result";
        } catch (IllegalArgumentException ex) {
            Puzzle puzzle = gameService.getPuzzle(gridForm.getPuzzleId());
            model.addAttribute("gridForm", gridForm);
            model.addAttribute("puzzle", puzzle);
            model.addAttribute("constraint", gameService.getConstraint(puzzle));
            model.addAttribute("errorMessage", ex.getMessage());
            return "game/play";
        }
    }
}
