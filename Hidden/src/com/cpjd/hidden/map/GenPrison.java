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
	
	private final int[] MINIMUM = {25, 20, 20, 15, 15, 10, 10, 5, 5, 5};
	private int[] currentCount;
	
	private byte[][] generation;
	private Random r;
	
	public GenPrison(byte[][] generation) {
		this.generation = generation;
		r = new Random();
		
		prisonLocations = new ArrayList<ArrayList<Point>>();
		
		currentCount = new int[10];
		
		for(int i = 0; i < prisonLocations.size(); i++) prisonLocations.add(new ArrayList<Point>());
		
		generatePrisonLocations(1, (int)(GenWorld.WIDTH * .3), -1, 800, 6, TileIDs.LASER_CENTER);
		generatePrisonLocations(2, (int)(GenWorld.WIDTH * .25), -1, 900, 6, TileIDs.WALL);
		generatePrisonLocations(3, (int)(GenWorld.WIDTH * .20), -1, 800, 6, TileIDs.BED);
		generatePrisonLocations(4, (int)(GenWorld.WIDTH * .25), (int)(GenWorld.WIDTH * 0.4), 1200, 8, TileIDs.WOOD);
		generatePrisonLocations(5, (int)(GenWorld.WIDTH * .23), (int)(GenWorld.WIDTH * 0.35), 1400, 8, TileIDs.LASER_RIGHT);
		generatePrisonLocations(6, (int)(GenWorld.WIDTH * .22), (int)(GenWorld.WIDTH * 0.32), 1400, 8, TileIDs.LASER_LEFT);
		generatePrisonLocations(7, (int)(GenWorld.WIDTH * .21), (int)(GenWorld.WIDTH * 0.28), 1600, 9, TileIDs.GLASS);
		generatePrisonLocations(8, 20, (int)(GenWorld.WIDTH * 0.25), 1800, 9, TileIDs.STONE);
		generatePrisonLocations(9, 15, (int)(GenWorld.WIDTH * 0.20), 2000, 10, TileIDs.OPEN_DOOR);
		generatePrisonLocations(10, 10, (int)(GenWorld.WIDTH * 0.18), 2200, 10, TileIDs.BUSH_1);
		
		// We've got locations for everything now, let's generate the structures around them.
		for(int i = 0; i < prisonLocations.size(); i++) {
			for(int j = 0; j < prisonLocations.get(i).size(); j++) {
				generateStructure(i, prisonLocations.get(i).get(j).x, prisonLocations.get(i).get(j).y, 1, 1);
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
	private void generateStructure(int tier, int centerx, int centery, int startx, int starty) {
		byte[][] structure = null;

		switch(tier) {
		case 1:
			structure = Structures.TIER_1;
			break;
		default:
			return;
		}
		
		for(int col = centerx - startx, x = 0; x < structure.length; col++, x++) {
			for(int row = centery, y = 0; y < structure[0].length; row++, y++) {
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
					
					if(tier == 1) {
						System.out.println("Generated a tier 1 prison @: "+col+","+row);
					}
				}
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
