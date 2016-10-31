package com.cpjd.cascade.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.hidden.main.GamePanel;

public class Map {
	// Consants
	public static byte SCALE = 1;
	
	// Map
	private byte[][] map;
	private int tileSize;
	private Tile[][] tiles;
	
	// Tileset
	private int numRowsAcross, numColsAcross;
	
	// Drawing
	private byte numColsToDraw, numRowsToDraw;
	private int numRows, numCols;
	private int width, height;
	
	// Offsets
	private double x, y; // Player x,y location in px
	private int xOffset, yOffset; // The left and top visible tiles
	
	public Map(int tileSize) {
		this.tileSize = tileSize;
	}
	
	/**
	 * Loads the image tileset
	 * @param path The location of the tileset
	 */
	public void loadTiles(String path) {
		try {
			BufferedImage tileset = ImageIO.read(getClass().getResourceAsStream(path));
			numColsAcross = tileset.getWidth() / tileSize;
			numRowsAcross = tileset.getHeight() / tileSize;
			tiles = new Tile[numRowsAcross][numColsAcross];
			
			for(byte row = 0; row < numRowsAcross; row++) {
				for(byte col = 0; col < numColsAcross; col++) {
					tiles[row][col] = new Tile(tileset.getSubimage(col * tileSize, row * tileSize, tileSize, tileSize), row);
				}
			}
			
			tileset = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the current level to be managed
	 * @param map A 2D array of tile ids
	 */
	public void setMap(byte[][] map) {
		this.map = map;
	
		numCols = map.length;
		numRows = map[0].length;
		numColsToDraw = (byte)(GamePanel.WIDTH / tileSize + 1);
		numRowsToDraw = (byte)(GamePanel.HEIGHT / tileSize + 1);
		width = numColsToDraw * tileSize;
		height = numColsToDraw * tileSize;
	}
	
	public void draw(Graphics2D g) {
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				g.drawImage(tiles[map[row][col] / numColsAcross][map[row][col] % numColsAcross].getImage(), col * tileSize * SCALE, row * tileSize * SCALE, tileSize * SCALE, tileSize * SCALE, null);
			}
		}
	}
	
	public void setCameraPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
}
