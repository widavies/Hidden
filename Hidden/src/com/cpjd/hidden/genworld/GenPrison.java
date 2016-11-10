package com.cpjd.hidden.genworld;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.cpjd.tools.Log;

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
	
	private final int[] MINIMUM = {25, 20, 20, 15, 15, 10, 10, 5, 5, 2};
	private int[] currentCount;
	
	private byte[][] generation;
	private Random r;
	
	private ArrayList<Point> villageLocations;
	
	public GenPrison(byte[][] generation, ArrayList<Point> villageLocations) {
		this.generation = generation;
		this.villageLocations = villageLocations;
		
		r = new Random();
		
		prisonLocations = new ArrayList<ArrayList<Point>>();
		
		currentCount = new int[10];
		
		for(int i = 0; i < prisonLocations.size(); i++) prisonLocations.add(new ArrayList<Point>());
		
		generatePrisonLocations(1, (int)(GenWorld.WIDTH * .3), -1, 800, 6, TileIDs.LASER_CENTER);
		generatePrisonLocations(2, (int)(GenWorld.WIDTH * .25), -1, 900, 6, TileIDs.WALL);
		generatePrisonLocations(3, (int)(GenWorld.WIDTH * .20), -1, 800, 8, TileIDs.BED);
		generatePrisonLocations(4, (int)(GenWorld.WIDTH * .25), (int)(GenWorld.WIDTH * 0.4), 1200, 10, TileIDs.WOOD);
		generatePrisonLocations(5, (int)(GenWorld.WIDTH * .23), (int)(GenWorld.WIDTH * 0.35), 1400, 10, TileIDs.LASER_RIGHT);
		generatePrisonLocations(6, (int)(GenWorld.WIDTH * .22), (int)(GenWorld.WIDTH * 0.32), 1400, 8, TileIDs.LASER_LEFT);
		generatePrisonLocations(7, (int)(GenWorld.WIDTH * .21), (int)(GenWorld.WIDTH * 0.28), 1600, 9, TileIDs.GLASS);
		generatePrisonLocations(8, 20, (int)(GenWorld.WIDTH * 0.25), 1800, 9, TileIDs.STONE);
		generatePrisonLocations(9, 15, (int)(GenWorld.WIDTH * 0.20), 1600, 15, TileIDs.LOCKED_DOOR_RIGHT);
		generatePrisonLocations(10, 10, (int)(GenWorld.WIDTH * 0.18), 1800, 15, TileIDs.BUSH_1);
		
		// We've got locations for everything now, let's generate the structures around them.
		for(int i = 0; i < prisonLocations.size(); i++) {
			for(int j = 0; j < prisonLocations.get(i).size(); j++) {
				generateStructure(i, prisonLocations.get(i).get(j).x, prisonLocations.get(i).get(j).y);
			}
		}
	}
	
	/**
	 * Generates a prison structure based off a location
	 * @param structure A byte array that contains the tile ids of the structure, must contain only 1 center tile, must have odd dimensions
	 * @param centerx the x position of the center block
	 * @param centery the y position of the center block
	 * @param startx the x position offset (from the center) to start generating the structure
	 * @param starty the y position offset (from the center) to start generating the structure
	 */
	private void generateStructure(int tier, int centerx, int centery) {
		byte[][] structure = null;

		switch(tier) {
		case 0:
			structure = Structures.TIER_1;
			break;
		case 1:
			structure = Structures.TIER_2;
			break;
		case 2:
			structure = Structures.TIER_3;
			break;
		case 3:
			structure = Structures.TIER_4;
			break;
		case 4:
			structure = Structures.TIER_5;
			break;
		case 5:
			structure = Structures.TIER_6;
			break;
		case 6:
			structure = Structures.TIER_7;
			break;
		case 7:
			structure = Structures.TIER_8;
			break;
		case 8:
			structure = Structures.TIER_9;
			break;
		case 9:
			structure = Structures.TIER_10;
			break;
		}
		
		if(structure == null) return;  
		for(int row = centery - (int)Math.floor(structure[0].length / 2), y = 0; y < structure.length; row++, y++) {
			for(int col = centerx - (int)Math.floor(structure.length / 2), x = 0; x < structure[0].length; col++, x++) {
				generation[row][col] = structure[y][x];
			}
		}
	}
	
	/*
	 * Start border is the distance from the outside it can't spawn under (e.g., 5 would mean it can't be tiles 0,1,2,3,4)
	 * End border is the maximum range it can spawn in from the outside (range of 10 would mean it can only spawn within 10 blocks of the edge)
	 */
	private void generatePrisonLocations(int tier, int startBorder, int endBorder, int prob, int regionSize, byte centerTile) {
		prisonLocations.add(new ArrayList<Point>());
		
		for(int col = 0; col < generation.length; col++) {
			if(col < startBorder || col > generation.length - startBorder - 1) continue;

			for(int row = 0; row < generation[0].length; row++) {
				if(row < startBorder || row > generation[0].length - startBorder - 1) continue;
				if(endBorder > 0 && (row > endBorder && row < generation.length - 1 - endBorder
						&& col > endBorder && col < generation.length - 1 - endBorder)) continue;	
				
				// Check to make sure we're in a piece of land that's not within 10 tiles of another prison, and not on top of water
				if(checkRegion(col, row, regionSize) && (r.nextInt(prob) <=1 || (currentCount[tier - 1] < MINIMUM[tier -1]) && col > currentCount[tier - 1] * 28)) {
					generation[row][col] = centerTile;
					currentCount[tier - 1]++;
					prisonLocations.get(tier - 1).add(new Point(col, row));
					
					Log.log("Tier "+tier+" prison generated at: "+col+","+row, 2);
				}
			}
		}
	}
			
	private boolean checkRegion(int col, int row, int diameter) {
		boolean regionViable;
		
		for(int i = 0; i < villageLocations.size(); i++) {
			if(Math.abs(villageLocations.get(i).x - col) <= GenVillages.WIDTH && Math.abs(villageLocations.get(i).y - row) <= GenVillages.HEIGHT) {
				return false;
			}
		}
		
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

	public ArrayList<ArrayList<Point>> getPrisonLocations() {
		return prisonLocations;
	}
	
}
