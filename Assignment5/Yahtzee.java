/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		
		/* Initialize scores array */
		scores = new int[N_CATEGORIES][nPlayers];
		
		playGame();
		
		/* TODO: Congratulate the winner and end game */
		
		display.printMessage("Congratulations!");
	}

	private void playGame() {
	
		for (int i = 0; i < N_ROUNDS; i++) {
			/* For loop for each player */
			for (player = 1; player <= nPlayers; player++) {
				playRound();
			}
		}
		
		/* Calculate and Update the Upper Score, Upper Bonus, Lower Score, and Total */
		for (player = 1; player <= nPlayers; player++) {
			finalizeScorecard();
		}
	}
	
	
	
	private void playRound() {
					
		/* First Roll */
		display.printMessage(playerNames[player - 1] + "\'s turn! Click \"Roll Dice\" button to roll the dice");
		display.waitForPlayerToClickRoll(player);
		initDice(); //returns true for all dice
		rollDice(); //rolls all dice which are set to true
		
		/* Second and Third Rolls */
		for (int j = 0; j < 2; j++) {
			display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\"");
			display.waitForPlayerToSelectDice(); 
			checkedDice();  //returns true for selected dice
			rollDice(); //rolls all dice which are set to true
		}
		
		/* Category Selection */
		display.printMessage("Select a category for this roll");
		
		/* Check for errors and update the scorecard */
		boolean error = true;
		while (error) {
			int category = display.waitForPlayerToSelectCategory();
			
			/* If score has been updated from default value = 0, it has already been selected */
			if (scores[category - 1][player - 1] == 0) {
			
				/* Utilizing magicstub to check if category is valid */
				if (checkCategory(dice, category)) {
					int score = calcScore(category);
					scores[category - 1][player - 1] = score;
					display.updateScorecard(category, player, score);
					error = false;
				} else {
					display.printMessage("That is not a valid category. Please select another category.");
				}
				
			} else {
				display.printMessage("That category has already been selected. Please choose another category.");
			}
		}
			
		
		
	}
	
	private void initDice() {
		for (int i = 0; i < N_DICE; i++) {
			dieSelected[i] = true;
		}
	}
	
	private void checkedDice() {
		for (int i = 0; i < N_DICE; i++) {
			dieSelected[i] = display.isDieSelected(i);
		}
	}
	
	/* Rolls all dice which are set to true using RandomGenerator */
	private void rollDice() {
		for (int i = 0; i < N_DICE; i++) {
			if(dieSelected[i]) dice[i] = rgen.nextInt(1,N_DICE_SIDES);
		}
		display.displayDice(dice);
	}
	
	private boolean checkCategory(int[] dice, int cat) {
		
		switch(cat) {
		default: return false;
		case ONES: return true;
		case TWOS: return true;
		case THREES: return true;
		case FOURS: return true;
		case FIVES: return true;
		case SIXES: return true;
		case THREE_OF_A_KIND: return isThreeOfAKind();
		case FOUR_OF_A_KIND: return isFourOfAKind();
		case FULL_HOUSE: return isFullHouse();
		case SMALL_STRAIGHT: return isSmallStraight();
		case LARGE_STRAIGHT: return isLargeStraight();
		case YAHTZEE: return isYahtzee();
		
		}
	}
	
	private boolean isThreeOfAKind() {
		int[] counter = new int[N_DICE_SIDES];
		for (int i = 0; i < N_DICE; i++) {
			counter[dice[i]-1]++;
			if (counter[dice[i]-1] == 3) return true;
		}
		return false;
	}
	
	private boolean isFourOfAKind() {
		int[] counter = new int[N_DICE_SIDES];
		for (int i = 0; i < N_DICE; i++) {
			counter[dice[i]-1]++;
			if (counter[dice[i]-1] == 4) return true;
		}
		return false;
	}
	
	private boolean isFullHouse() {
		int[] counter = new int[N_DICE_SIDES];
		for (int i = 0; i < N_DICE; i++) {
			counter[dice[i]-1]++;
		}
		//loop through all dice elements
		for (int j = 0; j < N_DICE_SIDES; j++) {
			if (counter[j] == 3) {
				for (int k = 0; k < N_DICE_SIDES; k++) {
					if (counter[k] == 2) return true;
				}
			}
		}
		return false;
	}
	
	//Straights checkers don't work!!
	private boolean isSmallStraight() {
		int[] counter = new int[N_DICE_SIDES];
		int straightCounter = 0;
		for (int i = 0; i < N_DICE; i++) {
			counter[dice[i]-1]++;
		}
		for (int j = 0; j < N_DICE_SIDES; j++) {
			if (counter[j] == 1) straightCounter++;
		}
		if (straightCounter == 4) return true;
		return false;
	}

	private boolean isLargeStraight() {
		int[] counter = new int[N_DICE_SIDES];
		int straightCounter = 0;
		for (int i = 0; i < N_DICE; i++) {
			counter[dice[i]-1]++;
		}
		for (int j = 0; j < N_DICE_SIDES; j++) {
			if (counter[j] == 1) straightCounter++;
		}
		if (straightCounter == 5) return true;
		return false;
	}
	
	private boolean isYahtzee() {
		int[] counter = new int[N_DICE_SIDES];
		for (int i = 0; i < N_DICE; i++) {
			counter[dice[i]-1]++;
			if (counter[dice[i]-1] == 5) return true;
		}
		return false;
	}
	/* Calculate score earned for particular category selected */
	private int calcScore(int cat) {
		int result = 0;
		switch(cat) {
			case ONES: 				for (int i = 0; i < N_DICE; i++) {
										if (dice[i] == ONES) result += ONES;
									}
									break; 
			case TWOS: 				for (int i = 0; i < N_DICE; i++) {
										if (dice[i] == TWOS) result += TWOS;
									}
									break;	
			case THREES: 			for (int i = 0; i < N_DICE; i++) {
										if (dice[i] == THREES) result += THREES;
									}
									break;
			case FOURS: 			for (int i = 0; i < N_DICE; i++) {
										if (dice[i] == FOURS) result += FOURS;
									}
									break;
			case FIVES: 			for (int i = 0; i < N_DICE; i++) {
										if (dice[i] == FIVES) result += FIVES;
									}
									break;
			case SIXES: 			for (int i = 0; i < N_DICE; i++) {
										if (dice[i] == SIXES) result += SIXES;
									}
									break;	
			case THREE_OF_A_KIND: 	for (int i = 0; i < N_DICE; i++) {
										result += dice[i];
									}
									break;
			case FOUR_OF_A_KIND: 	for (int i = 0; i < N_DICE; i++) {
										result += dice[i];
									}
									break;
			case FULL_HOUSE: 		for (int i = 0; i < N_DICE; i++) {
										result = 25;
									}
									break;

			case SMALL_STRAIGHT: 	for (int i = 0; i < N_DICE; i++) {
										result = 30;
									}
									break;

			case LARGE_STRAIGHT: 	for (int i = 0; i < N_DICE; i++) {
										result = 40;
									}
									break;
			case YAHTZEE: 			for (int i = 0; i < N_DICE; i++) {
										result = 50;
									}
									break;
			case CHANCE: 			for (int i = 0; i < N_DICE; i++) {
										result += dice[i];
									}
									break;
			default: 				break;
		}
		
		return result; 
		
	}
	
	private void finalizeScorecard() {
		int upperScore = 0, lowerScore = 0, total = 0;
		for (int i = ONES; i <= SIXES; i++) {
			upperScore += scores[i-1][player - 1];
		}
		for (int i = THREE_OF_A_KIND; i <= CHANCE; i++) {
			lowerScore += scores[i-1][player - 1];
		}
		
		total = upperScore + lowerScore;
		
		if (upperScore > 35) {
			scores[UPPER_BONUS - 1][player - 1] = UPPER_BONUS_SCORE;
			total += UPPER_BONUS_SCORE;
		}
		scores[UPPER_SCORE - 1][player - 1] = upperScore;
		scores[LOWER_SCORE - 1][player - 1] = lowerScore;
		scores[TOTAL - 1][player - 1] = total;
		
		display.updateScorecard(UPPER_SCORE, player, upperScore);
		display.updateScorecard(LOWER_SCORE, player, lowerScore);
		display.updateScorecard(TOTAL, player, total);
		
	}
	
/* Constants */
	private static final int N_ROUNDS = 13;
	private static final int N_DICE_SIDES = 6;
	private static final int UPPER_BONUS_SCORE = 35;
	
	
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] categories = new int[12];
	private boolean[] dieSelected = new boolean[N_DICE]; //array that stores boolean for whether die should be rolled
	private int[] dice = new int[N_DICE]; //numbers on each die
	private int[][] scores; //store the scores in an array for each player 
	private int player;
	
}
