package com.cpjd.hidden.ui.hud;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.prisons.PrisonID;

/**
 * Prisons-Up-Display ('PUD')
 * 
 * Displays a prison identification tag on each prison the player is nearby
 *
 */
public class PUD {

	private ArrayList<PrisonID> allPrisonIDs; // all the prison IDs in the game
	private ArrayList<PrisonID> prisonIDs; // The prison IDs that are on-deck to be drawn, a.k.a onscreen right now
	
	private static final int updateDistance = 500; //the range a prison must be within to be drawn (in pixels)
	
	public PUD() {
		prisonIDs = new ArrayList<PrisonID>();
		allPrisonIDs = new ArrayList<PrisonID>();
		
		PrisonID id = new PrisonID();
		id.x = 9200;
		id.y = 9400;
		id.name = "Test Name";
		id.hostage = "Daniel Peterson";
		id.reward = 200;
		id.tier = "5";
		id.timeLimit = 505000;
		
		allPrisonIDs.add(id);
	}
	
	public void update(double playerX, double playerY){
		
		if(GameStateManager.ticks % 1 == 0){
			updatePrisonIDs(playerX, playerY);
		}
		
		for(int i = 0; i < prisonIDs.size(); i++){
			
			prisonIDs.get(i).update();
		}
	}
	
	public void draw(Graphics2D g, double xOffset, double yOffset) {
		
		for(int i = 0; i < prisonIDs.size(); i++){
			
			prisonIDs.get(i).draw(g, xOffset, yOffset);
		}
	}
	
	private void updatePrisonIDs(double playerX, double playerY) {
		
		//remove and reset any that are outside of range
		for(int i = 0; i < prisonIDs.size(); i++){
			
			double xDiff = prisonIDs.get(i).x - playerX;
			double yDiff = prisonIDs.get(i).y - playerY;
			
			//pythag. but leaving it squared saves the expensive Math.sqrt
			double distanceSquared = xDiff * xDiff + yDiff * yDiff;
			
			if(distanceSquared > updateDistance * updateDistance){
				
				prisonIDs.get(i).reset();
				prisonIDs.remove(i--);
				
			}
			
		}
		
		//add any within range
		for(int i = 0; i < allPrisonIDs.size(); i++){
			
			if(!prisonIDs.contains(allPrisonIDs.get(i))){
			
				double xDiff = allPrisonIDs.get(i).x - playerX;
				double yDiff = allPrisonIDs.get(i).y - playerY;
				
				//pythag. but leaving it squared saves the expensive Math.sqrt
				double distanceSquared = xDiff * xDiff + yDiff * yDiff;
				
				if(distanceSquared < updateDistance * updateDistance){
					
					prisonIDs.add(allPrisonIDs.get(i));
				}
			}
		}
		
	}
	
	public void setPrisonIDs(ArrayList<PrisonID> ids){
		allPrisonIDs = ids;
	}
	
	public void addPrisonID(PrisonID id){
		allPrisonIDs.add(id);
	}
}
