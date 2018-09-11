package com.gamehub.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameTest {
	Game game;

	@BeforeEach
	void beforeEach() {
		game = new Game();
	}

	@Test
	void allGutterGame() {
		rollMany(20, 0);
		assertThat(game.score()).isEqualTo(0);
	}

	@Test
	void allOneGame() {
		rollMany(20, 1);
		assertThat(game.score()).isEqualTo(20);
	}

	void rollMany(int count, int pins) {
		for (int i = 0; i < count; i++) {
			game.roll(pins);
		}
	}

	@Test
	void oneSpareGame() {
		game.roll(8);
		game.roll(2);
		game.roll(3);
		rollMany(17, 0);
		assertThat(game.score()).isEqualTo(16);
	}


}
