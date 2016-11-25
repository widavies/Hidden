package com.cpjd.hidden.items;

import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.files.IO;

/**
 * Manages the inventory for Hidden. It's a 5 x 5  grid. The bottom left-most square isn't used.
 * The left-most column is for armor items only. The bottom 4 tiles are the hotbar.
 * @author Will Davies
 *
 */
public class Inventory {
	
	/**
	 * The inventory id
	 */
	public static final int INV = 0;
	/**
	 * Hotbar id
	 */
	public static final int HOTBAR = 1;
	/**
	 * Clothing id
	 */
	public static final int CLOTH = 2;
	
	private Item[][] inventory; // Note: position 4,0 is not used
	private Item[] hotbar;
	private Item[][] clothing;
	private GameSave gameSave;
	private Items items;
	
	public Inventory(GameSave gameSave) {
		this.gameSave = gameSave;
		
		if(gameSave != null) inventory = gameSave.getInventory();
		if(gameSave != null) hotbar = gameSave.getHotbar();
		if(gameSave != null) clothing = gameSave.getClothing();
		
		if(inventory == null) inventory = new Item[5][5];
		if(hotbar == null) hotbar = new Item[4];
		if(clothing == null) clothing = new Item[5][2];
		
		items = new Items();
		
		addItem(items.getItem(0));
		addItem(items.getItem(0));
		addItem(items.getItem(0));
		addItem(items.getItem(0));
		addItem(items.getItem(0));
		addItem(items.getItem(0));
	}
	
	/**
	 * Puts an item in the specified spot, if an items is there, removes it and returns it.
	 * @param item
	 * @param x
	 * @param y
	 * @return
	 */
	public Item putItem(Item item, int x, int y, int which) {
		Item temp = null;
		if(which == INV) {
			temp = inventory[y][x];
			inventory[y][x] = item;
		} else if(which == HOTBAR) {
			temp = hotbar[x];
			hotbar[x] = item;
		} else if(which == CLOTH) {
			temp = clothing[y][x];
			clothing[y][x] = item;
		}
		return temp;
	}
	
	/**
	 * Adds an item to the inventory (excluding armor) if there's a spot available. 
	 * @param item The item to be added
	 * @return <b>true</b> if a spot was available<br><b>false</b> if the inventory is full
	 */
	public boolean addItem(Item item) {
		for(int i = 0; i < hotbar.length; i++) {
			if(hotbar[i] == null) {
				hotbar[i] = item;
				return true;
			}
		}
		
		for(int i = 0; i < inventory[0].length; i++) {
			for(int j = 0; j < inventory.length; j++) {
				if(inventory[i][j] == null) {
					inventory[i][j] = item;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the specified item at the coordinate location
	 * @param x
	 * @param y
	 * @return
	 */
	public Item getItem(int x, int y, int which) {
		if(which == INV) return inventory[y][x];
		if(which == HOTBAR) return hotbar[x];
		if(which == CLOTH) return clothing[y][x];
		return null;
	}
	

	/**
	 * Removes the item from the inventory and returns it, if there indeed was an item there
	 * @param x
	 * @param y
	 * @return
	 */
	public Item retrieveItem(int x, int y, int which) {
		Item item = null;
		if(which == INV) {
			item = inventory[y][x];
			inventory[y][x] = null;
		} else if(which == HOTBAR) {
			item = hotbar[x];
			hotbar[x] = null;
		} else if(which == CLOTH){
			item = clothing[y][x];
			clothing[y][x] = null;
		}
		return item;
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
		
		for(int i = 0; i < hotbar.length; i++) {
			hotbar[i] = null;
		}
		
		for(int i = 0; i < clothing[0].length; i++) {
			for(int j = 0; j < clothing.length; j++) {
				clothing[i][j] = null;
			}
		}
	}
	
	public int getWidth(int which) {
		if(which == INV) return inventory.length;
		if(which == HOTBAR) return hotbar.length;
		if(which == CLOTH) return 2;
		return 0;
	}
	
	public int getHeight(int which) {
		if(which == INV) return inventory[0].length;
		if(which == HOTBAR) return 1;
		if(which == CLOTH) return 5;
		return 0;
	}
	
	public Items getItems() {
		return items;
	}
	
	/**
	 * Saves the inventory to the file system.
	 */
	public void saveChanges() {
		gameSave = IO.deserializeGameSave();
		gameSave.setInventory(inventory);
		gameSave.setHotbar(hotbar);
		gameSave.setClothing(clothing);
		IO.serializeGameSave(gameSave);
	}
}