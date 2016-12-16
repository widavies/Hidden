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
	
	private int NUM_MODULES;
	
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
		
		map = new int[500][500][2];
		
		for(int row = 0; row < TurbulenceConstants.HEIGHT / TurbulenceConstants.BUFFER_SIZE; row++) {
			for(int col = 0; col < TurbulenceConstants.WIDTH / TurbulenceConstants.BUFFER_SIZE; col++) {
				modules.add(new TerrainModule(map, row, col, TurbulenceConstants.BUFFER_SIZE, TurbulenceConstants.BUFFER_SIZE, poolLocations, forestLocations, this));
			}
		}
		
		modules.add(new VillageModule(map, this));
		
		NUM_MODULES = modules.size();
	}

	public double getCollectiveProgress() {
		double progress = 0;
		for(int i = 0; i < modules.size(); i++) {
			progress += modules.get(i).getProgress();
		}
		return progress / (TurbulenceConstants.WIDTH * TurbulenceConstants.HEIGHT) * 2;
	}
	
	public void release() {
		modules.clear();
	}
	
	public int[][][] getWorld() {
		return map;
	}
	
	@Override
	public void moduleFinished(int[][][] map, int startx, int starty, int width, int height) {
		for(int row = starty; row < height; row++) {
			for(int col = startx; col < width; col++) {
				this.map[row][col][0] = map[row][col][0];
				this.map[row][col][1] = map[row][col][1];
			}
		}
		
		currentModules++;
		
		if(currentModules == NUM_MODULES + 1) listener.worldGenerated(map);
	}

	public void addListener(TurbulenceListener listener) {
		this.listener = listener;
	}

	@Override
	public void villagesFinished(int[][][] map, ArrayList<Point> villageLocations) {
		System.out.println("Starting prison module");
		modules.add(new PrisonModule(map, villageLocations, this));
		
	}
}
