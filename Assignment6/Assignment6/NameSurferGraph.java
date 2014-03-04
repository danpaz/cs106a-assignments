/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		update();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		data = new ArrayList<NameSurferEntry>();
		
		update();

	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		
		data.add(entry);
		update();

	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		addGrid();
		addDataLines();
	}
	
	private void addGrid() {
		for (int i = 0; i < NDECADES; i++) {
			/* Vertical grid */
			double x = i * getWidth() / NDECADES;
			double y = getHeight();
			add(new GLine(x, 0, x, y));
			
			/* Year labels */
			int year = START_DECADE + 10*i;
			add(new GLabel(Integer.toString(year), x + 3, y - 5));
		}
		
		/* Horizontal Lines to define border */
		add(new GLine(0, VERT_PADDING, getWidth(), VERT_PADDING));
		add(new GLine(0, getHeight() - VERT_PADDING, getWidth(), getHeight() - VERT_PADDING));
		
	}
	
	private void addDataLines() {

		/* Loop through each NameSurferEntry to display */
		for (int i = 0; i < data.size(); i++) {
			
			/* Retrieve current entry object*/
			NameSurferEntry obj = data.get(i);
			String name = obj.getName(); //use for labels
			
			int rank = obj.getRank(0);
			String rankLabel = Integer.toString(rank);
			if (rank == 0) {
				rank = 1000;
				rankLabel = "*";
			}
			/* Set coordinates for lines to be drawn */
			double x1 = 0;
			double y1 = VERT_PADDING +( (double)rank/(double)MAX_RANK * ( getHeight()- ( 2*VERT_PADDING ) ) ); //trying to scale to window height
			double x2, y2;
			
			
			
			for (int j = 1; j < NDECADES; j++) {
				/* Set the label now, before overwriting rank */
				GLabel label = new GLabel(name + " " + rankLabel, x1, y1); 
				
				rank = obj.getRank(j);
				
				rankLabel = Integer.toString(rank);
				if (rank == 0) {
					rank = 1000;
					rankLabel = "*";
				}
				
				x2 = j * getWidth() / NDECADES;
				y2 = VERT_PADDING + ( (double)rank/(double)MAX_RANK * ( getHeight()- ( 2*VERT_PADDING ) ) );
				GLine line = new GLine(x1, y1, x2, y2);
				
				line.setColor(LINE_COLORS[i%4]); // 4 colors to loop through
				label.setColor(LINE_COLORS[i%4]);
				add(line);
				add(label);
				x1 = x2;
				y1 = y2;
			}
			/* Don't forget the last label on final data point */
			GLabel label = new GLabel(name + ": " + rankLabel, x1, y1);
			label.setColor(LINE_COLORS[i%4]);
			add(label);
			
		}
		
		
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	/* Private Instance Constants */
	public final static double VERT_PADDING = 20;
	/** Line color array */
	private static final Color[] LINE_COLORS = { Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA };
	
	/* Private Instance Variables */
	private ArrayList<NameSurferEntry> data = new ArrayList<NameSurferEntry>(); //currently displayed data on the graph
	private double yScalingFactor = VERT_PADDING + ( (getHeight() - VERT_PADDING)/MAX_RANK); //Scaling factor puts MAX_RANK at bottom of graph 
	private double xScalingFactor = getWidth() / NDECADES; //Scaling factor for x dimension
	
}