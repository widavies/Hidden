package com.cpjd.hidden.toolbox.pathfind;

import java.awt.Point;

/**
 * For use with A* pathfinding
 * @author Alex Harker
 *
 */
public class Node {

	public int cost;
	public int heuristic;
	
	public Point parent;//where the path came from to this tile
	
}
