package score;

import board.BoardState;


public class Evaluator {
	
	public static double getScoreOfState(BoardState state) {
		double pipDifference = EvaluatorUtils.pipCount(state, true) - EvaluatorUtils.pipCount(state, false);
		double coverageDifference = EvaluatorUtils.scoreForGoodCoverage(state, true) - EvaluatorUtils.scoreForGoodCoverage(state, false);
		double unsafeDifference = EvaluatorUtils.scoreForUnsafePieces(state, false) - EvaluatorUtils.scoreForUnsafePieces(state, true);
		return 100*pipDifference + 50*coverageDifference + 50*unsafeDifference; // STUB
	}
	
}
