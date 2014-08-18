package score;

import board.BoardState;
import board.BoardUtils;

public class EvaluatorUtils {
	
	public static double pipCount(BoardState state, boolean white) {
		int sum = 0;
		for(int i=0; i<24; i++) {
			if(white && state.getCountAt(i)>0 && state.isPositionWhite(i)) {
				sum += state.getCountAt(i) * (BoardUtils.BOARD_SIZE - i);
			}
			else if(!white && state.getCountAt(i)>0 && state.isPositionBlack(i)) {
				sum += state.getCountAt(i) * (i+1);
			}
		}
		if((white && state.getNumDead(true)>0) || (!white && state.getNumDead(false)>0)) {
			int freeSpots = countFreeSpotsOfColor(state, !white);
			double expectedIdle = getExpectedDelay(freeSpots);
			sum += ((expectedIdle - 1) * 7.0 + 24) * (white ? state.getNumDead(true) : state.getNumDead(false));
		}
		return sum;
	}
	
	private static double getExpectedDelay(int freeSpots) {
		if(freeSpots == 0) return 10; // Can be changed to another constant. 
		double probabilityMiss = Math.pow(((6.0 - freeSpots) / 6.0), 2);
		double probabilityToMakeAlive = 1.0 - probabilityMiss;
		return 1.0 / probabilityToMakeAlive;
		
	}
	
	private static int countFreeSpotsOfColor(BoardState state, boolean white) {
		int countFree = 0;
		if(white) {
			for(int i=18; i<=23; i++) {
				if(state.isPositionWhite(i) && state.getCountAt(i)>1) continue;
				countFree++;
			}
		}
		else {
			for(int i=0; i<=5; i++) {
				if(state.isPositionBlack(i) && state.getCountAt(i)>1) continue;
				countFree++;
			}
		}
		return countFree;
	}
	
	public static double scoreForUnsafePieces(BoardState state, boolean white) {
		int unsafeCount = 0;
		for(int i=0; i<BoardUtils.BOARD_SIZE; i++) {
			if(state.getCountAt(i)==0) continue;
			boolean match = (white == state.isPositionWhite(i));
			if(!match) continue;
			if(state.getCountAt(i)==1) {
				if(white && i<=5) unsafeCount++;
				else if(!white && i>=18) unsafeCount++;
				else if(canBeKilledNextMove(state, i, white)) unsafeCount += 5; // constant 5 can be changed to optimize the performance
			}
		}
		return unsafeCount;
	}
	
	private static boolean isSafe(BoardState state, int position, boolean white) {
		if(white) {
			return state.isPositionWhite(position) && state.getCountAt(position)>1;
		}
		else {
			return state.isPositionBlack(position) && state.getCountAt(position)>1;
		}
	}
	
	public static double scoreForGoodCoverage(BoardState state, boolean white) {
		int score = 0;
		if(white) {
			for(int i=0; i<17; i++) {
				if(isSafe(state, i, white)) {
					score++;
				}
			}
			if(isSafe(state, 17, white)) score += 3.5;
			if(isSafe(state, 18, white)) score += 5;
			if(isSafe(state, 19, white)) score += 4;
			if(isSafe(state, 20, white)) score += 3;
			if(isSafe(state, 21, white)) score += 2;
			if(isSafe(state, 22, white)) score += 1.5;
			if(isSafe(state, 23, white)) score += 1;
		}
		else {
			for(int i=23; i>6; i--) {
				if(isSafe(state, i, white)) {
					score++;
				}
			}
			if(isSafe(state, 6, white)) score += 3.5;
			if(isSafe(state, 5, white)) score += 5;
			if(isSafe(state, 4, white)) score += 4;
			if(isSafe(state, 3, white)) score += 3;
			if(isSafe(state, 2, white)) score += 2;
			if(isSafe(state, 1, white)) score += 1.5;
			if(isSafe(state, 0, white)) score += 1;
		}
		return score;
	}
	
	private static boolean canBeKilledNextMove(BoardState state, int position, boolean white) {
		if(white) {
			for(int i=position+1; i<Math.min(position+7, BoardUtils.BOARD_SIZE); i++) {
				if(state.isPositionBlack(i)) return true;
			}
		}
		else {
			for(int i=position-1; i>=Math.max(position-7, 0); i++) {
				if(state.isPositionWhite(i)) return true;
			}
		}
		return false;
	}
	
}
