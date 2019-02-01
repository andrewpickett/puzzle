package com.profounddistortion.puzzle.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.util.*;

@Entity
@Table(name = "puzzle")
@Data
public class Puzzle {
	@Id
	private Long id;
	private Long nextPuzzleId;
	private String title;
	private String description;
	private Date completeTime;
	private Integer userId;
	@Transient
	private List<CorrectAnswer> correctAnswers;
	@Transient
	private List<AnswerGuess> guesses;
	@Transient
	private List<Hint> hints;

//	public void setGuesses(List<AnswerGuess> guesses) {
//		if (guesses == null) {
//			guesses = new ArrayList<>();
//		}
//		this.guesses = guesses;
//		getAndSetMostRecentGuess();
//	}
//
//	public AnswerGuess getAndSetMostRecentGuess() {
//		AnswerGuess mostRecent = null;
//		if (guesses != null) {
//			Optional<AnswerGuess> opt = guesses.stream()
//				.max(Comparator.comparing(AnswerGuess::getGuessTime));
//			if (opt.isPresent()) {
//				mostRecent = opt.get();
//			}
//		}
//		this.mostRecentGuess = mostRecent;
//		return mostRecent;
//	}
}
