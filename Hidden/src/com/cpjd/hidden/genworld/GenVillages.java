package com.cpjd.hidden.genworld;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.cpjd.tools.Log;

public class GenVillages {

	/* Here's how village generation will work:
	 * -There will be about 4 - 6 villages generated per map, at random locations
	 * -Villages will include the following:
	 * Blacksmith station
	 * Electronic crafting station
	 * Work station
	 * several houses
	 * Roads
	 * Chests with some randomly generated resources
	 * Shops - carpentry, armory, gear
	 * Research center
	 * And support the spawning of the following:
	 * -NPCs (for trading) - blacksmiths, carpenters, electronic engineers
	 * -Village creatures - during night time, they will attack so - so stay alert!
	 * 
	 * Villages are essentially a way to research new technologies, buy new technologies, and optain, trade, or buy stuff, and get objectives
	 * Villagers might give you information / jobs to do at prisons - they might give you rewords for recusing their relatives - at the
	 * cost of upsetting the agency.
	 * 
	 * This are the 6 villages names that could be used (adjust as neccessary)
	 * -Bradford, Ellwood, Brares, Foxcroft, Town of Ender, Overwood
	 */
	public static final int WIDTH = 30;
	public static final int HEIGHT = 15;
	
	private byte[][] generation;
	private final byte BORDER = 50;
	private final byte NUM_TO_GENERATE = 5;
	private ArrayList<Point> villageLocations;
	private Random r;
	
	public GenVillages(byte[][] generation) {
		this.generation = generation;
		
		villageLocations = new ArrayList<Point>();
		r = new Random();
		
		generateVillages();
		
	}
	
	private void generateVillages() {
		// Generation the locations
		for(int i = 0; i < NUM_TO_GENERATE; i++) {
			int x = r.nextInt(generation.length - (2 * BORDER)) + BORDER;
			int y = r.nextInt(generation[0].length - (2 * BORDER)) + BORDER;
			
			for(int j = 0; j < villageLocations.size(); j++) {
				if(Math.abs(villageLocations.get(j).x - x) <= WIDTH && Math.abs(villageLocations.get(j).y - y) <= HEIGHT) {
					i--;
					continue;
				}
			}
			
			villageLocations.add(new Point(x, y));
			Log.log("Generated village at: ("+villageLocations.get(i).x+","+villageLocations.get(i).y+")", 4);
		}
		
		// Generate the structures
		for(int i = 0; i < villageLocations.size(); i++) {
			for(int k = villageLocations.get(i).y, y = 0; y < 15; k++, y++) {
				for(int j = villageLocations.get(i).x, l = 0; l < 30; j++, l++) {
					generation[k][j] = (byte)(Structures.VILLAGE_1[y][l] - 1);
				}
			}
		}
		
	}
	
	public byte[][] getMap() {
		return generation;
	}
	
	public ArrayList<Point> getVillageLocations() {
		return villageLocations;
	}
	
}
