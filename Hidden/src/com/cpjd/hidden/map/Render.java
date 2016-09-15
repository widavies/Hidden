package com.cpjd.hidden.map;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/*
 * Renders a map based off the inputed array
 */
public class Render {
	
	private int tileSize; // Tilesize, in pixels
	
	private int xOffset, yOffset; // The player's location
	
	private TileImage[][] tiles;
	
	public Render(String[][] map, int tileSize) {
		this.tileSize = tileSize;
		tiles = new TileImage[map.length][map[0].length];
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				try {
					tiles[i][j] = getTileImage(map[i][j]);
					tiles[i][j].calibrateTriangles();
				} catch(Exception e) {
					System.err.println("Failed to load tile image.");
				}
			}
		}
	}
	 
	public void update() {
		
	}
	
	public void renderMap(Graphics2D g) {
		for(int col = 0; col < tiles.length; col++) {
			for(int row = 0; row < tiles[col].length; row++) {
				if(tiles[col][row].square != null) g.drawImage(tiles[col][row].square, col * tileSize - xOffset, row * tileSize - yOffset, null);
				else {
					g.drawImage(tiles[col][row].triangleOne, col * tileSize, row * tileSize, null);
					g.drawImage(tiles[col][row].triangleTwo, col * tileSize, row * tileSize, null);
				}
			}
		}
	}
	
	private TileImage getTileImage(String tileID) throws Exception {
		TileImage tileImage = new TileImage();
		
		String[] tokens = tileID.split("#");
		
		if(tokens[0].equals("0")) {
			tileImage.square = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+tokens[1]+".png"));
			return tileImage;
		} else {
			tileImage.triangleOne = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+tokens[1]+".png"));
			tileImage.triangleTwo = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+tokens[2]+".png"));
			tileImage.left = tokens[3].equals("l");
			return tileImage;
		}
	}
	
	private BufferedImage rotate(BufferedImage image, int degrees) {
		double rotationRequired = Math.toRadians (degrees);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		return op.filter(image, null);
	}
	
	private class TileImage {
		public BufferedImage square;
		
		public BufferedImage triangleOne;
		public BufferedImage triangleTwo;
		public boolean left;
		
		private void calibrateTriangles() {
			if(square != null) return;
			
			if(left) triangleTwo = rotate(triangleTwo, 90);
			else {
				triangleOne = rotate(triangleOne, 45);
				triangleTwo = rotate(triangleTwo, 135);
			}
		}
	}
}
