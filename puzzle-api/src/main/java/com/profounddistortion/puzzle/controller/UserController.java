package com.profounddistortion.puzzle.controller;

import com.profounddistortion.puzzle.model.AnswerGuess;
import com.profounddistortion.puzzle.model.ApplicationUser;
import com.profounddistortion.puzzle.model.Puzzle;
import com.profounddistortion.puzzle.repository.AnswerGuessRepository;
import com.profounddistortion.puzzle.repository.PuzzleRepository;
import com.profounddistortion.puzzle.service.PuzzleService;
import com.profounddistortion.puzzle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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

	@RequestMapping(value = "/completed", method = RequestMethod.GET)
	public List<Puzzle> getCompletedPuzzlesForUser() {
		ApplicationUser user = userService.getUserFromJWTToken(true);
		puzzleService.populateGuessesOnPuzzles(user.getSolvedPuzzles());
		return user.getSolvedPuzzles();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Iterable<ApplicationUser> allUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public boolean submitAnswer(@RequestBody AnswerGuess answer) {
		return puzzleService.submitAnswer(answer);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletResponse response) {
		return response.getHeader(HttpHeaders.AUTHORIZATION);
	}
}
