package com.cpjd.hidden.ui.hud;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import com.cpjd.hidden.prisons.PrisonID;

/**
 * Prisons-Up-Display ('PUD')
 * 
 * Displays a prison identification tag on each prison the player is nearby
 * @author Will Davies
 *
 */
public class PUD {

	private ArrayList<ArrayList<Point>> prisonLocations;
	private ArrayList<PrisonID> prisonIDs; // The prison IDs that are on-deck to be drawn, a.k.a onscreen right now
	
	public PUD(ArrayList<ArrayList<Point>> prisonLocations) {
		this.prisonLocations = prisonLocations;
	}
	
	/**
	 * 
	 * @param playerx
	 * @param playery
	 */
	public void update(double playerx, double playery) {
		
	}
	
	public void draw(Graphics2D g) {
		
	}
	
}
