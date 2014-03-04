/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import java.awt.*;
import acm.graphics.*;
import acm.program.*;

public class Target extends GraphicsProgram {	
	public void run() {
		
		/* Conversion from inches to pixels */
		double inchToPix = 72;
		
		/* Set radii and center coords */
		double outerRad = 1*inchToPix;
		double middleRad = 0.65*inchToPix;
		double innerRad = 0.3*inchToPix;
		double centerX = getWidth()/2;
		double centerY = getHeight()/2;
		
		/* Draw the Ovals */
		GOval outerOval = new GOval(centerX - outerRad/2, centerY - outerRad/2 , outerRad, outerRad);
		GOval middleOval = new GOval(centerX - middleRad/2, centerY - middleRad/2, middleRad, middleRad);
		GOval innerOval = new GOval(centerX - innerRad/2, centerY - innerRad/2, innerRad, innerRad);
		
		/* Set Colors of outer and inner circle */
		outerOval.setColor(Color.RED);
		innerOval.setColor(Color.RED);
		
		/* Add to canvas */
		add(outerOval);
		add(middleOval);
		add(innerOval);
	}
}
