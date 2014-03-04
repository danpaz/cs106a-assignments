/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	
	private static final double BOX_WIDTH = 140;
	private static final double BOX_HEIGHT = 40;
	private static final double BOX_SPACING = 25;

	public void run() {
		

		double centerX = getWidth()/2 - BOX_WIDTH/2; 
		double centerY = getHeight()/2 - BOX_HEIGHT/2;
		drawSubclasses(centerX,centerY);
		drawSuperclass(centerX,centerY);
		
		GLine ConsoleToProgram = new GLine(centerX + BOX_WIDTH/2, centerY, centerX + BOX_WIDTH/2, centerY - 2*BOX_SPACING);
		add(ConsoleToProgram);
	}
	
	private void drawSubclasses(double centerX,double centerY) {
		GRect GraphicsProgramBox = new GRect(centerX - BOX_WIDTH - BOX_SPACING , centerY, BOX_WIDTH, BOX_HEIGHT);
		add(GraphicsProgramBox);
		GRect ConsoleProgramBox = new GRect(centerX, centerY, BOX_WIDTH, BOX_HEIGHT);
		add(ConsoleProgramBox);
		GRect DialogProgramBox = new GRect(centerX + BOX_WIDTH + BOX_SPACING , centerY, BOX_WIDTH, BOX_HEIGHT);
		add(DialogProgramBox);
	}
	private void drawSuperclass(double centerX, double centerY) {
		GRect ProgramBox = new GRect(centerX, centerY - BOX_HEIGHT - 2*BOX_SPACING, BOX_WIDTH, BOX_HEIGHT);
		add(ProgramBox);
	}
}

