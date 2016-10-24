package com.cpjd.hidden.map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.toolbox.MathTools;
import com.cpjd.hidden.toolbox.pathfind.Node;

public class TileMap {
	
	public static int tileSize;
	
	private double x;
	private double y;
	
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax; 
	
	private double tween;
	
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
		tween = 0.07;

	}
	
	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[5][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
				tiles[0][col] = new Tile(subimage,Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage,Tile.BLOCKED);
				subimage = tileset.getSubimage(col * tileSize, tileSize * 2, tileSize, tileSize);
				tiles[2][col] = new Tile(subimage,Tile.FATAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
				tiles[3][col] = new Tile(subimage,Tile.ITEM);
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
	public void setMap(int[][] generatedMap,int tileWidth,int tileHeight) {
		map = generatedMap;
		
		numRows = tileHeight;
		numCols = tileWidth;
		height = numRows * tileSize;
		width = numCols * tileSize;
		
		xmin = (GamePanel.WIDTH ) - width;
		xmax = 0;
		ymin = (GamePanel.HEIGHT ) - height;
		ymax = 0;
		
		numRowsToDraw = GamePanel.HEIGHT  * 4 / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH * 4 / tileSize + 2;
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
	public void setTween(double tween) {
		this.tween = tween;
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
		this.x += (x - this.x - GamePanel.WIDTH / 12) * tween;
		this.y += (y - this.y) * tween;

		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
	}
	
	private void fixBounds() {
		xmin = GamePanel.WIDTH - width * 2;
		ymin = GamePanel.HEIGHT - height * 2;
		numRowsToDraw = GamePanel.HEIGHT  * 4 / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH * 4 / tileSize + 2;
		
		if(x < xmin) x = xmin;
		if(x > xmax) x = xmax;
		if(y < ymin) y = ymin;
		if(y > ymax) y = ymax;
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
				g.drawImage(tiles[r][c].getImage(),(int)x + col * tileSize,(int)y + row * tileSize,null);
				} catch(Exception e) {}
			}
		}		
	}
	
	public List<Point> pathfind(double startX, double startY, double endX, double endY){
		
		int blockStartX = (int) (startX / tileSize);
		int blockStartY = (int) (startY / tileSize);
		
		int blockEndX = (int) (endX / tileSize);
		int blockEndY = (int) (endY / tileSize);
		
		List<Point> closedSet = new LinkedList<Point>();
		List<Point> openSet = new LinkedList<Point>();
		
		openSet.add(new Point(blockStartX, blockStartY));
		
		Node[][] nodes = new Node[getNumRows()][getNumCols()];
		
		for(int x = 0; x < nodes.length; x++){
			for(int y = 0; y < nodes[0].length; y++){
				nodes[x][y] = new Node();
			}
		}
		
		nodes[blockEndX][blockEndY].parent = null;
		
		while(!openSet.isEmpty()){
			
			
			int currentX = openSet.get(0).x;
			int currentY = openSet.get(0).y;
			
			if(currentX == blockEndX && currentY == blockEndY){
				break;
			}
			
			closedSet.add(openSet.get(0));
			openSet.remove(0);
			
			for (int x=-1;x<2;x++) {
				for (int y=-1;y<2;y++) {
					
					//ignore this block if...
					
					//if it is the same as currentX, Y
					if (x == 0 && y == 0) {
						continue;
					}
					
					
					int xp = x + currentX;
					int yp = y + currentY;
					
					if(xp > getNumCols() || yp > getNumRows() || xp < 0 || yp < 0)
						continue;
					
					int type = getType(yp, xp);
					
					if (type != Tile.BLOCKED && type != Tile.FATAL) {
						int nextStepCost = nodes[currentX][currentY].cost + MathTools.getMoveCost(currentX, currentY, xp, yp);
						
						Node neighbor = nodes[xp][yp];
						Point neighborPoint = new Point(xp, yp);

						if (nextStepCost < neighbor.cost) {
							
							if (openSet.contains(neighborPoint)) {
								openSet.remove(openSet.indexOf(neighborPoint));
							}
							if (closedSet.contains(neighborPoint)) {
								closedSet.remove(closedSet.indexOf(neighborPoint));
							}
						}

						if (!openSet.contains(neighborPoint) && !closedSet.contains(neighborPoint)) {
							neighbor.cost = nextStepCost;
							neighbor.heuristic = Math.abs(xp - blockEndX) + Math.abs(yp - blockEndY);
							neighbor.parent = new Point(currentX, currentY);
							openSet.add(neighborPoint);
						}
					}
				}
			}
		}
		
		if(nodes[blockEndX][blockEndY].parent == null){
			System.err.println("No Path Found");
			return null;
		}
		
		List<Point> path = new LinkedList<Point>();
		Point target = new Point(blockEndX, blockEndY);
		Point start = new Point(blockStartX, blockStartY);
		
		while (target != start && target != null) {
			path.add(0, new Point(target.x, target.y));
			target = nodes[target.x][target.y].parent;
		}
		//path.add(0, start);
		path.remove(0);//to remove starting tile from list

		return path;
	}
}
