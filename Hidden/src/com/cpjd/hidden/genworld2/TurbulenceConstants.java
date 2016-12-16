package com.cpjd.hidden.genworld2;

public class TurbulenceConstants {
	public static int WIDTH = 1000;
	public static int HEIGHT = 1000;
	
	public static int BUFFER_SIZE = 100; // Square tiles per thread
	
	// Terrain
	public static int WATER_MARGIN = 4;
	public static int POOL_RANGE = 5;
	public static int FOREST_RANGE = 8;
	
	// Villages
	public static final int VILLAGE_WIDTH = 30;
	public static final int VILLAGE_HEIGHT = 15;
	public static final byte VILLAGE_BORDER = 50;
	public static final byte VILLAGES_TO_GENERATE = 5;
}
