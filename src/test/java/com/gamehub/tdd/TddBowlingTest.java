package com.gamehub.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
 * 6. roll 호출 시 점수의 범위를 넘어간 경우
 * 7. roll 호출 시 지정 횟수보다 많이 호출 한 경우
 *
 */
public class TddBowlingTest {
	TddBowling bowling;

	@BeforeEach
	void beforeEach() {
		bowling = new TddBowling();
	}

	@Test
	void allGutter() {
		//20번을 던짐
		IntStream.range(0, 20).forEachOrdered(i -> {
			bowling.roll(0);
		});

		int score = bowling.score();

		assertThat(score).isEqualTo(0);
	}

	@Test
	void allOpen() {
		//20번을 던짐
		IntStream.range(0, 20).forEachOrdered(i -> {
			bowling.roll(4);
		});

		int score = bowling.score();

		assertThat(score).isEqualTo(80);
	}

	@Test
	void allSpare1() {
		//20번을 던지고
		IntStream.range(0, 20).forEachOrdered(i -> {
			int val = 9; //각 프레임의 첫번째 점수
			if (i % 2 > 0) {
				val = 1; //각 프레임의 두번째 점수
			}
			bowling.roll(val);
		});

		//마지막 프레임이 스페어 이기 때문에 한번 더 roll 을 함
		bowling.roll(9);

		int score = bowling.score();

		assertThat(score).isEqualTo(190);
	}

	@Test
	void allSpare2() {
		//15번을 먼저 던지고
		IntStream.range(0, 15).forEachOrdered(i -> {
			switch (i % 3) {
				case 0:
					//1번째 점수
					bowling.roll(8);
					break;
				case 1:
					//2번째 점수
					bowling.roll(2);
					break;
				case 2:
					//3번째 점수
					bowling.roll(10);
					break;
			}
		});

		//마지막 프레임이 스트라이크 이기 때문에 두번 더 roll 을 함
		bowling.roll(8);
		bowling.roll(2);

		int score = bowling.score();

		assertThat(score).isEqualTo(200);
	}

	@Test
	void allStrike() {
		IntStream.range(0, 20).forEachOrdered(i -> {
			if (i % 2 == 0) {
				bowling.roll(10);
			}
		});

		//마지막 프레임이 스트라이크 이기 때문에 두번 더 roll 을 함
		bowling.roll(10);
		bowling.roll(10);

		int score = bowling.score();

		assertThat(score).isEqualTo(300);
	}

	@Test
	void valueRangeError() {
		assertThatThrownBy(() -> bowling.roll(-1)).isInstanceOf(IllegalArgumentException.class).hasMessage("val must between 0 to 10");
		assertThatThrownBy(() -> bowling.roll(11)).isInstanceOf(IllegalArgumentException.class).hasMessage("val must between 0 to 10");
	}

	@Test
	void frameRangeError() {
		//20번을 던짐
		IntStream.range(0, 20).forEachOrdered(i -> bowling.roll(0));

		assertThatThrownBy(() -> bowling.roll(0)).isInstanceOf(IllegalStateException.class).hasMessage("game over");
	}
}
