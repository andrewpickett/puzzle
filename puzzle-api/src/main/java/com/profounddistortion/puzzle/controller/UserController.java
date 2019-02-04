package com.profounddistortion.puzzle.controller;

import com.profounddistortion.puzzle.model.AnswerGuess;
import com.profounddistortion.puzzle.model.ApplicationUser;
import com.profounddistortion.puzzle.service.PuzzleService;
import com.profounddistortion.puzzle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PuzzleService puzzleService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ApplicationUser getCurrentPuzzleForUser() {
		return userService.getUserFromJWTToken(true);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Iterable<ApplicationUser> allUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public boolean submitAnswer(@RequestBody AnswerGuess answer, @RequestParam long earnedScore) {
		return puzzleService.submitAnswer(answer, earnedScore);
	}

	@RequestMapping(value = "/hint", method = RequestMethod.POST)
	public boolean getHint(@RequestParam long puzzleId) {
		return puzzleService.getHint(puzzleId) != null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletResponse response) {
		return response.getHeader(HttpHeaders.AUTHORIZATION);
	}
}
