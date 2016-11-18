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
		
	public PUD() {
		prisonIDs = new ArrayList<PrisonID>();
	}
	
	public void update(double playerX, double playerY){
		
		for(int i = 0; i < prisonIDs.size(); i++){
			
			prisonIDs.get(i).update(playerX, playerY);
		}
	}
	
	public void draw(Graphics2D g, double xOffset, double yOffset) {
		
		for(int i = 0; i < prisonIDs.size(); i++){
			
			prisonIDs.get(i).draw(g, xOffset, yOffset);
		}
	}
	
	public void setPrisonIDs(ArrayList<PrisonID> ids){
		prisonIDs = ids;
	}
	
	public void addPrisonID(PrisonID id){
		prisonIDs.add(id);
	}
}
