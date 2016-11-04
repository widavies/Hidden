package com.cpjd.hidden.items;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

// Stores the current players inventory
public class Inventory {
	
	public Item[][] inventory = new Item[5][6]; // The inventory, including the hotbar

	public Inventory() {
		TEMP_VAR = 0;
		
		for(int i = 0; i < 4; i++) {
			inventory[i][0] = new Item();
		}
		
	}
	
	public void modifyPosition(int fromCol, int fromRow, int toCol, int toRow, Item item) {
		if(inventory[toCol][toRow] != null) {
			inventory[fromCol][fromRow] = inventory[toCol][toRow];
			inventory[toCol][toRow] = null;
		}
		inventory[toCol][toRow] = item;
	}
	
	public void addItem(int col, int row, Item item) {
		inventory[col][row] = item;
	}
	
	public void removeItem(int col, int row) {
		inventory[col][row] = null;
	}
	
	public Item getItem(int col, int row) {
		return inventory[col][row];
	}
	
	public static int TEMP_VAR = 0;
	
	public static class Item {
		public int id;
		public BufferedImage image;
		public String name;
		public String tooltip;
		
		public Item() {
			name = "Hidden item";
			tooltip = "Use this item to do fun stuff \nOh yeah\nhit points 10000";
			
			try {
				if(TEMP_VAR == 0) image = ImageIO.read(getClass().getResourceAsStream("/items/1.png"));
				if(TEMP_VAR == 1) image = ImageIO.read(getClass().getResourceAsStream("/items/2.png"));
				if(TEMP_VAR == 2) image = ImageIO.read(getClass().getResourceAsStream("/items/3.png"));
				if(TEMP_VAR == 3) image = ImageIO.read(getClass().getResourceAsStream("/items/4.png"));
				TEMP_VAR++;
				
			} catch(Exception e) {
				System.err.println("Failed to load item icon image");
			}
		}
	}
	
}
