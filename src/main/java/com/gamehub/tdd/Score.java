package com.gamehub.tdd;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Score {
	int first;
	int second;
	int bonus;

	boolean isFirstStrkie() {
		return first == 10;
	}

	boolean isSecondStrike() {
		return second == 10;
	}

	boolean isSpare() {
		if (first != 10 && second != 10) {
			return (first + second) == 10;
		}
		return false;
	}
}
