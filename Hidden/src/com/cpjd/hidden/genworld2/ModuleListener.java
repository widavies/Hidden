package com.cpjd.hidden.genworld2;

import java.awt.Point;
import java.util.ArrayList;

public interface ModuleListener {
	public abstract void moduleFinished(int[][][] map, int startx, int starty, int width, int height);
	public abstract void villagesFinished(int[][][] map, ArrayList<Point> villageLocations);
}
