package com.cpjd.hidden.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.cpjd.hidden.main.GamePanel;

/**
 * Manages the loading, drawing, scaling, and editing of the map.
 * @author Will Davies
 *
 */
public class Map {
	// Constants
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
	private double xOffset, yOffset;
	private int lastScreenWidth, lastScreenHeight;
	
	public Map(int tileSize) {
		this.tileSize = tileSize;
		this.scaledTileSize = tileSize * SCALE;
		
		lastScreenWidth = GamePanel.WIDTH;
		lastScreenHeight = GamePanel.HEIGHT;
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
	 * Loads a map from a .txt flare map file
	 * @param path The location of the map file
	 */
	public void loadTiledMap(String path) {
		try {
			InputStream in = getClass().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			br.readLine();
			String delim = "width="; String[] tokens = br.readLine().split(delim);
			numCols = Integer.parseInt(tokens[1]);
			delim = "height="; tokens = br.readLine().split(delim);
			numRows = Integer.parseInt(tokens[1]);
			
			byte[][] map = new byte[numRows][numCols];

			for(int i = 0; i < 10; i++) br.readLine();
			
			String delims = ",";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					byte tile = Byte.parseByte((tokens[col]));
					if(tile != 0) tile--;
					map[row][col] = tile;
				}
			}
			
			setMap(map);
		} catch (Exception e) {
			System.err.println("Could't load the tile map");
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

		width = (numCols)* (tileSize * SCALE);
		height = (numRows)* (tileSize * SCALE);
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
				
				g.drawImage(tiles[map[row][col] / numColsAcross][map[row][col] % numColsAcross].getImage(),(int)Math.floor(colPx * scaledTileSize - adjustx), (int)Math.floor(rowPx * scaledTileSize - adjusty), (int)scaledTileSize, (int)scaledTileSize, null);

			}
		}
	}

	/**
	 * Draws the map to the player's position.
	 * @param x horizontal pixels away from the upper-left most tile
	 * @param y vertical pixels away form the upper-left most tile
	 */
	public void setCameraPosition(double x, double y) {
		if(x > GamePanel.WIDTH / 2 && x < width - (GamePanel.WIDTH / 2)) xOffset -= lastx - x;
		if(y > GamePanel.HEIGHT / 2 && y < height - (GamePanel.HEIGHT / 2))	yOffset -= lasty - y;
		
		numColsToDraw = (byte)(GamePanel.WIDTH / (tileSize * SCALE) + 2);
		numRowsToDraw = (byte)(GamePanel.HEIGHT / (tileSize * SCALE) + 2);
		
		lastx = x;
		lasty = y;

		if(lastScreenWidth != GamePanel.WIDTH) {
			if(x > GamePanel.WIDTH / 2 && x < width - (GamePanel.WIDTH / 2)) xOffset = xOffset - (GamePanel.WIDTH - lastScreenWidth) / 2; 
			lastScreenWidth = GamePanel.WIDTH;
		}
		
		if(lastScreenHeight != GamePanel.HEIGHT) {
			if(y > GamePanel.HEIGHT / 2 && y < height - (GamePanel.HEIGHT / 2)) yOffset = yOffset - (GamePanel.HEIGHT - lastScreenHeight) / 2;
			lastScreenHeight = GamePanel.HEIGHT;
		}
	}
	
	/** MAKE THIS BETTER !! TODO
	 * @param x The horizontal distance (in px) to the requested tile
	 * @param y The vertical distance (in px) to the request tile
	 * @return The tile type (Tile.NO_COLLISION, TIlE.COLLISION, etc) of the tile that the specified x,y is in
	 */
	public int getTileType(double x, double y) {
		if(getTileID(x, y) < 30) return 0;
		else return 1;
	}
	
	/**
	 * 
	 * @param x The horizontal distance (in px) to the requested tile
	 * @param y The vertical distance (in px) to the request tile
	 * @return The tile id (31, 4, 5, etc) of the tile that the specified x,y is in
	 */
	public int getTileID(double x, double y) {
		return map[(int)(y / scaledTileSize)][(int)(x / scaledTileSize)];
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
	
	/**
	 * @return Map width (px)
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return Map height (px)
	 */
	public int getHeight() {
		return height;
	}
	
}
