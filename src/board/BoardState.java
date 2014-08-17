package board;

public class BoardState {
	
	private int[] count;
	private int numDeadWhite, numDeadBlack, putOutWhite, putOutBlack;
	
	// count[i] > 0 -> i-th position contains count[i] of white pieces
	// count[i] < 0 -> i-th position contains (-count[i]) of black pieces
	// count[i] = 0 -> i-th position is empty
	public BoardState(int[] count, int numDeadWhite, int numDeadBlack, int putOutWhite, int putOutBlack) {
		this.count = count;
		this.numDeadBlack = numDeadBlack;
		this.numDeadWhite = numDeadWhite;
		this.putOutBlack = putOutBlack;
		this.putOutWhite = putOutWhite;
	}
	
	public int getCountAt(int position) {
		return count[position];
	}
	
	public int getNumDead(boolean white) {
		return white ? numDeadWhite : numDeadBlack;
	}
	
	public int numPutAside(boolean white) {
		return white ? putOutWhite : putOutBlack;
	}
	
	public int getTakenOut(boolean white) {
		int totalCount = 0;
		int sign = (white ? 1 : -1);
		for(int index=0; index<count.length; index++) {
			int countHere = (sign*count[index]>0)  ? sign*count[index] : 0;
			totalCount += countHere;
		}
		if(white) totalCount += numDeadWhite;
		else totalCount += numDeadBlack;
		return BoardUtils.NUM_PIECES-totalCount;
	}
	
	public int getBitmaskOfPositions(boolean white) {
		int mask = 0;
		int sign = (white ? 1 : -1);
		for(int index=0; index<count.length; index++) {
			if(sign*count[index]>0) {
				mask ^= (1 << index);
			}
		}
		return mask;
	}
	
	public boolean isReadyToTakeOut(boolean white) {
		if(white && numDeadWhite>0) return false;
		if(!white && numDeadBlack>0) return false;
		int countInside = countPiecesOfColorInside(white);
		return countInside + (white ? putOutWhite : putOutBlack) == BoardUtils.NUM_PIECES;
	}
	
	public int countPiecesOfColorInside(boolean white) {
		int left = 18, right = 23;
		int sign = (white ? 1 : -1);
		if(!white) {
			left = 0;
			right = 5;
		}
		int countInside = 0;
		for(int index=left; index<=right; index++) {
			if(sign*count[index] > 0) {
				countInside += sign*count[index];
			}
		}
		return countInside;
	}
	
}
