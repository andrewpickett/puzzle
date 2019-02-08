package com.profounddistortion.puzzle.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class HintId implements Serializable {
	@Column(name = "puzzle_id")
	private long puzzleId;
	@Column(name = "seq_num")
	private long seqNum;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HintId hintId = (HintId) o;
		return puzzleId == hintId.puzzleId &&
			seqNum == hintId.seqNum;
	}

	@Override
	public int hashCode() {
		return Objects.hash(puzzleId, seqNum);
	}

}
