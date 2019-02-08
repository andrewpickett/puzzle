package com.profounddistortion.puzzle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "correct_answer")
@Data
public class CorrectAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long puzzleId;
	private Long nextPuzzleId;
	@JsonIgnore
	private String answer;
	@Column(name = "normalized_ind")
	private boolean normalized;
}
