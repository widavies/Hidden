package com.cpjd.hidden.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

// An open world generation algorithm by yours truly
public class OpenWorld implements Runnable {

	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	private Random r;
	private WorldListener listener;
	
	// Tile ids
	private final int GRASS_1 = 4;
	private final int GRASS_2 = 5;
	private final int GRASS_3 = 6;
	private final int GRASS_4 = 7;
	private final int GRASS_5 = 8;
	private final int GRASS_6 = 9;	
	private final int WATER = 31;
	private final int FOREST_1 = 34;
	private final int FOREST_2 = 35;
	private final int FOREST_3 = 36;
	private final int FOREST_4 = 37;
	private final int LOCKED_DOOR = 32;
	private final int OPEN_DOOR = 33;
	private final int WALL = 30;
	
	// Vars
	private double progress;
	private double maxProgress;
	private byte[][] map;
	private ArrayList<Point> poolLocations;
	private ArrayList<Point> forestLocations;
	private Point spawn;
	
	// Constants
	private int WATER_MARGIN = 4; // Water can't span more than 4 tiles into the map
	private int POOL_RANGE = 5; // The farthest away water blocks can be from a pool origin point
	private int FOREST_RANGE = 8;
	
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
	}
	
	public void addWorldListener(WorldListener listener) {
		this.listener = listener;
	}
	
	public void generate() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		generateWorld(WIDTH,HEIGHT);
		
		reset();
	}
	
	private void reset() {
		try {
			thread.join();
		} catch(Exception e) {
			System.err.println("Couldn't stop the terrain generation thread");
		}
		
		progress = 0;
		map = null;
	}

	// Should only be called after getGenerationProgress() returns 100
	public byte[][] getWorld() {
		return map;
	}
	
	private void generateWorld(int tileWidth, int tileHeight) {
		byte[][] generation = new byte[tileHeight][tileWidth];
		
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
				listener.updateProgress(progress / maxProgress);
				
				if(generation[i][j] != 0) continue;
				
				if(shouldGenOcean(tileWidth, tileHeight, i, j)) generation[i][j] = WATER;
				else if(shouldGenForest(generation, tileWidth, tileHeight, i, j)) generation[i][j] = (byte)getForestVariation();
				else if(shouldGenPool(generation, tileWidth, tileHeight, i, j)) generation[i][j] = WATER;
				else if(r.nextInt(900) <= 1) {
					generation[i][j] = 2;
					generation[i - 1][j] = WALL;
					generation[i + 1][j] = LOCKED_DOOR;
					generation[i][j - 1] = WALL;
					generation[i][j + 1] = WALL;
					generation[i + 1][j - 1] = WALL;
					generation[i + 1][j + 1] = WALL;
					generation[i - 1][j - 1] = WALL;
					generation[i - 1][j + 1] = WALL;
				}
				else generation[i][j] = (byte)getGrassVariation();
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
		int xOffset = tileWidth / 2;
		int yOffset = tileHeight / 2;
		int searches = 0; // 0, we're going right until the edge, 1, left until the edge, 2 up until the edge, 3 down until the edge
		int spawnSafeRange = 6;
		boolean viable = false;
		int randomAttempts = 0;
		do {
			viable = true;
			for(int i = 0; i < spawnSafeRange; i++) {
				for(int j = 0; j < spawnSafeRange; j++) {
					if(generation[yOffset + i][xOffset + j] > 10) viable = false; 
				}
			}
			
			if(searches == 0) {
				xOffset+=2;
				if(xOffset > tileWidth - 1) {
					xOffset = tileWidth / 2;
					searches = 1;
				}
			}
			else if(searches == 1) {
				xOffset-=2;
				if(xOffset < 0) {
					xOffset = tileWidth / 2;
					searches = 2;
				}
			}
			else if(searches == 2) {
				yOffset+=2;
				if(yOffset > tileHeight - 1) {
					yOffset = tileHeight / 2;
					searches = 3;
				}
			}
			else if(searches == 3) {
				yOffset-=2;
				if(yOffset < 0) {
					yOffset = 0;
					searches = 4;
				}
			}
			
			if(searches == 4) {
				xOffset = r.nextInt(tileWidth - 1);
				yOffset = r.nextInt(tileHeight - 1);
				randomAttempts++;
			}
			
			if(randomAttempts > 10) { 
				viable = true;
			}
		} while(!viable);
		
		if(viable && randomAttempts >= 10) {
			// We couldn't find a spawn location. :( Regenerate the map.
			generateWorld(tileWidth,tileHeight);
		} else if(viable) {
			spawn = new Point(xOffset + (int)(spawnSafeRange / 2), yOffset + (int)(spawnSafeRange / 2));
			listener.worldGenerated();
			
		}
		map = generation;
	}
	public Point getSpawn() {
		if(spawn == null) return null;
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
	private boolean shouldGenForest(byte[][] map, int tileWidth, int tileHeight, int row, int col) {
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
	
	private boolean shouldGenPool(byte[][] map, int tileWidth, int tileHeight, int row, int col) {
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
