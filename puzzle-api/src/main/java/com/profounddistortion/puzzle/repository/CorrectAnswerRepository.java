package com.profounddistortion.puzzle.repository;

import com.profounddistortion.puzzle.model.CorrectAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CorrectAnswerRepository extends CrudRepository<CorrectAnswer, Long> {

	List<CorrectAnswer> findByPuzzleId(long puzzleId);

}
