package com.profounddistortion.puzzle.repository;

import org.springframework.data.repository.CrudRepository;

import com.profounddistortion.puzzle.model.Puzzle;

import java.util.List;

public interface PuzzleRepository extends CrudRepository<Puzzle, Long> {

	List<Puzzle> findAllByUserId(Integer id);
}
