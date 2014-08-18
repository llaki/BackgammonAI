package board;

import java.util.ArrayList;

import move.MoveUtils;
import score.Evaluator;

public class BoardUtils {

	public static final int BOARD_SIZE = 24;
	public static final int NUM_PIECES = 15;

	public static BoardState getStartingPositionBoard() {
		int[] count = new int[BOARD_SIZE];
		count[0] = +2;
		count[23] = -2;
		count[5] = count[12] = -5;
		count[18] = count[11] = +5;
		count[7] = -3;
		count[16] = +3;
		return new BoardState(count, 0, 0, 0, 0);
	}

	private static void appendStuff(StringBuilder sb, BoardState state,
			int from, int to) {
		for (int index = from; index <= to; index++) {
			int count = state.getCountAt(index);
			String pieces = (count == 0 ? "--"
					: (state.isPositionWhite(index) ? count + "W" : count + "B"));
			sb.append(pieces + " ");
		}
	}

	private static void appendStuffReverse(StringBuilder sb, BoardState state,
			int from, int to) {
		for (int index = to; index >= from; index--) {
			int count = state.getCountAt(index);
			String pieces = (count == 0 ? "--"
					: (state.isPositionWhite(index) ? count + "W" : count + "B"));
			sb.append(pieces + " ");
		}
	}
	
	public static BoardState getBoardAfterMovingSinglePiece(BoardState state, int boardIndex, int moveLength) {
		boolean whitesTurn = state.isPositionWhite(boardIndex);
		int destIndex = (whitesTurn ? boardIndex+moveLength : boardIndex-moveLength);
		int[] count = state.getCountsArray();
		int numWhiteDead = state.getNumDead(true), numBlackDead = state.getNumDead(false);
		if(whitesTurn) {
			if(count[destIndex] < 0){
				numBlackDead++;
				count[destIndex] = 0;
			}
			count[boardIndex]--;
			count[destIndex]++;
		}
		else {
			if(count[destIndex] > 0){
				numWhiteDead++;
				count[destIndex] = 0;
			}
			count[boardIndex]++;
			count[destIndex]--;
		}
		return new BoardState(count, numWhiteDead, numBlackDead, state.numPutAside(true), state.numPutAside(false));
	}
	
	public static BoardState getBoardStateAfterPuttingOut(BoardState state, int boardIndex, int moveLength) {
		boolean whitesTurn = state.isPositionWhite(boardIndex);
		int[] count = state.getCountsArray();
		int numDeadWhite = state.getNumDead(true), numDeadBlack = state.getNumDead(false);
		int numPutWhite = state.numPutAside(true), numPutBlack = state.numPutAside(false);
		if(whitesTurn) {
			count[boardIndex]--;
			return new BoardState(count, numDeadWhite, numDeadBlack, numPutWhite+1, numPutBlack);
		}
		else {
			count[boardIndex]++;
			return new BoardState(count, numDeadWhite, numDeadBlack, numPutWhite, numPutBlack+1);
		}
	}
	
	public static BoardState getBoardForMakingPieceAlive(BoardState state, int dice, boolean whitesTurn) {
		int[] count = state.getCountsArray();
		int numDeadWhite = state.getNumDead(true), numDeadBlack = state.getNumDead(false);
		int numPutWhite = state.numPutAside(true), numPutBlack = state.numPutAside(false);
		if(whitesTurn) {
			if(count[dice-1] < 0) {
				count[dice-1] = 0;
				numDeadBlack++;
			}
			count[dice-1]++;
			numDeadWhite--;
		}
		else {
			if(count[BoardUtils.BOARD_SIZE-dice] > 0) {
				count[BoardUtils.BOARD_SIZE-dice] = 0;
				numDeadWhite++;
			}
			count[BoardUtils.BOARD_SIZE-dice]--;
			numDeadBlack--;
		}
		return new BoardState(count, numDeadWhite, numDeadBlack, numPutWhite, numPutBlack);
	}

	public static String getNiceViewOfBoard(BoardState state) {
		StringBuilder sb = new StringBuilder();
		appendStuffReverse(sb, state, 6, 11);
		sb.append(" | ");
		appendStuffReverse(sb, state, 0, 5);
		sb.append("\n");
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 6; i++)
				sb.append("|| ");
			sb.append(" | ");
			for (int i = 0; i < 6; i++)
				sb.append("|| ");
			sb.append("\n");
		}
		appendStuff(sb, state, 12, 17);
		sb.append(" | ");
		appendStuff(sb, state, 18, 23);
		sb.append("\n");
		return sb.toString();
	}

	public static void main(String[] args) {
		ArrayList<BoardState> states = MoveUtils.getAllPossibleMoveResults(BoardUtils.getStartingPositionBoard(), 3, 1, true);
		for(BoardState bs : states) {
			System.out.println(bs + "\n score = " + Evaluator.getScoreOfStateFor(bs, true));
		}
	}

}
