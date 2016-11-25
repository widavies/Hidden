package com.cpjd.hidden.ui.hud;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.items.Inventory;
import com.cpjd.hidden.items.Item;
import com.cpjd.hidden.items.Items;
import com.cpjd.tools.Layout;

/**
 * The HUD is a graphical implementation of the Inventory class
 * @author Will Davies
 *
 */
public class HUD {
	
	private Inventory inv;
	private boolean open;
	
	// Dimensions
	private int width, height, itemSize;
	private boolean hoverT1, hoverT2;
	private boolean clickedT1, clickedT2;
	private Player player;
	
	// Player drawing
	private int playerx, playery;
	private int degrees;
	
	// Inventory drawing
	private int mousex, mousey;
	private int[][] bulge;
	private int[] hotbarBulge;
	private int[][] clothBulge;
	
	// If an item has been picked up
	private Item hand;
	
	public HUD(GameSave gameSave) {
		inv = new Inventory(gameSave);
		bulge = new int[inv.getHeight(Inventory.INV)][inv.getWidth(Inventory.INV)];
		hotbarBulge = new int[inv.getWidth(Inventory.HOTBAR)];
		clothBulge = new int[inv.getHeight(Inventory.CLOTH)][inv.getWidth(Inventory.CLOTH)];
		
		clickedT1 = true;
	}
	
	public void update() {
		width = (int)((Layout.WIDTH / 3.5) - (Layout.WIDTH / 3.5 % inv.getWidth(Inventory.INV)));
		itemSize = width / inv.getWidth(Inventory.INV);
		height = itemSize * inv.getHeight(Inventory.INV);
		
		if(clickedT2) {
			playerx = Layout.getObjectCenter(Layout.centerw(width), Layout.centerw(width) + (int)(itemSize * 1.5), player.getWidth());
			playery = Layout.getObjectCenter(Layout.centerh(height), Layout.centerh(height) + itemSize * 2, player.getHeight());
			
			for (int col = 0; col < inv.getWidth(Inventory.CLOTH); col++) {
				for (int row = 0; row < inv.getHeight(Inventory.CLOTH); row++) {
					if(clothContains(col, row)) {
						clothBulge[row][col] += 2;
						if(clothBulge[row][col] >= 15) clothBulge[row][col] = 15;
					} else {
						clothBulge[row][col] -= 2;
						if(clothBulge[row][col] < 0) clothBulge[row][col] = 0;
					}
				}
			}
		}
		
		if(clickedT1) {
			for (int col = 0; col < inv.getWidth(Inventory.INV); col++) {
				for (int row = 0; row < inv.getHeight(Inventory.INV); row++) {
					if(invContains(col, row)) {
						bulge[row][col] += 2;
						if(bulge[row][col] >= 15) bulge[row][col] = 15;
					} else {
						bulge[row][col] -= 2;
						if(bulge[row][col] < 0) bulge[row][col] = 0;
					}
				}
			}
		}
		
		for(int col = 0; col < inv.getWidth(Inventory.HOTBAR); col++) {
			if(hotbarContains(col)) {
				hotbarBulge[col] += 2;
				if(hotbarBulge[col] >= 15) hotbarBulge[col] = 15;
			} else {
				hotbarBulge[col] -= 2;
				if(hotbarBulge[col] < 0) hotbarBulge[col] = 0; 
			}
		}
	}
	
	public void draw(Graphics2D g) {
		if(!open) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.89f));
			drawHotbar(g);
			return;
		} else {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f));
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, Layout.WIDTH, Layout.HEIGHT);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.89f));
			drawHotbar(g);

		}

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.89f));
		
		if(clickedT1) drawInv(g);
		if(clickedT2) drawPlayer(g);	
		drawTabs(g);
		
		if(hand != null) {
			g.drawImage(hand.getIcon(), mousex, mousey, null);
		}
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1f));
		
	}
	
	private void drawPlayer(Graphics2D g) {
		g.setFont(GameStateManager.font.deriveFont((float)(Layout.WIDTH * 0.012229 + 0.0204)));
		
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(width), Layout.centerh(height), width, height);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(Layout.centerw(width), Layout.centerh(height), (int)(itemSize * 1.5), (int)(itemSize * 2));
		
		g.setColor(Color.BLACK);
		g.drawRect(Layout.centerw(width), Layout.centerh(height), (int)(itemSize * 1.5), (int)(itemSize * 2));
		g.drawString("Daniel Peterson", Layout.centerw(width) + 5, Layout.centerh(height) + (int)(itemSize * 2) + Layout.getStringHeight(g));
		g.drawString("$1,000,000 / $10,000,000", Layout.centerw(width) + 5, Layout.centerh(height) + (int)(itemSize * 2) + Layout.getStringHeight(g) * 2);
		g.drawString("Level 22", Layout.centerw(width) + 5, Layout.centerh(height) + (int)(itemSize * 2) + Layout.getStringHeight(g) * 3);
		g.drawString("Ulimate scrub", Layout.centerw(width) + 5, Layout.centerh(height) + (int)(itemSize * 2) + Layout.getStringHeight(g) * 4);
		g.drawString("Stats", Layout.centerw(width) + 5, Layout.centerh(height) + (int)(itemSize * 2) + Layout.getStringHeight(g) * 5);
		g.drawString("100 / 200 prisons conquered", Layout.centerw(width) + 5, Layout.centerh(height) + (int)(itemSize * 2) + Layout.getStringHeight(g) * 6);
		
		if(player != null) player.draw(g, playerx, playery, degrees);
		
		for(int col = 0; col < inv.getWidth(Inventory.INV) + 1; col++) {
			if(col == 0 || col >= 3) g.drawLine(Layout.centerw(width) + col * itemSize, Layout.centerh(height), Layout.centerw(width) + col * itemSize, Layout.centerh(height) + height);
			for(int row = 0; row < inv.getHeight(Inventory.INV) + 1; row++) {
				if(row == 0 || row >= inv.getHeight(Inventory.INV)) g.drawLine(Layout.centerw(width), Layout.centerh(height) + row * itemSize, Layout.centerw(width) + width, Layout.centerh(height) + row * itemSize);
				g.drawLine((Layout.centerw(width) + 3 * itemSize), (Layout.centerh(height) + row * itemSize), Layout.centerw(width) + width, Layout.centerh(height) + row * itemSize);
			}
		}
		
		for(int col = 0; col < inv.getWidth(Inventory.CLOTH); col++) {
			for(int row = 0; row < inv.getHeight(Inventory.CLOTH); row++) {
				Item item = inv.getItem(col, row, Inventory.CLOTH);
				if(item == null) continue;
				g.drawImage(item.getIcon(), Layout.getObjectCenter(Layout.centerw(width) + (col + 3) * itemSize, (Layout.centerw(width) + (col + 3) * itemSize) + itemSize, 
						(int) (itemSize / 1.2))  - clothBulge[row][col] / 2, 
						Layout.getObjectCenter( Layout.centerh(height) + row * itemSize, (Layout.centerh(height) + row * itemSize) + itemSize, (int) (itemSize / 1.2)) - clothBulge[row][col] / 2,
						(int) (itemSize / 1.2) + clothBulge[row][col], (int) (itemSize / 1.2) + clothBulge[row][col], null);
			}
		}
	}
	
	private void drawHotbar(Graphics2D g) {
		g.setFont(GameStateManager.font.deriveFont((float)(Layout.WIDTH * 0.012229 + 0.0204)));
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(width - itemSize), Layout.HEIGHT - itemSize, width - itemSize, itemSize);
		
		g.setColor(Color.BLACK);
		for(int col = 0; col < inv.getWidth(Inventory.HOTBAR) + 1; col++) {
			if(col < inv.getWidth(Inventory.HOTBAR)) g.drawString(String.valueOf(col + 1), Layout.centerw(width - itemSize) + col * itemSize + 3, Layout.HEIGHT - itemSize + (int)(Layout.getStringHeight(g) / 1.2));
			g.drawLine(Layout.centerw(width - itemSize) + col * itemSize, Layout.HEIGHT - itemSize, Layout.centerw(width - itemSize) + col * itemSize, Layout.HEIGHT);
			g.drawLine(Layout.centerw(width - itemSize), Layout.HEIGHT - itemSize, Layout.centerw(width - itemSize) + width - itemSize, Layout.HEIGHT - itemSize);
			g.drawLine(Layout.centerw(width - itemSize), Layout.HEIGHT - 1, Layout.centerw(width - itemSize) + width - itemSize, Layout.HEIGHT - 1);
		}
		
		for(int col = 0; col < inv.getWidth(Inventory.HOTBAR); col++) {
				Item item = inv.getItem(col, -1, Inventory.HOTBAR);
				if(item == null) continue;
				g.drawImage(item.getIcon(), Layout.getObjectCenter(Layout.centerw(width - itemSize) + col * itemSize, (Layout.centerw(width - itemSize) + col * itemSize) + itemSize, 
						(int) (itemSize / 1.2))  - hotbarBulge[col] / 2, 
						Layout.getObjectCenter( Layout.HEIGHT - itemSize, ( Layout.HEIGHT - itemSize) + itemSize, (int) (itemSize / 1.2)) - hotbarBulge[col] / 2,
						(int) (itemSize / 1.2) + hotbarBulge[col], (int) (itemSize / 1.2) + hotbarBulge[col], null);
		}
	}
	
	private void drawInv(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(width), Layout.centerh(height), width, height);
		
		g.setColor(Color.BLACK);
		for(int col = 0; col < inv.getWidth(Inventory.INV) + 1; col++) {
			g.drawLine(Layout.centerw(width) + col * itemSize, Layout.centerh(height), Layout.centerw(width) + col * itemSize, Layout.centerh(height) + height);
			for(int row = 0; row < inv.getHeight(Inventory.INV) + 1; row++) {
				g.drawLine(Layout.centerw(width), Layout.centerh(height) + row * itemSize, Layout.centerw(width) + width, Layout.centerh(height) + row * itemSize);
			}
		}

		for(int col = 0; col < inv.getWidth(Inventory.INV); col++) {
			for(int row = 0; row < inv.getHeight(Inventory.INV); row++) {
				Item item = inv.getItem(col, row, Inventory.INV);
				if(item == null) continue;
				g.drawImage(item.getIcon(), Layout.getObjectCenter(Layout.centerw(width) + col * itemSize, (Layout.centerw(width) + col * itemSize) + itemSize, (int) (itemSize / 1.2))  - bulge[row][col] / 2, 
						Layout.getObjectCenter(Layout.centerh(height) + row * itemSize, (Layout.centerh(height) + row * itemSize) + itemSize, (int) (itemSize / 1.2)) - bulge[row][col] / 2,
						(int) (itemSize / 1.2) + bulge[row][col], (int) (itemSize / 1.2) + bulge[row][col], null);
			}
		}
	}
	
	private void drawTabs(Graphics2D g) {
		g.setColor(Color.WHITE);
		if(hoverT1 || clickedT1) g.setColor(Color.BLACK);
		g.fillRect(Layout.centerw(width), Layout.centerh(height) - width / 15, (int)(itemSize * 1.5), width / 15);
		g.setColor(Color.WHITE);
		if(hoverT2 || clickedT2) g.setColor(Color.BLACK);
		g.fillRect((int)(Layout.centerw(width) + itemSize * 1.5), Layout.centerh(height) - width / 15, (int)(itemSize * 1.5), width / 15);
		g.setColor(Color.BLACK);
		
		g.drawLine(Layout.centerw(width), Layout.centerh(height) - width / 15, (int)(Layout.centerw(width) + itemSize * 3) - 1, Layout.centerh(height) - width / 15);
		g.drawLine(Layout.centerw(width),Layout.centerh(height) - width / 15, Layout.centerw(width), Layout.centerh(height));
		g.drawLine(Layout.centerw(width) + (int)(itemSize * 1.5) ,Layout.centerh(height) - width / 15, Layout.centerw(width) + (int)(itemSize * 1.5),Layout.centerh(height));
		g.drawLine(Layout.centerw(width) + (int)(itemSize * 3) - 1 ,Layout.centerh(height) - width / 15, Layout.centerw(width) + (int)(itemSize * 3) - 1,Layout.centerh(height));
		
		g.setFont(GameStateManager.font.deriveFont((float)(Layout.WIDTH * 0.015729 + 0.0204)));
		if(hoverT1 || clickedT1) g.setColor(Color.WHITE);
		g.drawString("Inventory", Layout.centerw(width) + 5,(int)( Layout.centerh(height) - width / 15 + Layout.getStringHeight(g) / 1.25));
		g.setColor(Color.BLACK);
		if(hoverT2 || clickedT2) g.setColor(Color.WHITE);
		g.drawString("Player", Layout.centerw(width) + (int)(itemSize * 1.5) + 5,(int)( Layout.centerh(height) - width / 15 + Layout.getStringHeight(g) / 1.25));
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_E) open = !open;
	}
	public void keyReleased(int k) {}
	public void mouseMoved(int x, int y) {
		Rectangle r = new Rectangle(Layout.centerw(width), Layout.centerh(height) - width / 15, (int)(itemSize * 1.5), width / 15);
		if(r.contains(x, y)) hoverT1 = true;
		else hoverT1 = false;
		
		Rectangle r2 = new Rectangle((int)(Layout.centerw(width) + itemSize * 1.5), Layout.centerh(height) - width / 15, (int)(itemSize * 1.5), width / 15);
		if(r2.contains(x, y)) hoverT2 = true;
		else hoverT2 = false;
		
		r = null;
		r2 = null;
		
		degrees = getAngle(playerx, playery, x, y) + 90;
		
		mousex = x;
		mousey = y;
	}
	
	public void mousePressed(int x, int y) {
		Rectangle r = new Rectangle(Layout.centerw(width), Layout.centerh(height) - width / 15, (int)(itemSize * 1.5), width / 15);
		if(r.contains(x, y)) {
			clickedT1 = true;
			clickedT2 = false;
		}
		
		Rectangle r2 = new Rectangle((int)(Layout.centerw(width) + itemSize * 1.5), Layout.centerh(height) - width / 15, (int)(itemSize * 1.5), width / 15);
		if(r2.contains(x, y)) {
			clickedT2 = true;
			clickedT1 = false;
		}
		
		r = null;
		r2 = null;
		
		if(!open) return;
		boolean found = false;
		if(clickedT1) {
			for (int col = 0; col < inv.getWidth(Inventory.INV); col++) {
				for (int row = 0; row < inv.getHeight(Inventory.INV); row++) {
					if(invContains(col, row)) {
						if(hand != null) hand = inv.putItem(hand, col, row, Inventory.INV);
						else if(hand == null) hand = inv.retrieveItem(col, row, Inventory.INV);
						found = true;
					}
				}
			}
		} else if(clickedT2) {
			for (int col = 0; col < inv.getWidth(Inventory.CLOTH); col++) {
				for (int row = 0; row < inv.getHeight(Inventory.CLOTH); row++) {
					if(clothContains(col, row)) {
						if(hand != null) hand = inv.putItem(hand, col, row, Inventory.CLOTH);
						else if(hand == null) hand = inv.retrieveItem(col, row, Inventory.CLOTH);
						found = true;
					}
				}
			}
		}
		
		for(int col = 0; col < inv.getWidth(Inventory.HOTBAR); col++) {
			if(hotbarContains(col)) {
				if(hand != null) hand = inv.putItem(hand, col, -1, Inventory.HOTBAR);
				else if(hand == null) hand = inv.retrieveItem(col, -1, Inventory.HOTBAR);
				found = true;
			}
		}
		//if(!found) hand = null; // drop item
	}	
	public void mouseWheelMoved(int k) {}
	
	public void save() {
		inv.saveChanges();
	}
	public int getAngle(int targetx, int targety, int actx, int acty) {
	    int angle = (int) Math.toDegrees(Math.atan2(targety - acty, targetx - actx));

	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;
	}
	public boolean isOpen() {
		return open;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	private boolean invContains(int col, int row) {
		return mousex >= Layout.centerw(width) + col * itemSize
				&& mousex <= (Layout.centerw(width) + col * itemSize) + itemSize
				&& mousey >= Layout.centerh(height) + row * itemSize
				&& mousey <= (Layout.centerh(height) + row * itemSize) + itemSize;
	}
	private boolean hotbarContains(int col) {
		return mousex >= Layout.centerw(width - itemSize) + col * itemSize
				&& mousex <= (Layout.centerw(width - itemSize) + col * itemSize) + itemSize
				&& mousey >= Layout.HEIGHT - itemSize
				&& mousey <= Layout.HEIGHT;
	}
	private boolean clothContains(int col, int row) {
		return mousex >= Layout.centerw(width) + (col + 3) * itemSize
				&& mousex <= (Layout.centerw(width) + (col + 3) * itemSize) + itemSize
				&& mousey >= Layout.centerh(height) + row * itemSize
				&& mousey <= (Layout.centerh(height) + row * itemSize) + itemSize; 
	}
	public void addItem(Item item) {
		inv.addItem(item);
	}
	public Items getItems() {
		return inv.getItems();
	}
	public void clearInventory() {
		inv.clearInventory();
	}
}