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
import com.cpjd.hidden.items.Items;
import com.cpjd.hidden.items.Items.Item;
import com.cpjd.tools.Layout;

public class HUD {
	
	private Inventory inv;
	private boolean open;
	
	// Dimensions
	private int width, height, itemSize;
	private boolean hoverT1, hoverT2;
	private boolean clickedT1, clickedT2;
	private Player player;
	
	// Fancy grahpics
	private int playerx, playery;
	private int degrees;
	
	private Items items;
	
	public HUD(GameSave gameSave, Items items) {
		inv = new Inventory(gameSave);
		
		this.items = items;
		
		clickedT1 = true;
	}
	
	public void update() {
		width = (int)((Layout.WIDTH / 3.5) - (Layout.WIDTH / 3.5 % inv.getWidth()));
		itemSize = width / inv.getWidth();
		height = itemSize * inv.getHeight();
		
		playerx = Layout.getObjectCenter(Layout.centerw(width), Layout.centerw(width) + (int)(itemSize * 1.5), player.getWidth());
		playery = Layout.getObjectCenter(Layout.centerh(height), Layout.centerh(height) + itemSize * 2, player.getHeight());
	}
	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.89f));
		
		drawHotbar(g);
		
		if(!open) return;
		if(clickedT1) drawInv(g);
		if(clickedT2) drawPlayer(g);	
		drawTabs(g);
		
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
		
		for(int col = 0; col < inv.getWidth() + 1; col++) {
			if(col == 0 || col >= 3) g.drawLine(Layout.centerw(width) + col * itemSize, Layout.centerh(height), Layout.centerw(width) + col * itemSize, Layout.centerh(height) + height);
			for(int row = 0; row < inv.getHeight() + 1; row++) {
				if(row == 0 || row >= inv.getHeight()) g.drawLine(Layout.centerw(width), Layout.centerh(height) + row * itemSize, Layout.centerw(width) + width, Layout.centerh(height) + row * itemSize);
				g.drawLine(Layout.centerw(width) + 3 * itemSize, Layout.centerh(height) + row * itemSize, Layout.centerw(width) + width, Layout.centerh(height) + row * itemSize);
			}
		}
	}
	
	private void drawHotbar(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(width - itemSize), Layout.HEIGHT - itemSize, width - itemSize, itemSize);
		
		g.setColor(Color.BLACK);
		for(int col = 0; col < inv.getWidth(); col++) {
			g.drawLine(Layout.centerw(width - itemSize) + col * itemSize, Layout.HEIGHT - itemSize, Layout.centerw(width - itemSize) + col * itemSize, Layout.HEIGHT);
			g.drawLine(Layout.centerw(width - itemSize), Layout.HEIGHT - itemSize, Layout.centerw(width - itemSize) + width - itemSize, Layout.HEIGHT - itemSize);
			g.drawLine(Layout.centerw(width - itemSize), Layout.HEIGHT - 1, Layout.centerw(width - itemSize) + width - itemSize, Layout.HEIGHT - 1);
		}
	}
	
	private void drawInv(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(width), Layout.centerh(height), width, height);
		
		g.setColor(Color.BLACK);
		for(int col = 0; col < inv.getWidth() + 1; col++) {
			g.drawLine(Layout.centerw(width) + col * itemSize, Layout.centerh(height), Layout.centerw(width) + col * itemSize, Layout.centerh(height) + height);
			for(int row = 0; row < inv.getHeight() + 1; row++) {
				g.drawLine(Layout.centerw(width), Layout.centerh(height) + row * itemSize, Layout.centerw(width) + width, Layout.centerh(height) + row * itemSize);
			}
		}

		for(int col = 0; col < inv.getWidth(); col++) {
			for(int row = 0; row < inv.getHeight(); row++) {
				Item item = inv.getItem(col, row);
				if(item == null) continue;
				g.drawImage(item.getIcon(), Layout.getObjectCenter(Layout.centerw(width) + col * itemSize, (Layout.centerw(width) + col * itemSize) + itemSize, (int) (itemSize / 1.2)), 
						Layout.getObjectCenter(Layout.centerh(height) + row * itemSize, (Layout.centerh(height) + row * itemSize) + itemSize, (int) (itemSize / 1.2)),
						(int) (itemSize / 1.2), (int) (itemSize / 1.2), null);
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
		
		// Calculate degrees position
		// First, get the distance from the mouse to the player draw location
		degrees = getAngle(playerx, playery, x, y) + 90;
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
		
		inv.addItem(items.getItem(0));
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
}