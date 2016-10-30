package com.cpjd.cascade.engine;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Map {
	
	private int[][] map; // 2d array containing the tile ids of each block
	private int tileSize;
	private Tile[][] tiles;
	
	public Map(int tileSize) {
		this.tileSize = tileSize;
	}
	
	public void loadTiles(String path) {
		try {
			BufferedImage tileset = ImageIO.read(getClass().getResourceAsStream(path));
			
			int numCols = tileset.getWidth() / tileSize;
			int numRows = tileset.getHeight() / tileSize; 
			
			tiles = new Tile[numRows][numCols];
			
			for(int col = 0; col < numCols; col++) {
				for(int row = 0; row < numRows; row++) {
					tiles[row][col] = new Tile(tileset.getSubimage(col * tileSize, row * tileSize, tileSize, tileSize), row);
				}
			}
			
			tileset = null;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {
	}
	
	
	// Pass in the entity's size and location, then specify the location it's trying to enter.
	public boolean checkCollision(Rectangle r1, double x, double y) {
		return false;
	}
	
	// Map modification methods
	public void setMap(int[][] map) {
		this.map = map;
	}
	
	public void setMapID(int x, int y, int id) {
		map[y][x] = id;
	}
	
	public int getMapID(int x, int y) {
		return map[y][x];
	}
	
	public void release() {
		this.map = null;
		this.tiles = null;
	}
}
