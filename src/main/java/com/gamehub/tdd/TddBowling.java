package com.gamehub.tdd;

import java.util.HashMap;
import java.util.Map;

public class TddBowling {
	private Map<Integer, Integer> board = new HashMap<>();

	private int bonus = 0;

	public void roll(int frame, int val) {
		Integer first = board.get(frame);
		if (first != null) {
			val += first + bonus;
		} else {
			bonus = val;
		}
		board.put(frame, val);
	}

	public int score() {
		return board.keySet().stream().map(key -> board.get(key)).mapToInt(Integer::intValue).sum();
	}
}
