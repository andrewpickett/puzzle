package com.profounddistortion.puzzle.repository;

import com.profounddistortion.puzzle.model.Hint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HintRepository extends CrudRepository<Hint, Long> {

	List<Hint> findByIdPuzzleId(long puzzleId);

}
