package com.cpjd.cascade.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.hidden.main.GamePanel;

public class Map {
	// Consants
	public static byte SCALE = 4;
	
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
	private double lastx, lasty, x, y; // Player x,y location in px
	private double xOffset, yOffset; // The left and top visible tiles
	private double smoothx, smoothy;
	private double xmin, ymin, xmax, ymax;
	
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

		width = (numCols - 25)* (tileSize * 4);
		height = (numRows - 16)* (tileSize * 4);
		xmin = 0;
		ymin = 0;
		xmax = width;
		ymax = height;
	}
	
	public void draw(Graphics2D g) {
		int startRow = (int)(yOffset / (tileSize * SCALE));
		int startCol = (int)(xOffset / (tileSize * SCALE));
		
		for(int row = startRow, rowPx = 0; row < numRowsToDraw + startRow; row++, rowPx++) {
			for(int col = startCol, colPx = 0; col < numColsToDraw + startCol; col++, colPx++) {
				if(row >= map[0].length || col >= map.length) continue;
				
				g.drawImage(tiles[map[row][col] / numColsAcross][map[row][col] % numColsAcross].getImage(), (int)colPx * tileSize * SCALE, (int)rowPx * tileSize * SCALE, tileSize * SCALE, tileSize * SCALE, null);
			}
		}

	}
	
	public void setCameraPosition(double x, double y) {
		xOffset -= lastx - x;
		yOffset -= lasty - y;
		
		if(lastx - x > 1) smoothx++;
		if(smoothx > tileSize * 64) smoothx = 0;
		
		if(xOffset < xmin) xOffset = xmin;
		if(yOffset < ymin) yOffset = ymin;
		if(xOffset > xmax) xOffset = xmax;
		if(yOffset > ymax) yOffset = ymax;
		
		numColsToDraw = (byte)(GamePanel.WIDTH / (tileSize * SCALE) + 1);
		numRowsToDraw = (byte)(GamePanel.HEIGHT / (tileSize * SCALE) + 1);
		
		lastx = x;
		lasty = y;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
}
