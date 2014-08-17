package board;

import java.util.*;

public class BoardUtils {
	
	public static final int BOARD_SIZE = 24;
	public static final int NUM_PIECES = 15;
	
	public BoardState getStartingPositionBoard() {
		int[] count = new int[BOARD_SIZE];
		count[0] = +2;
		count[23] = -2;
		count[5] = count[12] = -5;
		count[18] = count[11] = +5;
		count[7] = -3;
		count[16] = +3;
		return new BoardState(count, 0, 0, 0, 0);
	}
	

	
}
