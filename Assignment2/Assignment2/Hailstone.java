/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		
		int n = readInt("Enter a number: ");
		int i = 0;
		int newN;
		
		while (true) {
			
			if (n==1) {
				break;
			} 
			
			if (n%2==0) {
				newN = 3*n+1;
				println(n + " is odd, so I make 3n+1: " + newN);
				n = newN;
			} else if (n%2!=0) {
				newN = n/2;
				println(n + " is odd, so I take half: " + newN);
				n = newN;
			}
						
			i++;
		}
		println("The process took " + i + " to reach 1");
	}
}

