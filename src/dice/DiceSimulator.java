package dice;

import java.util.Random;

public class DiceSimulator {
	
	private static final int MAX_DICE_RESULTS = 4000000;
	private static int[] randomDiceResults = new int[MAX_DICE_RESULTS];
	private static DiceSimulator diceSimulator = null;
	private static int currentPointer = 0;
	
	public static DiceSimulator getInstance() {
		if(diceSimulator == null) {
			diceSimulator = new DiceSimulator();
		}
		return diceSimulator;
	}
	
	private DiceSimulator() {
		Random randomGenerator = new Random();
		int pointer = 0;
		while(true) {
			if(pointer == randomDiceResults.length) break;
			int number = Math.abs(randomGenerator.nextInt());
			while(number > 0) {
				int randDice = number % 7;
				number /= 7;
				if(randDice == 0) {
					continue;
				}
				randomDiceResults[pointer++] = randDice;
				if(pointer == randomDiceResults.length) break;
			}
		}
		System.out.println("Done loading dice results");
	}
	
	public int getRandomDice() {
		int diceResult = randomDiceResults[currentPointer];
		currentPointer = (currentPointer + 1) % MAX_DICE_RESULTS;
		return diceResult;
	}
	
}
