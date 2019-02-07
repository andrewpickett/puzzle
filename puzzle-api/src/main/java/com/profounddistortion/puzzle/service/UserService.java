package com.profounddistortion.puzzle.service;

import com.profounddistortion.puzzle.model.ApplicationUser;
import com.profounddistortion.puzzle.model.Puzzle;
import com.profounddistortion.puzzle.model.UserSummary;
import com.profounddistortion.puzzle.repository.*;
import com.profounddistortion.puzzle.security.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PuzzleRepository puzzleRepo;
	@Autowired
	private HintRepository hintRepo;
	@Autowired
	private CorrectAnswerRepository correctAnswerRepo;
	@Autowired
	private AnswerGuessRepository answerGuessRepo;

	public Iterable<ApplicationUser> getAllUsers() {
		Iterable<ApplicationUser> users = userRepo.findAll();
		for (ApplicationUser user : users) {
			user.setPassword(null);
		}
		return users;
	}

	public ApplicationUser getUserFromJWTToken(boolean includePuzzles) {
		ApplicationUser user = null;

		JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			user = userRepo.findById(auth.getId()).get();
			if (user != null && includePuzzles) {
				user.setCurrentPuzzle(puzzleRepo.findCurrentPuzzleForUser(user.getId()));
				Puzzle currentPuzzle = user.getCurrentPuzzle();
				if (currentPuzzle != null) {
					if (currentPuzzle.getStartTime() == null) {
						currentPuzzle.setStartTime(new Date());
						puzzleRepo.save(currentPuzzle);
					}
					currentPuzzle.setHints(hintRepo.findByIdPuzzleId(currentPuzzle.getId()));
					currentPuzzle.setGuesses(answerGuessRepo.findByPuzzleIdOrderByGuessTimeDesc(currentPuzzle.getId()));
					currentPuzzle.setEnd(CollectionUtils.isEmpty(correctAnswerRepo.findByPuzzleId(currentPuzzle.getId())));
				}

				user.setSummary(new UserSummary(userRepo.getUserSummaryInfo(user.getId())));
			}
		}
		if (user == null) {
			throw new AccessDeniedException("You are not allowed to view this page.");
		} else {
			return user;
		}
	}
}
