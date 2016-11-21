package com.cpjd.hidden.ui.hud;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.items.Inventory;
import com.cpjd.tools.Layout;

public class HUD {
	
	private Inventory inv;
	private boolean open;
	
	// Dimensions
	private int width, height, itemSize;
	private boolean hoverT1, hoverT2;
	private boolean clickedT1, clickedT2;
	
	public HUD(GameSave gameSave) {
		inv = new Inventory(gameSave);
		
		clickedT1 = true;
	}
	
	public void update() {
		width = (int)((Layout.WIDTH / 3.5) - (Layout.WIDTH / 3.5 % inv.getWidth()));
		itemSize = width / inv.getWidth();
		height = itemSize * inv.getHeight();
		
	}
	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.89f));
		
		drawHotbar(g);
		
		if(!open) return;
		drawInv(g);
		drawTabs(g);
				
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1f));
		
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
	}	
	public void mouseWheelMoved(int k) {}
	
	public void save() {
		inv.saveChanges();
	}
	
	public boolean isOpen() {
		return open;
	}
}