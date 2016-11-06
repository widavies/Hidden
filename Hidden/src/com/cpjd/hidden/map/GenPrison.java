package com.cpjd.hidden.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

// Manages prison generation for the map
public class GenPrison {

	/*
	 * Prison spawning characteristics
	 * - We won't allow prisons to spawn near water
	 * - Prisons can spawn in the middle of forests
	 * - Prisons have a minimum distance of how close they can be - 10 tiles
	 * - They are generated off a reverse tier percentage e.g. tier10 extremely rare, tier1 fairly common
	 * - Guarentee the generation of a minimum number of each tier, here are the minimums
	 * tier1 - 30
	 * tier2 - 28
	 * tier3 - 25
	 * tier4 - 22
	 * tier5 - 21
	 * tier6 - 20
	 * tier7 - 19
	 * tier8 - 16
	 * tier9 - 15
	 * tier10 - 6
	 * - Prisons are specified by a coordinate (x,y) in tiles, this is center of the prison, the structure is generated around it.
	 * Each prison at each (x,y) will also have the following generated for it
	 * - A name
	 * - Specifics like reword, prisoner's name, other things like that
	 * -Spawn higher tier prisons to the outside of map, lower tiers to the inside
	 */
	
	// A prison must generate in a region that only contains the following tile types:
	private final int[] VIABLE_TILES = {TileIDs.GRASS_1, TileIDs.GRASS_2, TileIDs.GRASS_3, TileIDs.GRASS_4,
			TileIDs.FLOWER_1, TileIDs.FLOWER_2, TileIDs.FOREST_1, TileIDs.FOREST_2, TileIDs.FOREST_3, TileIDs.FOREST_4};

	private ArrayList<ArrayList<Point>> prisonLocations;
	
	private byte[][] generation;
	private Random r;
	
	public GenPrison(byte[][] generation) {
		this.generation = generation;
		r = new Random();
		
		prisonLocations = new ArrayList<ArrayList<Point>>();
		
		for(int i = 0; i < prisonLocations.size(); i++) prisonLocations.add(new ArrayList<Point>());
		
		generatePrisonLocations(1, (int)(GenWorld.WIDTH * .3), -1, 170, 6);
	}
	
	/*
	 * Start border is the distance from the outside it can't spawn under (e.g., 5 would mean it can't be tiles 0,1,2,3,4)
	 * End border is the maximum range it can spawn in from the outside (range of 10 would mean it can only spawn within 10 blocks of the edge)
	 */
	private void generatePrisonLocations(int tier, int startBorder, int endBorder, int prob, int regionSize) {
		for(int col = 0; col < generation.length; col++) {
			if(col < startBorder || col > generation.length - startBorder - 1) continue;

			for(int row = 0; row < generation[0].length; row++) {
				if(row < startBorder || row > generation[0].length - startBorder - 1) continue;
				if(endBorder > 0 && (row > endBorder && row < generation.length - 1 - endBorder
						&& col > endBorder && col < generation.length - 1 - endBorder)) continue;	
				
				// Check to make sure we're in a piece of land that's not within 10 tiles of another prison, and not on top of water
				if(checkRegion(col, row, regionSize) && r.nextInt(prob) <=1) generation[row][col] = TileIDs.LASER_CENTER;
				
			}
		}
	}
			
	private boolean checkRegion(int col, int row, int diameter) {
		boolean regionViable;
		
		for(int x = col - (diameter / 2); x < (col - diameter / 2) + diameter; x++) {
			for(int y = row - (diameter / 2); y < (row - (diameter / 2) + diameter); y++) {
				if(col < 0 || row < 0 || x > generation.length || y > generation[0].length) return false;
				
				regionViable = false;
				for(int i = 0; i < VIABLE_TILES.length; i++) {
					if(generation[y][x] == VIABLE_TILES[i]) {
						regionViable = true;
						break;
					}
				}
				
				if(!regionViable) return false;
			}
		}
		
		return true;
	}
	
	public byte[][] getMap() {
		return generation;
	}

}
