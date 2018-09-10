package com.gamehub.tdd;

public class Game {
	private int score = 0;
	private int[] rolls = new int[21];
	private int currentRole = 0;

	public void roll(int pins) {
		score += pins;
		rolls[currentRole++] = pins;
	}

	public int score() {
		return score;
	}
}
