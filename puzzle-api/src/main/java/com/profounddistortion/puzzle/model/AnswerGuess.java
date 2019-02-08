package com.profounddistortion.puzzle.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "answer_guess")
@Data
public class AnswerGuess {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long puzzleId;
	private String value;
	private Date guessTime;
	@Column(name = "correct_ind")
	private boolean correct;
}
