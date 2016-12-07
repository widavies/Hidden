package com.cpjd.hidden.files;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import com.cpjd.hidden.items.Item;

/**
 * Represents all the data that should be saved for the game
 * @author Will Davies
 *
 */
public class GameSave implements Serializable {

	private static final long serialVersionUID = 525924313781734122L;
	
	// Map related
	private byte[][][] map;
	private ArrayList<ArrayList<Point>> prisonLocations;
	private Point playerLocation;
	
	// Inventory related
	private Item[][] inventory;
	private Item[] hotbar;
	private Item[][] clothing;
	
	
	/*
	 * GETTERS AND SETTERS - NOTHING TO SEE HERE
	 */
	public byte[][][] getMap() {
		return map;
	}
	public void setMap(byte[][][] map) {
		this.map = map;
	}
	public ArrayList<ArrayList<Point>> getPrisonLocations() {
		return prisonLocations;
	}
	public void setPrisonLocations(ArrayList<ArrayList<Point>> prisonLocations) {
		this.prisonLocations = prisonLocations;
	}
	public Point getPlayerLocation() {
		return playerLocation;
	}
	public void setPlayerLocation(Point playerLocation) {
		this.playerLocation = playerLocation;
	}
	public Item[][] getInventory() {
		return inventory;
	}
	public void setInventory(Item[][] inventory) {
		this.inventory = inventory;
	}
	public Item[] getHotbar() {
		return hotbar;
	}
	public void setHotbar(Item[] hotbar) {
		this.hotbar = hotbar;
	}
	public Item[][] getClothing() {
		return clothing;
	}
	public void setClothing(Item[][] clothing) {
		this.clothing = clothing;
	}

}
