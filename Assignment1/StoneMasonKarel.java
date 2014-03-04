/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
	
		repairArches();
		
	}
	
	private void repairArches() {
		while (frontIsClear()) {
			createBeeperLine();
			move4East();
		}
		createBeeperLine();
		
	}
	
	private void createBeeperLine() {
		
		faceColumnDirection();
		
		while (frontIsClear()) {	
			if (noBeepersPresent()){
				putBeeper();
			}
			move();
		}
		
		if (noBeepersPresent()){
			putBeeper();
		}
		
		faceForwardDirection();
		
	}
	
	private void faceColumnDirection() {
		if (rightIsBlocked()) {
			turnLeft();
		} else if (leftIsBlocked()) {
			turnRight();
		}	
	}
	
	private void faceForwardDirection() {
		if (facingNorth()) {
			turnRight();
		} else if (facingSouth()) {
			turnLeft();
		}
	}
	private void move4East() {
		for (int i=0;i<4;i++) {
			move();
		}
	}
	
	
}
