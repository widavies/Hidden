package com.cpjd.hidden.genworld2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.cpjd.hidden.genworld.TileIDs;

public class TerrainModule extends TurbulenceModule implements Runnable {

	private int startx, starty, width, height;
	private ArrayList<Point> poolLocations, forestLocations;

	/**
	 * The TerrainModule manages the generation of the following features of the map:  
	 * -Grass and flowers
	 * -Trees
	 * -Water and pools
	 * 
	 * @param map The map to inject generated data to.
	 * @param startx The x index of the map to start generation at.
	 * @param starty The y index of the map to start generation at.
	 * @param width The desired width to stop map generation at (for this thread).
	 * @param height The desired height to stop map generation at (for this thread).
	 */
	public TerrainModule(int[][][] map, int startx, int starty, int width, int height, ArrayList<Point> poolLocations, ArrayList<Point> forestLocations, ModuleListener listener) {
		this.map = map;
		this.startx = startx;
		this.starty = starty;
		this.width = width;
		this.height = height;
		this.poolLocations = poolLocations;
		this.forestLocations = forestLocations;
		this.listener = listener;
		
		r = new Random();
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		for(int row = starty; row < height + starty; row++) {
			for(int col = startx; col < width + startx; col++) {

				if(map[row][col][0] != 0) continue;
				
				if(shouldGenOcean(map.length, map[0].length, row, col)) map[row][col][1] = TileIDs.WATER;
				else if(shouldGenPool(map.length, map[0].length, row, col)) map[row][col][1] = TileIDs.WATER;
				else if(shouldGenForest(map, map.length, map[0].length, row, col)) {
					map[row][col][0] = getGrassVariation(false);
					map[row][col][1] = getForestVariation();
				}
				else map[row][col][0] = getGrassVariation(true);
				
				progress++;
			}
		}
		
		listener.moduleFinished(map, startx, starty, width, height, null, null);
		
		try {
			thread.join();
		} catch(Exception e) {
			System.err.println("A terrain module could not be stopped");
		}
	}


	private int getGrassVariation(boolean flowers) {
		int prob = r.nextInt(100);
		if(prob <= 25) return TileIDs.GRASS_1;
		if(prob > 25 && prob <= 50) return TileIDs.GRASS_2;
		if(prob > 50 && prob <= 75) return TileIDs.GRASS_3;
		if(prob > 75 && prob <= 76 && flowers) return TileIDs.FLOWER_1;
		if(prob == 77 && flowers) return TileIDs.FLOWER_2;
		else return TileIDs.GRASS_4;
	}
	
	private boolean shouldGenOcean(int tileWidth, int tileHeight, int row, int col) {
		if(row > TurbulenceConstants.WATER_MARGIN && row < (tileHeight - 1) - TurbulenceConstants.WATER_MARGIN && col > TurbulenceConstants.WATER_MARGIN && col < (tileWidth - 1) - TurbulenceConstants.WATER_MARGIN) return false;
		
		if(col > TurbulenceConstants.WATER_MARGIN) col = Math.abs(col - (tileWidth - 1)); 
		if(row > TurbulenceConstants.WATER_MARGIN) row = Math.abs(row - (tileHeight - 1));
		
		if(row <= r.nextInt(TurbulenceConstants.WATER_MARGIN) || col <= r.nextInt(TurbulenceConstants.WATER_MARGIN)) return true;
		return false;
	}
	
	private boolean shouldGenForest(int[][][] map, int tileWidth, int tileHeight, int row, int col) {
		if(map[row][col][0] == TileIDs.WATER) return false;
		
		for(int i = 0; i < forestLocations.size(); i++) {
			double range = Math.hypot(forestLocations.get(i).x - col, forestLocations.get(i).y - row);
			if(range < TurbulenceConstants.FOREST_RANGE) {
				if(range <= r.nextInt(TurbulenceConstants.FOREST_RANGE)) return true;
				if(range <= r.nextInt(TurbulenceConstants.FOREST_RANGE)) return true;
				if(range <= r.nextInt(TurbulenceConstants.FOREST_RANGE)) return true;
			}
		}
		
		return false;
	}
	private int getForestVariation() {
		int prob = r.nextInt(100);
		if(prob <= 75) return TileIDs.FOREST_1;
		if(prob > 75 && prob <= 90) return TileIDs.FOREST_2;
		if(prob > 90 && prob <= 98) return TileIDs.FOREST_3;
		else return TileIDs.FOREST_4;
	}
	
	private boolean shouldGenPool(int tileWidth, int tileHeight, int row, int col) {
		for(int i = 0; i < poolLocations.size(); i++) {
			double range = Math.hypot(poolLocations.get(i).x - col, poolLocations.get(i).y - row);
			if(range < TurbulenceConstants.POOL_RANGE) {
				if(range <= r.nextInt(TurbulenceConstants.POOL_RANGE)) return true;
				if(range <= r.nextInt(TurbulenceConstants.POOL_RANGE)) return true;
				if(range <= r.nextInt(TurbulenceConstants.POOL_RANGE)) return true;
			}
		}
		return false;
	}
}
