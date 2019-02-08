package com.profounddistortion.puzzle.model;

import lombok.Data;

@Data
public class UserSummary {
	private int puzzleNum;
	private long totalPossibleScore;
	private long currentScore;

	public UserSummary() {
	}

	public UserSummary(int puzzleNum, long totalPossibleScore, long currentScore) {
		this.puzzleNum = puzzleNum;
		this.totalPossibleScore = totalPossibleScore;
		this.currentScore = currentScore;
	}

	/**
	 * This assumes the parameter array passed in matches the constructor above.
	 *
	 * @param params
	 */
	public UserSummary(Object[] params) {
		setValues(params);
	}

	/**
	 * This assumes the parameter is an object array
	 *
	 * @param p
	 */
	public UserSummary(Object p) {
		if (p instanceof Object[]) {
			setValues((Object[]) p);
		}
	}

	private void setValues(Object[] params) {
		if (params != null) {
			this.puzzleNum = params[0] == null ? 1 : getValueFromParam(params[0], Integer.class);
			this.totalPossibleScore = params[1] == null ? 0 : getValueFromParam(params[1], Long.class);
			this.currentScore = params[2] == null ? 0 : getValueFromParam(params[2], Long.class);
		}
	}

	private <T extends Number> T getValueFromParam(Object param, Class<T> clazz) {
		Number p = (Number) param;
		if (param != null) {
			if (clazz.isAssignableFrom(Long.class)) {
				p = clazz.cast(p.longValue());
			} else if (clazz.isAssignableFrom(Double.class)) {
				p = clazz.cast(p.doubleValue());
			} else if (clazz.isAssignableFrom(Integer.class)) {
				p = clazz.cast(p.intValue());
			} else {
				p = clazz.cast(p);
			}
		}
		return (T) p;
	}

	public int getScorePerc() {
		if (totalPossibleScore == 0) {
			return 0;
		} else {
			return (int) ((100 * currentScore) / totalPossibleScore);
		}
	}
}
