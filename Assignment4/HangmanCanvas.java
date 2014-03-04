/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {


/** Resets the display so that only the scaffold appears */
	public void reset() {
		
		// Reset guessed letters string
		guessedLetters.setLabel("");
		
		hangmanGraphic = new GCompound();
		hangmanGraphic.setLocation(WIDTH/2, 0);
		drawScaffold();
		add(hangmanGraphic);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		wordDisplay.setLabel(word);
		add(wordDisplay);
		
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
	
		// Initialize a StringBuilder to concatenate chars 
		StringBuilder sb = new StringBuilder();
		
		// Get existing string 
		String str = guessedLetters.getLabel();
		
		// Add guessed letter to existing string
		str += sb.append(letter);
		
		// Replace string label
		guessedLetters.setLabel(str);		
		add(guessedLetters);
		
		// Add a body part to the canvas 
		addBodyPart();		
	}
		
	private void drawScaffold() {
		double x1 = 0;
		double y1 = HEIGHT;
		double x2 = 0;
		double y2 = y1 - SCAFFOLD_HEIGHT;
		double x3 = BEAM_LENGTH;
		double y3 = y2;
		double x4 = x3;
		double y4 = y3 + ROPE_LENGTH;
		
		GLine scaffoldMast = new GLine(x1, y1, x2, y2);
		GLine scaffoldTop = new GLine(x2, y3, x3, y3);
		GLine scaffoldRope = new GLine(x3, y3, x4, y4);
		hangmanGraphic.add(scaffoldMast);
		hangmanGraphic.add(scaffoldTop);
		hangmanGraphic.add(scaffoldRope);
	}
	
	private void addBodyPart() {
		// Use a switch statement
	
		switch (i) {
		case 0:	GOval head = new GOval(BEAM_LENGTH - (HEAD_RADIUS / 2), HEIGHT - SCAFFOLD_HEIGHT + ROPE_LENGTH, HEAD_RADIUS, HEAD_RADIUS);
				hangmanGraphic.add(head);
				add(hangmanGraphic);
				break;
		case 1: break;
		
		}
		i++;
	}
	
/* Constants for the simple version of the picture (in pixels) */
	
	/* Dimensions of canvas */
	private final int WIDTH = 200;
	private final int HEIGHT = 400;
		
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int WORD_LABEL_X = 10;
	private final int WORD_LABEL_Y = HEIGHT - 10;


	/* private instance variables */
	private GCompound hangmanGraphic; /* Compound graphic for the hangman */
	private GLabel wordDisplay = new GLabel("", 50, 450); /* String for word as currently revealed */
	private GLabel guessedLetters = new GLabel("", 50, 465); /* String of guessed letters */
	private int i = 0; /* Counter variable for body parts */

}
