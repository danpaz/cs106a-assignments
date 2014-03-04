/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	private static final int SENTINEL = 0;
		
	public void run() {
		println("This program finds the largest and smallest numbers");
		int min = 0;
		int max = 0;
				
		while (true) {
			
			int userInput = readInt("? ");
			if (userInput == SENTINEL) {
				break;
			}
			if (userInput > max) {
				max = userInput;
			} else if (userInput < min) {
				min = userInput;
			}
			
			
		}
		println("smallest: " + min);
		println("largest: " + max);
		
		
	}
}

