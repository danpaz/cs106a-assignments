/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		/* Instantiate the canvas */
		canvas = new FacePamphletCanvas();
		add(canvas);
		
		canvas.showMessage("Welcome to Face Pamphlet! Start by adding a name");
		
		/* North input fields */
		add( new JLabel( "Name " ), NORTH );
		add( nameField, NORTH );
		add( addButton, NORTH );
		add( deleteButton, NORTH );
		add( lookupButton, NORTH );
		
		/* West input fields */
		add( changeStatusField, WEST );
		add( changeStatusButton, WEST );		
		add( new JLabel( EMPTY_LABEL_TEXT ), WEST );
		add( changePictureField, WEST );
		add( changePictureButton, WEST );			
		add( new JLabel( EMPTY_LABEL_TEXT ), WEST );
		add( addFriendField, WEST );			 
		add( addFriendButton, WEST );
		
		/* Allow user to click enter */
		changeStatusField.setActionCommand("Change Status");
		changePictureField.setActionCommand("Change Picture");
		addFriendField.setActionCommand("Add Friend");
		
		/* Add listeners */
		addActionListeners();
		
		/* Instantiate the database */
		db = new FacePamphletDatabase();
		
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	
    	/* Get values from all of the input fields */
		name = nameField.getText(); //get Name from the JTextField
		status = changeStatusField.getText(); //get Name from the JTextField
		imageFilename = changePictureField.getText(); //get Name from the JTextField
		friend = addFriendField.getText(); //get Name from the JTextField
    	
    	
		/* Actions for adding, deleting, and looking up */
    	if (e.getActionCommand().equals("Add")) {
    		 if (profileExists()) {
    			 profile = db.getProfile(name);
    			 canvas.showMessage("A profile for " + name + " already exists");
    		 } else {
    			profile = new FacePamphletProfile(name);
    			db.addProfile(profile);
    			canvas.displayProfile(profile);
    			canvas.showMessage("Added new profile for " + name);
    		 }
		} else if (e.getActionCommand().equals("Delete")) { 
			 profile = null;
			 if (profileExists()) {
	   			db.deleteProfile(name);
	   			canvas.showMessage(name + "'s profile deleted");
   		 	 } else {
   		 		canvas.showMessage("That profile does not exist"); 
   		 	 }
		} else if (e.getActionCommand().equals("Lookup")) { 
			if (profileExists()) {
				profile = db.getProfile(name);
				canvas.displayProfile(profile);
				canvas.showMessage("Displaying " + name);
   		 	} else {
   		 		profile = null;
   		 		canvas.showMessage("That profile does not exist"); 
   		 	}  
		} 
    	
    	/* Actions for editing the current profile */
    	if (e.getActionCommand().equals("Change Status")) { 
			if (profile != null) {
				profile.setStatus(status);
				canvas.displayProfile(profile);
				canvas.showMessage("Displaying " + name);
			} else {
				canvas.showMessage("First select a user profile");
			}
		} else if (e.getActionCommand().equals("Change Picture")) { 
			if (profile != null) {
				GImage image = null; 
				try { 
					image = new GImage(imageFilename); 
				} catch (ErrorException ex) { 
					canvas.showMessage("Sorry, that image could not be found"); 
				} 
				
				if (image != null) {
					profile.setImage(image);
					canvas.displayProfile(profile);
					canvas.showMessage("Displaying " + name);
				}
			} else {
				canvas.showMessage("Choose a profile to set the image");
			}
		} else if (e.getActionCommand().equals("Add Friend")) { 
			if (profile != null) {
				if (db.getProfile(friend) != null) {
					if (profile.addFriend(friend)) {
						db.getProfile(friend).addFriend(profile.getName());
						canvas.displayProfile(profile);
						canvas.showMessage("Displaying " + name);
					} else {
						canvas.showMessage("You already have that friend, silly");
					}
				} else {
					canvas.showMessage("Select a profile to add a friend to");
				}
			} else {
				canvas.showMessage("That person does not exist");
			}
		}
    	
    	
    	
	}
    
    private boolean profileExists() {
    	if (db.getProfile(name) != null) return true;
    	return false;
    }
    
    /* Private Instance Variables */
    private JTextField nameField = new JTextField(TEXT_FIELD_SIZE); // Input field for name
    private JButton addButton = new JButton("Add"); // Button for adding a new instance to the network
    private JButton deleteButton = new JButton("Delete"); // Button for deleting someone from the network
    private JButton lookupButton = new JButton("Lookup"); // Button for looking up someone in the network
    private JTextField changeStatusField = new JTextField(TEXT_FIELD_SIZE); // Input field for setting status
    private JTextField changePictureField = new JTextField(TEXT_FIELD_SIZE); // Input field for changing picture
    private JTextField addFriendField = new JTextField(TEXT_FIELD_SIZE); // Input field for adding a friend
    private JButton changeStatusButton = new JButton("Change Status"); // Button for looking up someone in the network
    private JButton changePictureButton = new JButton("Change Picture"); // Button for looking up someone in the network
    private JButton addFriendButton = new JButton("Add Friend"); // Button for looking up someone in the network
    private FacePamphletDatabase db; // Database of Profiles
    private FacePamphletProfile profile = null; // Object for current profile with initial value of null
    private String name; // name text
    private String status; // status text
    private String imageFilename; // picture text
    private String friend; // add friend name text
    private FacePamphletCanvas canvas; //canvas object for the profile display

}
