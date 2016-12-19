package com.cpjd.hidden.genworld2;

import java.awt.Point;
import java.util.ArrayList;

public interface TurbulenceListener {
	public abstract void worldGenerated(int[][][] map, ArrayList<Point> villageLocations, ArrayList<ArrayList<Point>> prisonLocations, Point spawn);
}
