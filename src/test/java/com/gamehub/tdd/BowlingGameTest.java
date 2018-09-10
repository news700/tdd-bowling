package com.gamehub.tdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameTest {
	@Test
	void allGutterGame() {
		Game game = new Game();
		for (int i = 0; i < 20; i++) {
			game.roll(0);
		}
		assertThat(game.score()).isEqualsTo(0);
	}

}
