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
	private double scaledTileSize;
	private Tile[][] tiles;
	
	// Tileset
	private int numRowsAcross, numColsAcross;
	
	// Drawing
	private byte numColsToDraw, numRowsToDraw;
	private int numRows, numCols;
	private int width, height;
	private short startRow, startCol;
	private double adjustx, adjusty;
	
	// Offsets
	private double lastx, lasty;
	private double xOffset, yOffset; // The left and top visible tiles
	private double xmin, ymin, xmax, ymax;
	private double borderx, bordery;
	
	public Map(int tileSize) {
		this.tileSize = tileSize;
		this.scaledTileSize = tileSize * SCALE;
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
		startRow = (short)(yOffset / scaledTileSize);
		startCol = (short)(xOffset / scaledTileSize);
		adjustx = xOffset % scaledTileSize;
		adjusty = yOffset % scaledTileSize;
		
		for(short row = startRow, rowPx = 0; row < numRowsToDraw + startRow; row++, rowPx++) {
			if(row >= map[0].length) break;
			
			for(short col = startCol, colPx = 0; col < numColsToDraw + startCol; col++, colPx++) {
				if(col >= map.length) break;
				
				g.drawImage(tiles[map[row][col] / numColsAcross][map[row][col] % numColsAcross].getImage(),(int)((double)colPx * scaledTileSize - adjustx), (int)((double)rowPx * scaledTileSize - adjusty), (int)scaledTileSize, (int)scaledTileSize, null);

			}
		}
	}

	/**
	 * Draws the map to the player's position.
	 * @param x horizontal pixels away from the upper-left most tile
	 * @param y vertical pixels away form the upper-left most tile
	 */
	public void setCameraPosition(double x, double y) {
		//x = 0; y = 0;
		xOffset -= lastx - x;
		yOffset -= lasty - y;
		
		if(xOffset < xmin) xOffset = xmin;
		if(yOffset < ymin) yOffset = ymin;
		if(xOffset > xmax) xOffset = xmax;
		if(yOffset > ymax) yOffset = ymax;
		
		numColsToDraw = (byte)(GamePanel.WIDTH / (tileSize * SCALE) + 2);
		numRowsToDraw = (byte)(GamePanel.HEIGHT / (tileSize * SCALE) + 2);
		
		lastx = x;
		lasty = y;
	}
	
	/**
	 * @return Tile size (px)
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * @return Tile size multiplied by the scale factor (px)
	 */
	public int getScaledTileSize() {
		return (int)scaledTileSize;
	}
	
}
