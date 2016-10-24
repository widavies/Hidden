package com.cpjd.hidden.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

// An open world generation algorithm by yours truly
public class OpenWorld implements Runnable {

	Random r;
	
	// Tile ids
	public static final int GRASS_1 = 4;
	public static final int GRASS_2 = 5;
	public static final int GRASS_3 = 6;
	public static final int GRASS_4 = 7;
	public static final int GRASS_5 = 8;
	public static final int GRASS_6 = 9;
	
	public static final int TREE = 3;
	public static final int WATER = 31;
	public static final int STONE = 1;
	public static final int FOREST_1 = 34;
	public static final int FOREST_2 = 35;
	public static final int FOREST_3 = 36;
	public static final int FOREST_4 = 37;
	
	
	// Vars
	private double progress;
	private double maxProgress;
	private int[][] map;
	private ArrayList<Point> poolLocations;
	private ArrayList<Point> forestLocations;
	private Point spawn;
	
	// Constants
	private int WATER_MARGIN = 4; // Water can't span more than 10 tiles into the map
	private int POOL_RANGE = 5; // The farthest away water blocks can be from a pool origin point
	private int FOREST_RANGE = 8;
	
	// Generation probabilities (1 is max, 0 is no chances)
	
	/*
	 * Description of custom open-world generation. Decided not to implement perlin noise or similar because we want it more tuned to the style we're looking for
	 * 1) Water is generated around edges. Won't generate past the margin-border variable
	 * 2) Generate small pools of water
	 * 2) The closer the tile to generate is to the outside, the higher probability of being water it will have is
	 * 3) A forest tile has a small probability of generating, if a tile is generated, then a forest will be randomly generated around it and no more forests will be allowed to generate nearby
	 * 4) Grass variations are most likely to be variation 1 but chances of adding flowers, grass variations, etc
	 * 5) Prisons exist from tiers I-X, the higher the tier, the better the loot and less liklyhood of generating. However, guarenntee at least 1 prison at each map 
	 * 6) Villages generate similar to forests
	 * 7) Other things are based of probabily
	 */
	
	private Thread thread;
	
	public OpenWorld() {
		r = new Random();
		poolLocations = new ArrayList<Point>();
		forestLocations = new ArrayList<Point>();
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		map = generateWorld(200,200);
		
		try {
			thread.join();
		} catch(Exception e) {}
	}
	
	public int getGenerationProgress() {
		return (int)(progress / maxProgress * 100);
	}
	
	public boolean isFinishedGeneration() {
		return progress >= maxProgress;
	}
	
	// Should only be called after getGenerationProgress() returns 100
	public int[][] getWorld() {
		return map;
	}
	
	public int[][] generateFlatWorld(int tileWidth, int tileHeight) {
		int[][] gen = new int[tileHeight][tileWidth];
		for(int i = 0; i < tileHeight; i++) {
			for(int j = 0; j < tileWidth; j++) {
				gen[i][j] = 1;
				
			}
		}
		gen[0][0] = 30;
		return gen;
	}
	
	public int[][] generateWorld(int tileWidth, int tileHeight) {
		int[][] generation = new int[tileHeight][tileWidth];
		
		maxProgress = tileWidth * tileHeight;
		
		// Precalculate forest locations
		for(int i = 0; i < tileHeight; i++) {
			for(int j = 0; j < tileWidth; j++) {
				if(r.nextInt(2000) <= 1) forestLocations.add(new Point(j, i));
				if(r.nextInt(1000) <= 1) poolLocations.add(new Point(j, i));
			}				
		}
		
		for(int i = 0; i < tileHeight; i++) {
			for(int j = 0; j < tileWidth; j++) {
				progress++;
				
				if(shouldGenOcean(tileWidth, tileHeight, i, j)) generation[i][j] = WATER;
				else if(shouldGenForest(generation, tileWidth, tileHeight, i, j)) generation[i][j] = getForestVariation();
				else if(shouldGenPool(generation, tileWidth, tileHeight, i, j)) generation[i][j] = WATER;
				else generation[i][j] = getGrassVariation();
			}
		}
		
		map = generation;
		
		/*
		 * Let's figure out a good spawning location with these characteristics
		 * 1) Centerish of map
		 * 2) Not on a collision tile
		 * 3) Not boxed in
		 * 
		 * Technically speaking, how do we do it?
		 * First, pick the middle tile. Extend eastward until we hit a grass tile
		 */
		int offset = tileWidth / 2;
		do {
			if(offset > tileWidth - 1) offset = tileWidth - 1; 
			
			if(generation[tileHeight / 2][offset] <= 10) {
				// Viable spawn!
				spawn = new Point(tileHeight / 2, offset);
			}
			
			offset++;
		} while(generation[tileHeight / 2][offset] > 10);
		
		
		
		return generation;
	}
	public Point getSpawn() {
		if(spawn == null) return null;
		
		spawn.setLocation(spawn.x * 16, spawn.y * 16);
		return spawn;
	}
	private boolean shouldGenOcean(int tileWidth, int tileHeight, int row, int col) {
		if(row > WATER_MARGIN && row < (tileHeight - 1) - WATER_MARGIN && col > WATER_MARGIN && col < (tileWidth - 1) - WATER_MARGIN) return false;
		
		if(col > WATER_MARGIN) col = Math.abs(col - (tileWidth - 1)); 
		if(row > WATER_MARGIN) row = Math.abs(row - (tileHeight - 1));
		
		if(row <= r.nextInt(WATER_MARGIN) || col <= r.nextInt(WATER_MARGIN)) return true;
		return false;
	}
	private int getGrassVariation() {
		int prob = r.nextInt(100);
		if(prob <= 25) return GRASS_1;
		if(prob > 25 && prob <= 50) return GRASS_2;
		if(prob > 50 && prob <= 75) return GRASS_3;
		if(prob > 75 && prob <= 76) return GRASS_5;
		if(prob == 77) return GRASS_6;
		else return GRASS_4;
	}
	private boolean shouldGenForest(int[][] map, int tileWidth, int tileHeight, int row, int col) {
		if(map[row][col] == WATER) return false;
		
		// Several steps here. First determine if we're within the forest range of an existing forest, if we are, test the probability of this tile being a forest tile
		for(int i = 0; i < forestLocations.size(); i++) {
			double range = Math.hypot(forestLocations.get(i).x - col, forestLocations.get(i).y - row);
			if(range < FOREST_RANGE) {
				if(range <= r.nextInt(FOREST_RANGE)) return true;
				if(range <= r.nextInt(FOREST_RANGE)) return true;
				if(range <= r.nextInt(FOREST_RANGE)) return true;
			}
		}
		
		return false;
	}
	private int getForestVariation() {
		int prob = r.nextInt(100);
		if(prob <= 75) return FOREST_1;
		if(prob > 75 && prob <= 90) return FOREST_2;
		if(prob > 90 && prob <= 98) return FOREST_3;
		else return FOREST_4;
	}
	
	private boolean shouldGenPool(int[][] map, int tileWidth, int tileHeight, int row, int col) {
		for(int i = 0; i < poolLocations.size(); i++) {
			double range = Math.hypot(poolLocations.get(i).x - col, poolLocations.get(i).y - row);
			if(range < POOL_RANGE) {
				if(range <= r.nextInt(POOL_RANGE)) return true;
				if(range <= r.nextInt(POOL_RANGE)) return true;
				if(range <= r.nextInt(POOL_RANGE)) return true;
			}
		}
		
		return false;
	}
}
