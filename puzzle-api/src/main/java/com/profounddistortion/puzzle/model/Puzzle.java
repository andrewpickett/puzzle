package com.profounddistortion.puzzle.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "puzzle")
@Data
public class Puzzle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private double sequenceNum;
	private String title;
	private String description;
	@JsonIgnore
	private String answer;
	@Column(name = "complete_ind")
	private boolean complete;
	private Integer userId;
	@Transient
	private ApplicationUser user;
	@Transient
	private AnswerGuess mostRecentGuess;
	@Transient
	private List<AnswerGuess> guesses;
	@Transient
	private List<Hint> hints;

	public void setGuesses(List<AnswerGuess> guesses) {
		if (guesses == null) {
			guesses = new ArrayList<>();
		}
		this.guesses = guesses;
		getAndSetMostRecentGuess();
	}

	public AnswerGuess getAndSetMostRecentGuess() {
		AnswerGuess mostRecent = null;
		if (guesses != null) {
			Optional<AnswerGuess> opt = guesses.stream()
				.max(Comparator.comparing(AnswerGuess::getGuessTime));
			if (opt.isPresent()) {
				mostRecent = opt.get();
			}
		}
		this.mostRecentGuess = mostRecent;
		return mostRecent;
	}
}
