package com.profounddistortion.puzzle.repository;

import com.profounddistortion.puzzle.model.Hint;
import com.profounddistortion.puzzle.model.HintId;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Column;
import java.util.List;

public interface HintRepository extends CrudRepository<Hint, Long> {

	Hint findById(HintId id);

	List<Hint> findByIdPuzzleId(long puzzleId);

}
