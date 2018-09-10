package com.gamehub.tdd;

public class Game {
	private int[] rolls = new int[21];
	private int currentRole = 0;

	public void roll(int pins) {
		rolls[currentRole++] = pins;
	}

	public int score() {
		int score = 0;
		for (int i = 0; i < rolls.length; i++) {
			score += rolls[i];
		}
		return score;
	}
}
