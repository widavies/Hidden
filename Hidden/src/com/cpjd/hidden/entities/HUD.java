package com.cpjd.hidden.entities;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cpjd.hidden.effects.Effect;
import com.cpjd.hidden.effects.Effect.EffectInfo;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.toolbox.Layout;

public class HUD {

	// Player stats
	private double health; // Health, as a percent

	// Inventory
	private int selected;

	// Effects
	private ArrayList<EffectInfo> effects;
	
	// Technical
	private AlphaComposite composite;
	private AlphaComposite defaultComposite;
	private boolean inv;

	public HUD() {
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f);
		defaultComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		
		effects = new ArrayList<EffectInfo>();
		
		effects.add(new EffectInfo(Effect.POSION));
		effects.add(new EffectInfo(Effect.POSION));
		effects.add(new EffectInfo(Effect.POSION));
		effects.add(new EffectInfo(Effect.POSION));
		
	}

	public void setStats(double health, double maxHealth) {
		this.health = health / maxHealth;
	}

	public void addEffect(EffectInfo effectInfo) {
		if(!updateEffect(effectInfo)) effects.add(effectInfo);
	}
	
	public void removeEffect(EffectInfo effectInfo) {
		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i).type == effectInfo.type) {
				effects.remove(i);
				return;
			}
		}
	}
	
	// Returns if the effect was successfully updated
	public boolean updateEffect(EffectInfo effectInfo) {
		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i).type == effectInfo.type) {
				effects.set(i, effectInfo);
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		health = .82;
	}


	public void draw(Graphics2D g) {
		g.setComposite(composite);

		drawStats(g);
		if(inv) drawInventory(g);
		drawHotbar(g);
		drawEffects(g);
		
		g.setComposite(defaultComposite);
	}

	private void drawInventory(Graphics2D g) {
		// Player stats
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(450) - 200, Layout.centerh(450), 200, 300);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Layout.centerw(450) - 200, Layout.centerh(450), 200 - 64, 175);
		for (int i = 0; i < 4; i++) {
			g.setColor(Color.BLACK);
			g.drawRect(Layout.centerw(450) - 64, Layout.centerh(450) + (i * 64), 64, 64);
		}

		// Inventory
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(450), Layout.centerh(450), 450, 450);
		g.setColor(Color.BLACK);
		g.drawRect(Layout.centerw(450), Layout.centerh(450), 450, 450);
		g.drawRect(Layout.centerw(450) - 200, Layout.centerh(450), 200, 300);
		// Inventory slots
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				g.setColor(Color.BLACK);
				g.drawRect(Layout.centerw(450) + (i * 90), Layout.centerh(450) + (j * 90), 90, 90);
			}
		}
	}

	private void drawHotbar(Graphics2D g) {
		for (int i = 0; i < 4; i++) {
			Font font = g.getFont();
			font = font.deriveFont(20f);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.fillRect(Layout.centerw(256) + (i * 64), GamePanel.HEIGHT - 64, 64, 64);
			g.setColor(Color.BLACK);
			if(i == selected) {
				g.setStroke(new BasicStroke(6));
				g.drawRect(Layout.centerw(256) + (i * 64 + 2), GamePanel.HEIGHT - 62, 60, 60);
			} else {
				g.setStroke(new BasicStroke(1));
				g.drawRect(Layout.centerw(256) + (i * 64), GamePanel.HEIGHT - 64, 64, 64);
			}

			g.drawString("" + (i + 1), Layout.centerw(256) + (i * 64) + 7, GamePanel.HEIGHT - 64 + 20);
			g.setStroke(new BasicStroke(1));
		}
	}
	
	private void drawEffects(Graphics2D g) {
		for(int i = 0; i < effects.size(); i++) {
			g.setColor(Color.WHITE);
			g.fillRect(GamePanel.WIDTH - ((i + 1) * 100), 10, 90, 90);
			g.setColor(Color.BLACK);
			g.drawRect(GamePanel.WIDTH - ((i + 1) * 100), 10, 90, 90);
			g.setColor(Color.BLACK);
			double percent = effects.get(i).duration / effects.get(i).maxDuration;
			g.fillRect(GamePanel.WIDTH - ((i + 1) * 100) + 1, (int)(10 + (90 - (percent * 90))) + 1, 89, (int)(percent * 90));
		}
	}
	
	private void drawStats(Graphics2D g) {
		/* Draw player stats */
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 250, 200);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 250, 200);
		// Health
		g.setColor(Color.GRAY);
		g.fillRect(40, 10, 200, 30);
		g.setColor(Color.BLACK);
		g.fillRect(40, 10, (int) ((240 - 40) * health), 30);
		// Food
		g.setColor(Color.GRAY);
		g.fillRect(40, 50, 200, 30);
		g.setColor(Color.BLACK);
		g.fillRect(40, 50, (int) ((240 - 40) * .2), 30);
		// Timer
		g.setColor(Color.GRAY);
		g.fillRect(40, 90, 200, 30);
		g.setColor(Color.BLACK);
		Font font = g.getFont();
		font = font.deriveFont(27f);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.BLACK);
		g.fillRect(40, 90, (int) ((240 - 40) * .75), 30);
		g.setColor(Color.WHITE);
		g.drawString("42 seconds", 45, 83 + fm.getHeight());
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_1) selected = 0;
		if(k == KeyEvent.VK_2) selected = 1;
		if(k == KeyEvent.VK_3) selected = 2;
		if(k == KeyEvent.VK_4) selected = 3;

		if(k == KeyEvent.VK_E) inv = !inv;
	}
}
