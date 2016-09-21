package com.cpjd.hidden.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.cpjd.hidden.main.GamePanel;

public class HUD {
	
	// Player stats
	private double health; // Health, as a percent
	
	private AlphaComposite composite;
	private AlphaComposite defaultComposite;
	
	public HUD() {
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f);
		defaultComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	}
	
	public void update() {
		health = .82;
	}
	
	public void setStats(double health, double maxHealth) {
		this.health = health / maxHealth;
	}
	
	public void draw(Graphics2D g) {
		g.setComposite(composite);
		
		/* Draw player stats */
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 250, 200);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 250, 200);
		// Health
		g.setColor(Color.GRAY);
		g.fillRect(40, 10, 200, 30);
		g.setColor(Color.BLACK);
		g.fillRect(40, 10, (int)((240 - 40) * health), 30);
		// Health
		g.setColor(Color.GRAY);
		g.fillRect(40, 50, 200, 30);
		g.setColor(Color.BLACK);
		g.fillRect(40, 50, (int) ((240 - 40) * .2), 30);
		// Health
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
		
		// Draw active inventory slot
		g.setColor(Color.WHITE);
		g.fillRect(GamePanel.WIDTH - 64, GamePanel.HEIGHT - 64, 64, 64);
		g.setComposite(defaultComposite);
	}
	
}
