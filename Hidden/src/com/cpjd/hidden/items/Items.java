package com.cpjd.hidden.items;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Loads all the available items in the game
 * @author Will Davies
 *
 */
public class Items {

	private ArrayList<Item> items;
	
	/**
	 * Loads all the specified items into the system
	 */
	public Items() {
		items = new ArrayList<Item>();
		
		try {
			InputStream in = getClass().getResourceAsStream("/items-info/.items-info.properties");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = br.readLine();
			
			while(line != null) {
				loadItem(line);
				
				line = br.readLine();
				
			}
		} catch (Exception e) {
			System.err.println("Error reading .items-info.properties file. (Check existing / syntax)");
			e.printStackTrace();
		}
	}
	
	private void loadImage(Item item) {
		
	}
	
	/**
	 * Loads the specified item in and adds it to the items arraylist
	 * @param itemName The item's name (no extension)
	 */
	private void loadItem(String itemName) {
		try {
			InputStream in = getClass().getResourceAsStream("/items-info/"+itemName+".item");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String title = br.readLine();
			String tooltip = br.readLine();
			int type = Integer.parseInt(br.readLine());
			int damage = Integer.parseInt(br.readLine());
			BufferedImage icon = ImageIO.read(getClass().getResourceAsStream(br.readLine()));
			
			items.add(new Item(icon, title, tooltip, type, damage, 0));
			
			icon = null;
		} catch (Exception e) {
			System.err.println("Error loading item: Unproperly specified item information file.");
		}
	}
	
	/**
	 * Returns a fresh loaded copy of an item
	 * @param id The item's id.
	 * @return
	 */
	public Item getItem(int id) {
		return items.get(id);
	}
	
	/**
	 * Releases all the loaded items.
	 */
	public void release() {
		items.clear();
		items = null;
	}
}
