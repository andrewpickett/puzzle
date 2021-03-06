package com.profounddistortion.puzzle.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "hint")
@Data
public class Hint {
	@EmbeddedId
	private HintId id;
	private String description;
	@Column(name = "available_ind")
	private boolean available;
	private Date hintTime;

	public HintId getId() {
		return id;
	}

	public void setId(HintId id) {
		this.id = id;
	}
}
