package com.gamehub.tdd;

import java.util.HashMap;
import java.util.Map;

public class TddBowling {
	private Map<Integer, Score> scoreBoard = new HashMap<>();
	private int currFrame = 1;
	private int currUnit = 1;

	private void increaseFrame() {
		currFrame++;
	}

	private void increaseUnit() {
		currUnit++;
	}

	private void refreshUnit() {
		currUnit = 1;
	}

	public boolean roll(int val) {
		//todo frame 프레임이 넘어가는건지 아닌지를 판단
		if (currUnit > 1) {
			refreshUnit();
			increaseFrame();
		}

		//1프레임인 경우 점수를 들어온대로 저장
		if (currFrame == 1) {
			if (currUnit == 1) {
				Score score = Score.builder().first(val).build();
				scoreBoard.put(currFrame, score);
			} else {
				Score score = scoreBoard.get(currFrame);
				score.setSecond(val);
				scoreBoard.put(currFrame, score);
			}
			increaseUnit();
			return false;
		}

		//2프레임부터
		if (currFrame > 1) {
			if (currUnit == 1) { //첫번째 점수인 경우
				//이전 프레임에 보너스 점수를 첫번째 점수로 넣어줌 (이전 프레임이 스트라이크인 or 스페어인 경우)
				int prevFrame = currFrame - 1;
				Score prevScore = scoreBoard.get(prevFrame);
				if (prevScore.isStrike() || prevScore.isSpare()) {
					//이전 프레임 첫번째가 스트라이크인 경우 or 이전 프레임이 스페어인 경우 첫번째 보너스 점수 부여
					prevScore.setBonus(val);
					scoreBoard.put(prevFrame, prevScore);
					if (currFrame > 2) { //3프레임부터는 이전 프레임이 스트라이크인지 확인
						if (prevScore.isStrike()) {
							int morePrevFrame = prevFrame - 1;
							Score morePrevScore = scoreBoard.get(morePrevFrame);
							if (morePrevScore.isStrike()) {
								//이전전 프레임이 스트라이크인 경우 첫번째 보너스 점수 합산
								int morePrevBonus = morePrevScore.getBonus();
								morePrevScore.setBonus(morePrevBonus + val);
								scoreBoard.put(morePrevFrame, morePrevScore);
							}
						}
					}
				}
				//프레임에 첫번째 점수를 넣어줌
				scoreBoard.put(currFrame, Score.builder().first(val).build());
				increaseUnit();
			} else {
				//해당 프레임의 첫번째 점수는 이미 들어가 있음
				Score score = scoreBoard.get(currFrame);
				if (currUnit == 2) {
					//이전 프레임에 보너스 점수를 두번째 점수를 합산해서 넣어줌 (이전 프레임이 스트라이크인 경우)
					int prevFrame = currFrame - 1;
					Score prevScore = scoreBoard.get(prevFrame);
					if (prevScore.isStrike()) {
						//이전 프레임 첫번째가 스트라이크인 경우 두번째 보너스 점수 부여
						int prevBonus = prevScore.getBonus();
						prevScore.setBonus(prevBonus + val);
						scoreBoard.put(prevFrame, prevScore);
					}
					score.setSecond(val);
				} else {
					score.setThird(val);
				}
				scoreBoard.put(currFrame, score);
				increaseUnit();
			}
		}

		if (currFrame > 10) {
			return true;
		}

		return false;
	}

	public int score() {
		return scoreBoard.keySet().stream().map(key -> scoreBoard.get(key)).mapToInt(score -> score.getFirst() + score.getSecond() + score.getThird() + score.getBonus()).sum();
	}

	private void spare() {

	}

	private void strike() {

	}
}
