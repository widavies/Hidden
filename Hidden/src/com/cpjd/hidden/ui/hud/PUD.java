package com.cpjd.hidden.ui.hud;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.hidden.prisons.PrisonID;

/**
 * Prisons-Up-Display ('PUD')
 * 
 * Displays a prison identification tag on each prison the player is nearby
 *
 */
public class PUD {

	private ArrayList<PrisonID> prisonIDs; // The prison IDs that are on-deck to be drawn, a.k.a onscreen right now
	
	private PrisonID id;
	
	public PUD() {
		prisonIDs = new ArrayList<PrisonID>();
		
		id = new PrisonID();
		id.x = 9200;
		id.y = 9400;
		id.name = "Test Name";
		id.hostage = "Daniel Peterson";
		id.reward = 200;
		id.tier = "5";
		id.timeLimit = 505000;
		
		prisonIDs.add(id);
	}
	
	public void update(){
		for(int i = 0; i < prisonIDs.size(); i++){
			
			prisonIDs.get(i).update();
		}
	}
	
	public void draw(Graphics2D g, double xOffset, double yOffset) {
		
		for(int i = 0; i < prisonIDs.size(); i++){
			
			prisonIDs.get(i).draw(g, xOffset, yOffset);
		}
	}
	
}
