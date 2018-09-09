package com.gamehub.tdd;

import java.util.HashMap;
import java.util.Map;

public class TddBowling {
	private Map<Integer, Score> board = new HashMap<>();
	private boolean isThird = false;

	public void roll(int frame, int val) {
		Score score = board.get(frame);

		if (score == null) { //각 프레임의 첫번째 점수
			if (frame > 1) { //1번째, 11번째 프레임은 이전 프레임에 보너스를 주지 않음
				//이전 프레임에 보너스 점수를 첫번째 점수로 넣어줌 (이전 프레임이 스트라이크인 or 스페어인 경우)
				int prevFrame = frame - 1;
				Score prevScore = board.get(prevFrame);
				if (prevScore.isStrike() || prevScore.isSpare()) {
					//이전 프레임 첫번째가 스트라이크인 경우 or 이전 프레임이 스페어인 경우
					prevScore.setBonus(val);
					board.put(prevFrame, prevScore);
					if (prevScore.isStrike()) {
						if (frame > 2) {
							int morePrevFrame = prevFrame - 1;
							Score morePrevScore = board.get(morePrevFrame);
							if (morePrevScore.isStrike()) {
								int morePrevBonus = morePrevScore.getBonus();
								morePrevScore.setBonus(morePrevBonus + val);
								board.put(morePrevFrame, morePrevScore);
							}
						}
					}
				}
			}

			//프레임에 첫번째 점수를 넣어줌
			board.put(frame, Score.builder().first(val).build());
			isThird = false;
		} else { //각 프레임의 두번째 점수
			if (frame > 1) { //1번째, 11번째 프레임은 이전 프레임에 보너스를 주지 않음
				if (!isThird) {
					//이전 프레임에 보너스 점수를 두번째 점수를 합산해서 넣어줌 (이전 프레임이 스트라이크인 경우)
					int prevFrame = frame - 1;
					Score prevScore = board.get(prevFrame);
					if (prevScore.isStrike()) {
						//이전 프레임 첫번째가 스트라이크인 경우
						int prevBonus = prevScore.getBonus();
						prevScore.setBonus(prevBonus + val);
						board.put(prevFrame, prevScore);
					}
				}
			}

			//프레임에 두번째 점수를 넣어줌
			if (isThird) {
				score.setThird(val);
				board.put(frame, score);
			} else {
				score.setSecond(val);
				board.put(frame, score);
			}

			isThird = true;
		}
	}

	public int score() {
		return board.keySet().stream().map(key -> board.get(key)).mapToInt(score -> score.getFirst() + score.getSecond() + score.getThird() + score.getBonus()).sum();
	}
}
