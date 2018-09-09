package com.gamehub.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
 * 6. 점수가 범위를 벗어난 경우
 * 7. 게임이 종료된 경우
 * 8. 점수가 섞여있는 경우
 */
public class TddBowlingTest {
	TddBowling bowling;

	@BeforeEach
	void beforeEach() {
		bowling = new TddBowling();
	}

	@Test
	@DisplayName("모두 거터인 경우")
	void allGutter() {
		//20번을 던짐
		IntStream.range(0, 20).forEachOrdered(i -> {
			bowling.roll(0);
		});

		int score = bowling.score();

		assertThat(score).isEqualTo(0);
	}

	@Test
	@DisplayName("모두 오픈인 경우")
	void allOpen() {
		//20번을 던짐
		IntStream.range(0, 20).forEachOrdered(i -> {
			bowling.roll(4);
		});

		int score = bowling.score();

		assertThat(score).isEqualTo(80);
	}

	@Test
	@DisplayName("모두 스페어1인 경우")
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
	@DisplayName("모두 스페어2인 경우")
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
	@DisplayName("모두 스트라이크인 경우")
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
	@DisplayName("점수가 범위를 벗어난 경우")
	void valueRangeError() {
		assertThatThrownBy(() -> bowling.roll(-1)).isInstanceOf(IllegalArgumentException.class).hasMessage("val must between 0 to 10");
		assertThatThrownBy(() -> bowling.roll(11)).isInstanceOf(IllegalArgumentException.class).hasMessage("val must between 0 to 10");
	}

	@Test
	@DisplayName("게임이 종료된 경우")
	void frameRangeError() {
		//20번을 던짐
		IntStream.range(0, 20).forEachOrdered(i -> bowling.roll(0));

		assertThatThrownBy(() -> bowling.roll(0)).isInstanceOf(IllegalStateException.class).hasMessage("game over");
	}

	@Test
	@DisplayName("프레임별 점수가 다 다르지만 10프레임이 오픈인 경우")
	void mixedScoreFor10FrameOpen() {
		//todo
		bowling.roll(1);
		int score = bowling.score();
		assertThat(score).isEqualTo(1);
	}

	@Test
	@DisplayName("프레임별 점수가 다 다르지만 10프레임이 스페어인 경우")
	void mixedScoreFor10FrameSpare() {
		//todo
		bowling.roll(1);
		int score = bowling.score();
		assertThat(score).isEqualTo(1);
	}

	@Test
	@DisplayName("프레임별 점수가 다 다르지만 10프레임이 스트라이크인 경우")
	void mixedScoreFor10FrameStrike() {
		//todo
		bowling.roll(1);
		int score = bowling.score();
		assertThat(score).isEqualTo(1);
	}
}
