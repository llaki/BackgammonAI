package board;

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
		if(whitesTurn) {
			count[boardIndex]--;
			count[destIndex]++;
		}
		else {
			count[boardIndex]++;
			count[destIndex]--;
		}
		int killed = ((whitesTurn && state.isPositionBlack(destIndex)) || (!whitesTurn && state.isPositionWhite(destIndex))) ? 1 : 0;
		return new BoardState(count, (whitesTurn ? state.getNumDead(true) : state.getNumDead(true)+killed), 
				(!whitesTurn ? state.getNumDead(false) : state.getNumDead(false)+killed), state.numPutAside(true), state.numPutAside(false));
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
			count[dice-1]++;
			numDeadWhite--;
		}
		else {
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
		System.out.println(getNiceViewOfBoard(getStartingPositionBoard()));
	}

}
