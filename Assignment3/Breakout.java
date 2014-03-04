/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	
/** Paddle Y position */
	private static final int PADDLE_Y_POSITION = HEIGHT - PADDLE_HEIGHT - PADDLE_Y_OFFSET;
	
/** Paddle color */
	private static final Color PADDLE_COLOR = Color.BLACK;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 9;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 12;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Color of the ball */
	private static final Color BALL_COLOR = Color.BLACK;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
/** Brick color array */
	private static final Color[] BRICK_COLOR_ARRAY = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN };

/** Number of turns */
	private static final int NTURNS = 3;

/** Time delay between movement steps */
	private static final int DELAY = 10;
	
/** Initial Y movement of the ball */
	private static final double BALL_INITIAL_Y = 3.0;
	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		while (turnCount > 0) {
			initBreakout();
			
			readyAlert();
			
			playBreakout();
		}
		endGame();
	}

/* Method: initBreakout() */
/** Initializes the board */
	private void initBreakout() {
		initBricks();
		initPaddle();
		initBall();
	}
	
	private void readyAlert() {
		GLabel ready = new GLabel("",WIDTH/2 + 20, HEIGHT/2);
		add(ready);
		ready.setLabel("Ready...3");
		pause(1000);
		ready.setLabel("Ready...2");
		pause(1000);
		ready.setLabel("Ready...1");
		pause(1000);
		remove(ready);
	}
	
	private void initBricks() {
		int xStart =  ( WIDTH / 2 ) - (( NBRICKS_PER_ROW /2 ) * ( BRICK_WIDTH + BRICK_SEP ));
		for ( int i = 0; i < NBRICK_ROWS; i++ ) {
			int yStart = ( i * ( BRICK_HEIGHT + BRICK_SEP )) + BRICK_Y_OFFSET;
			Color brickColor = BRICK_COLOR_ARRAY[i/2];
			drawBricksRow(xStart, yStart, brickColor);
		}
	}

	
	private void drawBricksRow(int xStart, int yStart, Color brickColor) {
		for ( int i = 0; i < NBRICKS_PER_ROW; i++ ) {
			int x = xStart + i * (BRICK_WIDTH + BRICK_SEP); 
			int y = yStart;
			brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setColor(brickColor);
			add(brick);
		}
	}
	
	private void initPaddle() {
		drawPaddle();
		addPaddleListeners();
	}
		
	/** Method: drawPaddle() */
	/* Inserts a paddle */
	
	private void drawPaddle() {
		int x = ( WIDTH / 2 ) - ( PADDLE_WIDTH / 2 );
		
		paddle = new GRect(x, PADDLE_Y_POSITION, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(PADDLE_COLOR);
		add(paddle);
		
	}
	
	/** Method: addPaddleListeners(); */
	/* Add mouse listeners for paddle */
	private void addPaddleListeners() {
		addMouseListeners();
		
	}
	
	public void mousePressed(MouseEvent e) { 
		 // GPoint has X and Y coordinate 
		 last = new GPoint(e.getPoint()); 
		 gobj = getElementAt(last); 
	 } 

//	 Called on mouse drag to reposition the object 
	public void mouseDragged(MouseEvent e) { 
		if (gobj != null) { 
			 double paddlePosition = gobj.getX();
			 
			 if (paddlePosition < 0) {
				dx = -paddlePosition; 
			 } else if (paddlePosition > (WIDTH - PADDLE_WIDTH)) {
				dx = (WIDTH - PADDLE_WIDTH) - paddlePosition; 
			 } else {
				dx = e.getX() - last.getX();
			 }
			 
			 gobj.move(dx, 0); 
			 last = new GPoint(e.getPoint());	
			 
		} 
	} 
	 
	private void initBall() {
		ball = new GOval( (WIDTH/2) - BALL_RADIUS, (HEIGHT/2) - BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(BALL_COLOR);
		add(ball);
	}
	 
	/** Method: playBreakout() */
	/* Set the game in motion, define bounce functions */
	
	private void playBreakout() {
		ballMotion();
	}
	
	private void ballMotion() {
		//Initialize x velocity with random number gen
		vx = rgen.nextDouble(1.0, 3.0); 
		if (rgen.nextBoolean(0.5)) vx = -vx;
		
		while (brickCount>0){
			moveBall();
			checkForCollision();
			pause(DELAY);
		}
		endGame();
		
	}
	
	private void moveBall() {
		ball.move(vx, vy);
	}
	
	private void checkForCollision() {
		
		/* Redefine the edges of the ball */
		double ballPositionLeft = ball.getX();
		double ballPositionRight = ball.getX() + (BALL_RADIUS * 2);
		double ballPositionTop = ball.getY();
		double ballPositionBottom = ball.getY() + (BALL_RADIUS * 2);
		
		/* Check for wall collisions */
		if (ballPositionLeft <= 0 || ballPositionRight >= WIDTH) {
			vx = -vx;
		} else if (ballPositionTop <= 0) {
			vy = -vy;
		} else if (ballPositionBottom >= HEIGHT) {
			turnCount--;
			remove(ball);
			remove(paddle);
			initBall();
			initPaddle();
			playBreakout();
			
			
		}
		
		
		/* Check for object collisions */
		GObject collider = getCollidingObject(ballPositionLeft, ballPositionRight, ballPositionTop, ballPositionBottom); 
		if (collider != null) {
			if (collider != paddle) {
				remove(collider);
				brickCount--;
				vy = -vy;
			} else if (collider == paddle) {
				vy = -vy;
			}
		}
		
	}
	
	private GObject getCollidingObject(double xLeft, double xRight, double yTop, double yBottom) {
		
		GObject collider = getElementAt(xLeft, yTop);
		if (collider == null) {
			collider = getElementAt(xLeft, yBottom);
		}
		if (collider == null) {
			collider = getElementAt(xRight, yTop);
		}
		if (collider == null) {
			collider = getElementAt(xRight, yBottom);
		}
		return collider;
	}
	
	private void endGame() {
		GLabel endDialog = new GLabel("Game Over", WIDTH/2, HEIGHT/2);
		add(endDialog);
		remove(ball);
	}
	
	/* Private instance variables */ 
	private GObject gobj; /* The object being dragged */ 
	private GPoint last; /* The last mouse position */ 
	private double dx; /* The X differential to move the paddle by */
	private double vx, vy = BALL_INITIAL_Y; /* The velocity components of the ball */
	private RandomGenerator rgen = RandomGenerator.getInstance(); /* Random number generator for initial ball velocity */
	private GOval ball; /* The ball object */
	private GRect paddle; /* The paddle object */
	private GRect brick; /* The brick object */
	private int brickCount = NBRICKS_PER_ROW * NBRICK_ROWS; /* Counter for brick objects */
	private int turnCount = NTURNS; /* Counter for number of turns */

}
