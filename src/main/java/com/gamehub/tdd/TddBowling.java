package com.gamehub.tdd;

import java.util.HashMap;
import java.util.Map;

public class TddBowling {
	private Map<Integer, Integer> board = new HashMap<>();

	public void roll(int frame, int val) {
		Integer first = board.get(frame);
		if (first != null) { //각 프레임의 두번째 점수인 경우
			val += first;
		} else { //각 프레임의 첫번째 점수인 경우
			if (frame > 1) {
				int prevFrame = frame - 1;
				Integer prevVal = board.get(prevFrame);
				if (prevVal == 10) { //이전 프레임의 점수가 10점인 경우 보너스 점수를 이전 프레임 점수에 합산
					board.put(prevFrame, prevVal + val);
				}
			}
		}
		board.put(frame, val);
	}

	public int score() {
		return board.keySet().stream().map(key -> board.get(key)).mapToInt(Integer::intValue).sum();
	}
}
