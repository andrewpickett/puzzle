package com.profounddistortion.puzzle.service;

import com.profounddistortion.puzzle.model.ApplicationUser;
import com.profounddistortion.puzzle.model.Puzzle;
import com.profounddistortion.puzzle.repository.AnswerGuessRepository;
import com.profounddistortion.puzzle.repository.HintRepository;
import com.profounddistortion.puzzle.repository.PuzzleRepository;
import com.profounddistortion.puzzle.repository.UserRepository;
import com.profounddistortion.puzzle.security.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PuzzleRepository puzzleRepo;
	@Autowired
	private AnswerGuessRepository answerRepo;
	@Autowired
	private HintRepository hintRepo;

	public Iterable<ApplicationUser> getAllUsers() {
		Iterable<ApplicationUser> users = userRepo.findAll();
		for (ApplicationUser user : users) {
			user.setUnsolvedPuzzles(null);
			user.setPuzzles(null);
			user.setCurrentPuzzle(null);
			user.setPassword(null);
			user.setSolvedPuzzles(null);
		}
		return users;
	}

	public ApplicationUser getUserFromJWTToken(boolean includePuzzles) {
		ApplicationUser user = null;

		JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			user = userRepo.findOne(auth.getId());
			if (user != null && includePuzzles) {
				user.setPuzzles(puzzleRepo.findAllByUserId(user.getId()));
				Puzzle currentPuzzle = user.getAndSetCurrentPuzzle();
				if (currentPuzzle != null) {
					currentPuzzle.setGuesses(answerRepo.findByPuzzleIdOrderByGuessTimeDesc(currentPuzzle.getId()));
					currentPuzzle.setHints(hintRepo.findByPuzzleId(currentPuzzle.getId()));
				}
			}
		}
		if (user == null) {
			throw new AccessDeniedException("You are not allowed to view this page.");
		} else {
			return user;
		}
	}
}
