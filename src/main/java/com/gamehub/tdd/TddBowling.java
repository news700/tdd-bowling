package com.gamehub.tdd;

import java.util.HashMap;
import java.util.Map;

public class TddBowling {
	private Map<Integer, Score> scoreBoard = new HashMap<>();
	private int currFrame = 1; //프레임은 볼링에서의 프레임
	private int currUnit = 1; //유닛은 한프레임당 던진 횟수

	/**
	 * 프레임을 하나 올리고 유닛을 1로 초기화함
	 * 10 프레임인 경우 무시
	 */
	private void increaseFrame() {
		if (currFrame < 10) {
			currFrame++;
			currUnit = 1;
		}
	}

	/**
	 * 유닛을 하나 올림
	 */
	private void increaseUnit() {
		currUnit++;
	}

	/**
	 * 현재 프레임과 유닛을 판단함
	 */
	private void setCurrent() {
		if (currFrame < 10) {
			Score score = scoreBoard.get(currFrame);
			if (score != null) {
				if (score.isStrike()) {
					increaseFrame();
				} else {
					if (currUnit > 2) {
						increaseFrame();
					}
				}
			}
		}
	}

	/**
	 * 첫번째 프레임의 점수만 담당함
	 *
	 * @param val
	 */
	private void setFirstFrameScore(int val) {
		Score score;
		if (currUnit == 1) {
			score = Score.builder().first(val).build();
		} else {
			score = scoreBoard.get(currFrame);
			score.setSecond(val);
			scoreBoard.put(currFrame, score);
		}
		scoreBoard.put(currFrame, score);
		increaseUnit();
	}

	/**
	 * 두번째 프레임부터 10프레임까지의 점수만 담당함
	 *
	 * @param val
	 */
	private void setNormalFrameScore(int val) {
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
		}
		increaseUnit();
	}

	public boolean roll(int val) {
		//현재 프레임과, 유닛을 정함
		setCurrent();

		//1프레임인 경우 점수를 들어온대로 저장
		if (currFrame == 1) {
			setFirstFrameScore(val);
			return false;
		}

		//2프레임부터 9프레임 까지
		if (currFrame > 1) {
			setNormalFrameScore(val);
		}

		if (currFrame > 10) {
			//게임종료
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
