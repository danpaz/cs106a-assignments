/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

/** Number of guesses per round */
	private static final int N_GUESS = 8;
	
	public void init() { 
		 canvas = new HangmanCanvas();
		 canvas.reset();
		 add(canvas); 
	} 
	
    public void run() {
		/* Create a new HangmanLexicon */
    	lexicon = new HangmanLexicon();  	
    	
    	/* Loop to play repeatedly */
    	int wordCount = lexicon.getWordCount() - 1;
    	word = lexicon.getWord(rgen.nextInt(0, wordCount));
    	println("Welcome to Hangman!");
    	
    	/* Main function */
    	playRound();
    	
    	if (guesses > 0) {
    		gameWin();
    	} else {
    		gameLose();
    	}
    	
	}
    
    private void playRound() {
    	    	
    	wordString = "";
    	for (int i = 0; i < word.length(); i++) {
    		wordString = wordString + "-";
    	}	

		canvas.displayWord(wordString);
    	
    	while (guesses > 0 && !isWordComplete()) {
    		
    		println("The word now looks like this: " + wordString);
    		println("You have " + guesses + " guesses left.");
    		String userInput = readLine("Your guess: ");
    		
    		if (!isError(userInput)) {
			userGuess = userInput.charAt(0);
    		userGuess = Character.toUpperCase(userGuess);
    			if (isInWord(userGuess)) {
    				println("That guess is correct");
    	    		wordString = buildWordString();
    				canvas.displayWord(wordString);
    				
    			} else {
    				println("There are no " + userGuess + "'s in the word.");
    				guesses--;
    				canvas.noteIncorrectGuess(userGuess);
    			}
    			
    		} else {
    			println("That is not a valid input. Try again.");
    		}
    	}
    	
    }
    
    
    private boolean isWordComplete() {
    	int index = wordString.indexOf('-');
    	if (index == -1) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private String buildWordString() {
    	
		int startCount = 0;
    	int index = -1;
    	
		while (startCount == (index + 1)) {
			
    		String wordsubstr = word.substring(startCount, word.length());
    		index = wordsubstr.indexOf(userGuess);
    		if (index != -1) {
    			index = index + startCount;
    			wordString = wordString.substring(0, index)
    						+ userGuess
    						+ wordString.substring(index+1, word.length());
    			startCount = index + 1;
    		}
		
		}
    	
    	return wordString;
    }
    
    private boolean isError(String userInput) {
    	
    	// Check if user input is valid (e.g. single character)
    	if (userInput.length() > 0) {
    		return false;
    	} else {
    		return true;	
    	}
    	
    	
    }
    
    private boolean isInWord(char userGuess) {
    	// Check if the character is in the word
    	for (int i = 0; i < word.length(); i++) {
    		if (userGuess == word.charAt(i)) return true;
    	}    	
    	return false;
    }
    
    private void gameWin() {
    	println("You win!");
    }
    
    private void gameLose() {
    	println("You lose!");
    }

	/* Private instance variables */
    private HangmanLexicon lexicon; /* Instance of the Hangman Lexicon */
    private String word; /* Secret word for a given round */ 
    private RandomGenerator rgen = RandomGenerator.getInstance(); /* Random generator to select word from lexicon */
    private int guesses = N_GUESS; /* Remaining guesses for the round */
    private String wordString = ""; /* Display string for word to guess */
    private char userGuess; /* Char the user guessed */
    private HangmanCanvas canvas; /* Canvas for Hangman graphics */

}	