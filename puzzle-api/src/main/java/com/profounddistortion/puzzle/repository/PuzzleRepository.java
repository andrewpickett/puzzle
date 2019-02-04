package com.profounddistortion.puzzle.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.profounddistortion.puzzle.model.Puzzle;
import org.springframework.data.repository.query.Param;

public interface PuzzleRepository extends CrudRepository<Puzzle, Long> {
	String FIND_CURRENT_PUZZLE_FOR_USER = "" +
		"  SELECT * " +
		"    FROM puzzle " +
		"   WHERE id = IFNULL((SELECT next_puzzle_id " +
		"                        FROM puzzle " +
		"                       WHERE complete_time = (SELECT MAX(complete_time) " +
		"                                                FROM puzzle " +
		"                                               WHERE user_id = :userId)), " +
		"                     (SELECT MIN(id) FROM puzzle WHERE user_id = :userId))" +
		"     AND complete_time IS NULL";

	@Query(value=FIND_CURRENT_PUZZLE_FOR_USER, nativeQuery=true)
	Puzzle findCurrentPuzzleForUser(@Param("userId") Integer userId);

}
