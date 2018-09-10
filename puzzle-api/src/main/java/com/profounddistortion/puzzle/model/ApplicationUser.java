package com.profounddistortion.puzzle.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class ApplicationUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonIgnore
	@Transient
	private List<Puzzle> puzzles;
	@Transient
	private Puzzle currentPuzzle;
	@Transient
	private List<Puzzle> solvedPuzzles;
	@Transient
	@JsonIgnore
	private List<Puzzle> unsolvedPuzzles;

	public void setPuzzles(List<Puzzle> puzzles) {
		if (puzzles == null) {
			puzzles = new ArrayList<>();
		}
		this.puzzles = puzzles;
		setSolvedPuzzles();
		setUnsolvedPuzzles();
	}

	private void setSolvedPuzzles() {
		solvedPuzzles = puzzles.stream()
			.filter(x -> x.isComplete())
			.sorted(Comparator.comparing(Puzzle::getSequenceNum))
			.collect(Collectors.toList());
	}

	private void setUnsolvedPuzzles() {
		unsolvedPuzzles = puzzles.stream()
			.filter(x -> !x.isComplete())
			.sorted(Comparator.comparing(Puzzle::getSequenceNum))
			.collect(Collectors.toList());
	}

	public Puzzle getAndSetCurrentPuzzle() {
		Puzzle current = null;
		if (puzzles != null) {
			Optional<Puzzle> opt = puzzles.stream()
				.filter(x -> !x.isComplete())
				.min(Comparator.comparing(Puzzle::getSequenceNum));
			if (opt.isPresent()) {
				current = opt.get();
			}
		}
		this.currentPuzzle = current;
		return current;
	}
}
