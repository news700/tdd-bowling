package com.gamehub.tdd;

public class TddBowling {
	int score = 0;

	public void roll(int val) {
		score += val;
	}

	public int score() {
		return score;
	}
}
