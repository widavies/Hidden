package com.cpjd.hidden.files;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents all the data that should be saved for the game
 * @author Will Davies
 *
 */
public class GameSave implements Serializable {

	private static final long serialVersionUID = 525924313781734122L;
	
	private byte[][] map;
	private ArrayList<ArrayList<Point>> prisonLocations;
	private Point playerLocation;
	
	public void setMap(byte[][] map) {
		this.map = map;
	}
	
	public byte[][] getMap() {
		return map;
	}
	
	public ArrayList<ArrayList<Point>> getPrisonLocations() {
		return prisonLocations;
	}
	
	public void setPrisonLocations(ArrayList<ArrayList<Point>> prisonLocations) {
		this.prisonLocations = prisonLocations;
	}
	
	public void setPlayerLocation(Point p) {
		this.playerLocation = p;
	}
	
	public Point getPlayerLocation() {
		return playerLocation;
	}
	
	
}
