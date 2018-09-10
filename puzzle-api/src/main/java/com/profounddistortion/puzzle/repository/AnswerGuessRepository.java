package com.profounddistortion.puzzle.repository;

import com.profounddistortion.puzzle.model.AnswerGuess;
import com.profounddistortion.puzzle.model.Puzzle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerGuessRepository extends CrudRepository<AnswerGuess, Long> {

	List<AnswerGuess> findByPuzzleIdOrderByGuessTimeDesc(long puzzleId);

}
