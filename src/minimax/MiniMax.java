package minimax;

import score.Evaluator;
import board.BoardState;
import board.BoardUtils;

import java.util.ArrayList;
import java.util.Scanner;

import move.MoveUtils;

public class MiniMax {
	
	public static final int MAX_DEPTH = 1;
	
	public static BoardState makeOptimalMove(BoardState state, int dice1, int dice2, boolean whitesTurn) {
		ArrayList<BoardState> states = MoveUtils.getAllPossibleMoveResults(state, dice1, dice2, whitesTurn);
		double bestScore = -1e9;
		BoardState optimalState = null;
		for(BoardState bs : states) {
			double scoreForState = getMinimaxScore(bs, !whitesTurn, MAX_DEPTH, whitesTurn);
			if(bestScore < scoreForState) {
				bestScore = scoreForState;
				optimalState = bs;
			}
		}
		return optimalState;
	}
	
	private static double getMinimaxScore(BoardState state, boolean whitesTurn, int depth, boolean initialTurnWhite) {
		if(depth==0) {
			return Evaluator.getScoreOfStateFor(state, initialTurnWhite);
		}
		double score = 0.0;
		for(int d1=1; d1<=6; d1++) {
			for(int d2=d1; d2<=6; d2++) {
				double diceProbability = (d1==d2) ? 1.0/36.0 : 1.0/18.0; 
				ArrayList<BoardState> states = MoveUtils.getAllPossibleMoveResults(state, d1, d2, whitesTurn);
				boolean min = (initialTurnWhite != whitesTurn);
				double maxScore = -1e9;
				if(min) {
					maxScore = 1e9;
				}
				for(BoardState bs : states) {
					if(!min) {
						maxScore = Math.max(maxScore, getMinimaxScore(bs, !whitesTurn, depth-1, initialTurnWhite));
					}
					else {
						maxScore = Math.min(maxScore, getMinimaxScore(bs, !whitesTurn, depth-1, initialTurnWhite));
					}
				}
				score += diceProbability * maxScore;
			}
		}
		return score;
	}
	
	public static void main(String[] args) {
		//BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		BoardState state = BoardUtils.getStartingPositionBoard();
		boolean whitesTurn = true;
		while(true) { 
			int d1 = sc.nextInt(), d2 = sc.nextInt();
			if(d1<1 || d2<1 || d1>6 || d2>6) {
				System.out.println("try again with correct dices");
				continue;
			}
			state = makeOptimalMove(state, d1, d2, whitesTurn);
			whitesTurn = !whitesTurn;
			System.out.println(state);
		}
		
	}
	
}
