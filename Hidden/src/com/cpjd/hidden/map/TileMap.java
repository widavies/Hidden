package com.cpjd.hidden.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.cpjd.hidden.main.GamePanel;
import com.cpjd.tools.Layout;

public class TileMap {
	
	public static int tileSize;
	
	private double x;
	private double y;
	
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax; 
	
	private int[][] map;
	
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		TileMap.tileSize =  tileSize;
		numRowsToDraw = (GamePanel.HEIGHT )  / tileSize + 2;
		numColsToDraw = (GamePanel.WIDTH ) / tileSize + 2;

	}
	
	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[5][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
				tiles[0][col] = new Tile(subimage,(short)Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage,(short)Tile.BLOCKED);
				subimage = tileset.getSubimage(col * tileSize, tileSize * 2, tileSize, tileSize);
				tiles[2][col] = new Tile(subimage,(short)Tile.FATAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
				tiles[3][col] = new Tile(subimage,(short)Tile.ITEM);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadTiledMap(String s) {
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			br.readLine();
			String delim = "width="; String[] tokens = br.readLine().split(delim);
			numCols = Integer.parseInt(tokens[1]);
			delim = "height="; tokens = br.readLine().split(delim);
			numRows = Integer.parseInt(tokens[1]);
			
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize; 

			for(int i = 0; i < 10; i++) {
				br.readLine();
			}
			
			xmin = (GamePanel.WIDTH ) - width;
			xmax = 0;
			ymin = (GamePanel.HEIGHT ) - height;
			ymax = 0;
			
			String delims = ",";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					int tile = Integer.parseInt(tokens[col]);
					if(tile != 0) tile--;
					map[row][col] = tile;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void setMap(int[][] generatedMap) {
		map = generatedMap;
		
		numRows = generatedMap[0].length;
		numCols = generatedMap.length;
		height = numRows * tileSize;
		width = numCols * tileSize;
		
		xmin = (GamePanel.WIDTH ) - width;
		xmax = 0;
		ymin = (GamePanel.HEIGHT ) - height;
		ymax = 0;
		
		numRowsToDraw = Layout.HEIGHT / tileSize + 2;
		numColsToDraw = Layout.WIDTH / tileSize + 2;
	}
	public int getTileSize() {
		return tileSize;
	}
	public int[][] getMap() {
		return map;
	}
	public void setMap(int row, int col, int value) {
		map[row][col] = value;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumCols() {
		return numCols;
	}
	
	public int getType(int row, int col) {
		if(map == null) return 0;
		
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public int getID(int row, int col) {
		int rc = map[row][col];
		return rc;
	}
	
	public void setPosition(double x, double y) {
		this.x += (x - this.x);
		this.y += (y - this.y);

		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
	}
	
	private void fixBounds() {
		xmin = GamePanel.WIDTH - width;
		ymin = GamePanel.HEIGHT - height;
		numRowsToDraw = GamePanel.HEIGHT  * 4 / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH * 4 / tileSize + 2;
	}
	
	public void draw(Graphics2D g) {
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if(col >= numCols) break;
				try{
				
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross; 
				int c = rc % numTilesAcross;
				g.drawImage(tiles[r][c].getImage(),(int)x + col * tileSize * 4,(int)y + row * tileSize * 4,tileSize * 4, tileSize * 4,null);
				} catch(Exception e) {}
			}
		}		
	}
}
