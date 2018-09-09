package com.gamehub.tdd;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Score {
	int first;
	int second;
	int third;
	int bonus;

	boolean isStrike() {
		return first == 10;
	}

	boolean isSpare() {
		if (first != 10 && second != 10) {
			return (first + second) == 10;
		}
		return false;
	}
}
