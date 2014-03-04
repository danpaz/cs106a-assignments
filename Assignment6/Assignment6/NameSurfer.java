/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
	    // You fill this in, along with any helper methods //
		//println("init");
		
		/* Add buttons to bottom of window */
		nameField = new JTextField(10);
		add(new JLabel("Name:"), SOUTH);
		add(nameField, SOUTH);
		add(new JButton("Graph"), SOUTH);
		add(new JButton("Clear"), SOUTH);
		
		/* Add canvas */
		graph = new NameSurferGraph(); 
		add(graph);
		
		addActionListeners();
		
		/* Load the database of names and ranks */
		database = new NameSurferDataBase(NAMES_DATA_FILE);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Clear")) { 
			 graph.clear(); // Clears the canvas
		 } else if (e.getSource() == nameField || e.getActionCommand().equals("Graph")){
			 NameSurferEntry Entry = database.findEntry(nameField.getText());
			 graph.addEntry(Entry);
		 }
	}
	
/* Private instance variables */
	private JTextField nameField;
	private NameSurferDataBase database;
	private NameSurferGraph graph; 

}
