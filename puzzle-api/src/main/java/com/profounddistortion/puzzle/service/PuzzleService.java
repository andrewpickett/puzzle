package com.profounddistortion.puzzle.service;

import com.profounddistortion.puzzle.model.AnswerGuess;
import com.profounddistortion.puzzle.model.Puzzle;
import com.profounddistortion.puzzle.repository.AnswerGuessRepository;
import com.profounddistortion.puzzle.repository.HintRepository;
import com.profounddistortion.puzzle.repository.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PuzzleService {
	@Autowired
	private PuzzleRepository puzzleRepo;
	@Autowired
	private AnswerGuessRepository answerRepo;

	public boolean submitAnswer(AnswerGuess answer) {
		boolean correctAnswer = false;

		answer.setGuessTime(new Date());
		answerRepo.save(answer);

		Puzzle p = puzzleRepo.findOne(answer.getPuzzleId());

		if (p.getAnswer().equals(answer.getValue().toUpperCase())) {
			p.setComplete(true);
			puzzleRepo.save(p);
			correctAnswer = true;
		}
		return correctAnswer;
	}

	public void populateGuessesOnPuzzles(List<Puzzle> puzzles) {
		for (Puzzle p : puzzles) {
			p.setGuesses(answerRepo.findByPuzzleIdOrderByGuessTimeDesc(p.getId()));
		}
	}
}
