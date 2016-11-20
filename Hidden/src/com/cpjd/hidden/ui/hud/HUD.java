package com.cpjd.hidden.ui.hud;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.items.Inventory;
import com.cpjd.tools.Layout;

public class HUD {
	
	private Inventory inv;
	private boolean open;
	
	// Dimensions
	private int width, height, sizeToFollow;
	
	public HUD(GameSave gameSave) {
		inv = new Inventory(gameSave);
	}
	
	public void update() {
		width = Layout.WIDTH / 3 - (Layout.WIDTH / 3 % (inv.getWidth() + 1));
		sizeToFollow = width / (inv.getWidth() + 1);
		height = sizeToFollow * (inv.getHeight() + 1);
		
	}
	public void draw(Graphics2D g) {
		if(!open) return;
		
		g.setColor(Color.WHITE);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.79f));
		
		g.fillRect(Layout.centerw(width), Layout.centerh(height), width, height - sizeToFollow);
		g.fillRect(Layout.centerw(width), Layout.HEIGHT - sizeToFollow, width, height);
		g.fillRect(Layout.centerw(width) - sizeToFollow * 2, Layout.centerh(height), sizeToFollow, height - sizeToFollow);
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1f));
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_E) open = !open;
	}
	public void keyReleased(int k) {}
	public void mouseMoved(int x, int y) {}
	public void mousePressed(int x, int y) {}	
	public void mouseWheelMoved(int k) {}
	
	public void save() {
		inv.saveChanges();
	}
}