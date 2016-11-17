package com.cpjd.hidden.items;

import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.files.IO;
import com.cpjd.hidden.items.Items.Item;

/**
 * Manages the inventory for Hidden. It's a 5 x 5  grid. The bottom left-most square isn't used.
 * The left-most column is for armor items only. The bottom 4 tiles are the hotbar.
 * @author Will Davies
 *
 */
public class Inventory {
	
	private Item[][] inventory; // Note: position 4,0 is not used
	private static final int[][] INVENTORY_TYPES = { // -1 for anything, or an Item.type for a type restriction, -2 for not used
			{Item.ARMOR, -1, -1, -1, -1},
			{Item.ARMOR, -1, -1, -1, -1},
			{Item.ARMOR, -1, -1, -1, -1},
			{Item.ARMOR, -1, -1, -1, -1},
			{-2, -1, -1, -1, -1}
	};
	private GameSave gameSave;
	
	public Inventory(GameSave gameSave) {
		this.gameSave = gameSave;
		
		inventory = gameSave.getInventory();
		
		if(inventory == null) inventory = new Item[5][5];
	}
	
	/**
	 * Transfers a item's location within the inventory. If an item exists in the destination location, it will be automatically switched to the old item's starting spot.
	 * @param startx the item's original x position
	 * @param starty the item's original y position
	 * @param destx the destination x position
	 * @param desty the destination y position
	 * @return <b>true</b> if the move was successful
	 * <br><b>false</b> if the move would result in items being in incompatible inventory slots
	 */
	public boolean moveItem(int startx, int starty, int destx, int desty) {
		if(!validPosition(inventory[starty][startx], destx, desty) || !validPosition(inventory[destx][desty], startx, starty)) return false;
		
		Item dest = inventory[desty][destx];
		inventory[desty][destx] = inventory[starty][startx];
		inventory[starty][startx] = dest;
		
		return true;
	}
	
	/**
	 * Adds an item to the inventory (excluding armor). 
	 * @param item The item to be added
	 * @return <b>true</b> if a spot was available<br><b>false</b> if the inventory is full
	 */
	public boolean addItem(Item item) {
		for(int i = 0; i < inventory[0].length; i++) {
			for(int j = 1; j < inventory.length; j++) {
				if(inventory[i][j] == null) {
					inventory[i][j] = item;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes all items from the inventory.
	 */
	public void clearInventory() {
		for(int i = 0; i < inventory[0].length; i++) {
			for(int j = 0; j < inventory.length; j++) {
				inventory[i][j] = null;
			}
		}
	}
	
	public int getWidth() {
		return inventory.length;
	}
	
	public int getHeight() {
		return inventory[0].length;
	}
	
	/**
	 * Saves the inventory to the file system.
	 */
	public void saveChanges() {
		gameSave = IO.deserializeGameSave();
		gameSave.setInventory(inventory);
		IO.serializeGameSave(gameSave);
	}
	
	/**
	 * Verifies that the specified item is permitted to reside in the specified inventory slot.
	 * @param item
	 * @param x The target location, x
	 * @param y The target location, y
	 * @return
	 */
	private boolean validPosition(Item item, int x, int y) {
		if(item == null) return true;
		else if(INVENTORY_TYPES[y][x] == -1) return true;
		else if(INVENTORY_TYPES[y][x] == item.getType()) return true;
		return false;
	}
}