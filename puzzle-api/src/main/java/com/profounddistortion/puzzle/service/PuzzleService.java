package com.profounddistortion.puzzle.service;

import com.profounddistortion.puzzle.model.AnswerGuess;
import com.profounddistortion.puzzle.model.CorrectAnswer;
import com.profounddistortion.puzzle.model.Hint;
import com.profounddistortion.puzzle.model.Puzzle;
import com.profounddistortion.puzzle.repository.AnswerGuessRepository;
import com.profounddistortion.puzzle.repository.CorrectAnswerRepository;
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
	@Autowired
	private CorrectAnswerRepository correctAnswerRepo;
	@Autowired
	private HintRepository hintRepo;

	public boolean submitAnswer(AnswerGuess answer, long earnedScore) {
		Date answerDate = new Date();
		answer.setGuessTime(answerDate);
		answer.setCorrect(false);

		Puzzle p = puzzleRepo.findById(answer.getPuzzleId()).get();

		if (p != null) {
			p.setCorrectAnswers(correctAnswerRepo.findByPuzzleId(p.getId()));

			for (CorrectAnswer ca : p.getCorrectAnswers()) {
				String officialAnswer = getOfficialAnswer(answer.getValue(), ca.isNormalized());
				if (officialAnswer.matches("\\A" + ca.getAnswer() + "\\Z")) {
					answer.setCorrect(true);
					p.setCompleteTime(answerDate);
					p.setNextPuzzleId(ca.getNextPuzzleId());
					p.setEarnedScore(earnedScore);
					puzzleRepo.save(p);
					break;
				}
			}
		}

		answerRepo.save(answer);
		return answer.isCorrect();
	}

	public Hint getHint(long puzzleId) {
		List<Hint> hints = hintRepo.findByIdPuzzleId(puzzleId);
		Hint nextAvailable = null;
		for (Hint hint : hints) {
			if (hint.isAvailable()) {
				nextAvailable = hint;
				break;
			}
		}

		if (nextAvailable != null) {
			nextAvailable.setAvailable(false);
			nextAvailable.setHintTime(new Date());
			hintRepo.save(nextAvailable);
		}
		return nextAvailable;
	}

	private String getOfficialAnswer(String answer, boolean normalize) {
		String officialAnswer = answer;
		if (normalize) {
			officialAnswer = answer.replaceAll("\\W", "").toUpperCase();
		}
		return officialAnswer;
	}
}
