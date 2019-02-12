package com.profounddistortion.puzzle.model;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "puzzle")
@Data
public class Puzzle {
	private static final long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;
	@Id
	private Long id;
	private Long nextPuzzleId;
	private String description;
	private Date startTime;
	private Date completeTime;
	private Integer userId;
	private Long maxScore;
	private Long earnedScore;
	private long pointsPerDay;
	private long pointsPerHint;
	private long pointsPerIncorrect;
	@Transient
	private boolean end;
	@Transient
	private List<CorrectAnswer> correctAnswers;
	@Transient
	private List<AnswerGuess> guesses;
	@Transient
	private List<Hint> hints;
	@Transient
	private double currentScore;

	public double getCurrentScore() {
		long days = 0;
		this.currentScore = 1.0 * maxScore;
		if (startTime != null) {
			long elapsedMillis = (DateUtils.truncate(new Date(), Calendar.DATE).getTime() - DateUtils.truncate(startTime, Calendar.DATE).getTime());
			if (completeTime != null) {
				elapsedMillis = (DateUtils.truncate(completeTime, Calendar.DATE).getTime() - DateUtils.truncate(startTime, Calendar.DATE).getTime());
			}
			days = (elapsedMillis / MILLIS_PER_DAY);
			this.currentScore -= (days * pointsPerDay);
		}

		long usedHints = 0;
		if (hints != null) {
			for (Hint hint : hints) {
				if (!hint.isAvailable()) {
					usedHints++;
				}
			}
		}
		long wrongGuesses = 0;
		if (guesses != null) {
			for (AnswerGuess guess : guesses) {
				if (!guess.isCorrect()) {
					wrongGuesses++;
				}
			}
		}
		this.currentScore -= (usedHints * pointsPerHint);
		this.currentScore -= (wrongGuesses * pointsPerIncorrect);

		if (this.currentScore < 0) {
			this.currentScore = 0;
		}
		return this.currentScore;
	}
}
