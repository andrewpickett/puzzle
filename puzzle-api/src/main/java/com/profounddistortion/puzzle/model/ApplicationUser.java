package com.profounddistortion.puzzle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
@Data
public class ApplicationUser {
	@Id
	private Integer id;
	private String name;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private boolean admin;
	@Transient
	private Puzzle currentPuzzle;
	@Transient
	private UserSummary summary;
}
