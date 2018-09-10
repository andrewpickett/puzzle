package com.profounddistortion.puzzle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hint")
@Data
public class Hint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long puzzleId;
	private String description;
}
