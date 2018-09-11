package com.gamehub.tdd;

public class Game {
	private int[] rolls = new int[21];
	private int currentRole = 0;

	public void roll(int pins) {
		rolls[currentRole++] = pins;
	}

	public int score() {
		int score = 0;
		int rollIndex = 0;
		for (int frame = 0; frame < 10; frame++) {
			if (isSpare(rollIndex)) { //spare
				score += 10 + rolls[rollIndex + 2];
			} else {
				score += rolls[rollIndex] + rolls[rollIndex + 1];
			}
			rollIndex += 2;
		}
		return score;
	}

	private boolean isSpare(int rollIndex) {
		return rolls[rollIndex] + rolls[rollIndex + 1] == 10;
	}
}
