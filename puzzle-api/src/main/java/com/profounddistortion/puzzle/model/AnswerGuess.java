package com.profounddistortion.puzzle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answer_guess")
@Data
public class AnswerGuess {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long puzzleId;
	private String value;
	private Date guessTime;
}
