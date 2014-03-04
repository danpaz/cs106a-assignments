/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		double x = ( getWidth() / 2 ) - ( message.getWidth() / 2) ;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		message.setLocation(x, y);
		add(message);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		
		/* Set current profile to ivar */
		currentProfile = profile;
		
		/* Clear the canvas */
		removeAll();
		
		/* Display all the elements of the profile */
		displayName();
		displayImage();
		displayStatus();
		displayFriends();
		
	}
	
	private void displayName() {
		GLabel name = new GLabel(currentProfile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setLocation(LEFT_MARGIN, TOP_MARGIN);
		add(name);
	}
	
	private void displayImage() {
		GImage image = currentProfile.getImage();
		if (image != null) {
			/* Scale the image to fit */
			double sx = IMAGE_WIDTH/image.getWidth();
			double sy = IMAGE_HEIGHT/image.getHeight();		
			image.scale(sx,sy);
			image.setLocation(LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN);
			add(image);
		} else {
			/* If no image exists, display a rectangle with a label */
			GRect noImageRect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			noImageRect.setLocation(LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN);
			add(noImageRect);
			GLabel noImageMsg = new GLabel("No Image");
			noImageMsg.setFont(PROFILE_IMAGE_FONT);
			noImageMsg.setLocation(LEFT_MARGIN + ( IMAGE_WIDTH / 2 ) - ( noImageMsg.getWidth() / 2 ) , IMAGE_MARGIN + TOP_MARGIN + ( IMAGE_HEIGHT / 2 ) );
			add(noImageMsg);
		}
	}
	
	private void displayStatus() {
		String status = currentProfile.getStatus();
		String name = currentProfile.getName();
		GLabel statusLabel;
		if (status == "") {
			statusLabel = new GLabel("No current status");
		} else {
			statusLabel = new GLabel(name + " is " + status);
		}
		statusLabel.setFont(PROFILE_STATUS_FONT);
		statusLabel.setLocation(LEFT_MARGIN, STATUS_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + TOP_MARGIN);
		add(statusLabel);
	}
	
	private void displayFriends() {
		
		double x = getWidth() / 2;
		double y = IMAGE_MARGIN + TOP_MARGIN;
		GLabel friendsHeader = new GLabel("Friends: ");
		friendsHeader.setLocation(x, y);
		friendsHeader.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsHeader);
		
		double headerMargin = friendsHeader.getHeight();
		y += headerMargin;
		Iterator it = currentProfile.getFriends();
		while (it.hasNext()) {
			String nextFriend = "" + it.next();
			GLabel friendLabel = new GLabel(nextFriend);
			friendLabel.setLocation(x, y);
			friendLabel.setFont(PROFILE_FRIEND_FONT);
			y += friendLabel.getHeight();
			add(friendLabel);
		}
		
	}

	/* Private Instance Variables */
	private FacePamphletProfile currentProfile; 
	
}
