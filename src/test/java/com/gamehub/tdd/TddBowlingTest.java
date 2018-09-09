package com.gamehub.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * roll(int n)과 score()를 구현
 * 내부적으로 각 프레임(1-10)의 점수를 관리할 것
 * spare, strike 점수 구현
 * 범위를 초과했거나 횟수를 초과하여 roll 을 호출하면 IllegalArgumentException 과 IllegalStateException 발생
 * 최저 점수와 최대 점수까지 테스트에 반드시 반영
 * 새 테스트 통과마다 commit 해서 github 에 올릴 것
 * <p>
 * <p>
 * 1. 모두 거터인 경우
 * 2. 모두 오픈인 경우
 * 3. 모두 스페어(1)인 경우
 * 4. 모두 스페어(2)인 경우
 * 5. 모두 스트라이크인 경우
 */
public class TddBowlingTest {
	TddBowling bowling;

	@BeforeEach
	void beforeEach() {
		bowling = new TddBowling();
	}

	@Test
	void allGutter() {
		IntStream.range(0, 10).forEachOrdered(i -> bowling.roll(0));

		int score = bowling.score();

		assertThat(score).isEqualTo(0);
	}

	@Test
	void allOpen() {
		IntStream.range(0, 10).forEachOrdered(i -> bowling.roll(4));

		int score = bowling.score();

		assertThat(score).isEqualTo(40);
	}

	@Test
	void allSpare1() {
		IntStream.range(0, 10).forEachOrdered(i -> {
			int val = 9;
			if (i % 2 == 0) {
				val = 1;
			}
			bowling.roll(val);
		});

		int score = bowling.score();

		assertThat(score).isEqualTo(190);

	}

	@Test
	void allSpare2() {

	}

	@Test
	void allStrike() {

	}
}
