package com.cpjd.hidden.genworld2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Turbulence is the top-level class for controlling world generation.
 * It's based off modular generation.
 * 
 * -Create as many TerrainModule as neccessary to manage the generation of the entire map.
 * -Create one VillageModule to generate villages.
 * -Create on PrisonModule to generate prisons.
 * 
 * Make sure to change the current modules number to the number of modules used.
 * @author Will Davies
 *
 */
public class Turbulence implements Runnable, ModuleListener {
	
	private TurbulenceListener listener;
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	
	private Random r;
	private ArrayList<Point> forestLocations, poolLocations;
	private int[][][] map;
	private int currentModules;

	private ArrayList<TurbulenceModule> modules;
	
	public Turbulence() {
		forestLocations = new ArrayList<Point>();
		poolLocations = new ArrayList<Point>();
		r = new Random();
		
		modules = new ArrayList<TurbulenceModule>();
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if(r.nextInt(2000) <= 1) forestLocations.add(new Point(j, i));
				if(r.nextInt(1000) <= 1) poolLocations.add(new Point(j, i));
			}
		}
		
		map = new int[TurbulenceConstants.WIDTH][TurbulenceConstants.HEIGHT][2];
		
		for(int row = 0; row < TurbulenceConstants.HEIGHT / TurbulenceConstants.BUFFER_SIZE; row++) {
			for(int col = 0; col < TurbulenceConstants.WIDTH / TurbulenceConstants.BUFFER_SIZE; col++) {
				modules.add(new TerrainModule(map, row * TurbulenceConstants.BUFFER_SIZE, col * TurbulenceConstants.BUFFER_SIZE, TurbulenceConstants.BUFFER_SIZE, TurbulenceConstants.BUFFER_SIZE, poolLocations, forestLocations, this));
			}
		}
		
		modules.add(new VillageModule(map, this));
	}

	public double getCollectiveProgress() {
		return currentModules / ((double) modules.size()+ 1);
	}
	
	public void release() {
		modules.clear();
	}
	
	public int[][][] getWorld() {
		return map;
	}
	
	@Override
	public void moduleFinished(int[][][] map, int startx, int starty, int width, int height, ArrayList<Point> villageLocations, ArrayList<ArrayList<Point>> prisonLocations) {
		for(int row = starty; row < height; row++) {
			for(int col = startx; col < width; col++) {
				this.map[row][col][0] = map[row][col][0];
				this.map[row][col][1] = map[row][col][1];
			}
		}
		
		currentModules++;
		
		if(currentModules == modules.size()) {
			currentModules++;
			listener.worldGenerated(map, villageLocations, prisonLocations, findSpawn());
		}
	}

	public void addListener(TurbulenceListener listener) {
		this.listener = listener;
	}

	@Override
	public void villagesFinished(int[][][] map, ArrayList<Point> villageLocations) {
		modules.add(new PrisonModule(map, villageLocations, this));
		
	}
	
	// Location a valid spawning location
	private Point findSpawn() {
		int xOffset = TurbulenceConstants.WIDTH / 2;
		int yOffset = TurbulenceConstants.HEIGHT / 2;
		int searches = 0;
		int spawnSafeRange = 4;
		boolean viable = false;
		int randomAttempts = 0;
		Point spawn = null;
		
		do {
			viable = true;
			for(int i = 0; i < spawnSafeRange; i++) {
				for(int j = 0; j < spawnSafeRange; j++) {
					if(yOffset + i >= map[0].length || xOffset + j >= map.length) continue;
					
					// FIXME
					if(map[yOffset + i][xOffset + j][1] > 10) viable = false;
				}
			}
			
			if(searches == 0) {
				xOffset+=2;
				if(xOffset > TurbulenceConstants.WIDTH - 1) {
					xOffset = TurbulenceConstants.WIDTH / 2;
					searches = 1;
				}
			}
			else if(searches == 1) {
				xOffset-=2;
				if(xOffset < 0) {
					xOffset = TurbulenceConstants.WIDTH / 2;
					searches = 2;
				}
			}
			else if(searches == 2) {
				yOffset += 2;
				if(yOffset > TurbulenceConstants.HEIGHT - 1) {
					yOffset = TurbulenceConstants.HEIGHT / 2;
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
			else if(searches == 3) {
				xOffset = r.nextInt(TurbulenceConstants.WIDTH - 1);
				yOffset = r.nextInt(TurbulenceConstants.HEIGHT - 1);
				randomAttempts++;
			}
			
			if(randomAttempts > 30) {
				viable = true;
			}
		} while(!viable);
		
		if(viable && randomAttempts >= 30) {
			// regenerate world
		} else if(viable) {
			spawn = new Point(xOffset + (int)(spawnSafeRange / 2), yOffset + (int)(spawnSafeRange / 2));
		}
		
		return spawn;
	}
}
