package com.gamehub.tdd;

import java.util.HashMap;
import java.util.Map;

public class TddBowling {
	private Map<Integer, Score> board = new HashMap<>();

	public void roll(int frame, int val) {
		Score score = board.get(frame);

		if (score == null) { //각 프레임의 첫번째 점수
			if (frame > 1 && frame < 11) { //1번째, 11번째 프레임은 이전 프레임에 보너스를 주지 않음
				//이전 프레임에 보너스 점수를 첫번째 점수로 넣어줌 (이전 프레임이 스트라이크인 or 스페어인 경우)
				int prevFrame = frame - 1;
				Score prevScore = board.get(prevFrame);
				if (prevScore.isFirstStrkie() || prevScore.isSpare()) {
					//이전 프레임 첫번째가 스트라이크인 경우 or 이전 프레임이 스페어인 경우
					prevScore.setBonus(val);
					board.put(prevFrame, prevScore);
				}
			}
			//프레임에 첫번째 점수를 넣어줌
			board.put(frame, Score.builder().first(val).build());
		} else { //각 프레임의 두번째 점수
			if (frame > 1 && frame < 11) { //1번째, 11번째 프레임은 이전 프레임에 보너스를 주지 않음
				//이전 프레임에 보너스 점수를 두번째 점수를 합산해서 넣어줌 (이전 프레임이 스트라이크인 경우)
				int prevFrame = frame - 1;
				Score prevScore = board.get(prevFrame);
				if (prevScore.isFirstStrkie()) {
					//이전 프레임 첫번째가 스트라이크인 경우
					int prevBonus = prevScore.getBonus();
					prevScore.setBonus(prevBonus + val);
					board.put(prevFrame, prevScore);
				}
			}
			//프레임에 첫번째 점수를 넣어줌
			score.setSecond(val);
			board.put(frame, score);
		}
	}

	public int score() {
		return board.keySet().stream().map(key -> board.get(key)).mapToInt(score -> score.getFirst() + score.getSecond() + score.getBonus()).sum();
	}
}
