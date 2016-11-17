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
	
	public HUD(GameSave gameSave) {
		inv = new Inventory(gameSave);
	}
	
	public void update() {}
	public void draw(Graphics2D g) {
		if(!open) return;
		
		g.setColor(Color.WHITE);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.79f));
		
		g.fillRect(Layout.centerw(Layout.WIDTH / 3), Layout.centerh(Layout.HEIGHT / 2), Layout.WIDTH / 3, Layout.HEIGHT / 2);
		
		//Draw squares
		g.setColor(Color.BLACK);
		for(int row = 0; row < inv.getHeight(); row++) {
			g.drawLine(Layout.centerw(Layout.WIDTH / 3), row * (Layout.HEIGHT / 2 / inv.getHeight()), Layout.centerw(Layout.WIDTH / 3) + Layout.WIDTH / 3, row * (Layout.HEIGHT / 2 / inv.getHeight()) + Layout.HEIGHT / 2);
		}
		
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