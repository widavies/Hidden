package com.cpjd.hidden.gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.toolbox.Layout;
import com.cpjd.tools.Usage;

public class Options {
	
	public static final int CLOSED = 0;
	public static final int OPENING = 1;
	public static final int CLOSING = 2;
	public static final int OPENED = 3;
	
	private int expand;
	private int mode;
	private boolean hover;
	private final String[] TAB_NAMES = { "General", "Sound", "Graphics", "Controls" };
	private boolean[] tab = new boolean[TAB_NAMES.length];
	private boolean[] tabHover = new boolean[TAB_NAMES.length];
	private int adjust;
	
	public Options() {
		tab[0] = true;
	}
	
	public void update() {
		if(mode == 1) {
			expand+=8;
			if(expand > 100) {
				expand = 100;
				mode = 3;
			}
		}
		if(mode == 2) {
			expand-=8;
			if(expand < -25) {
				expand = 0;
				mode = 0;
			}
		}
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		if(mode != 0) {
			Font font = g.getFont();
			font = font.deriveFont(27f);
			g.setFont(font);
			
			drawFoundation(g);
			drawTabs(g);
			drawGraphics(g);
		}
	}
	
	private void drawGraphics(Graphics2D g) {
		if(!tab[2]) return;
		Font font = g.getFont();
		font = font.deriveFont(22f);
		g.setFont(font);
		g.setColor(Color.DARK_GRAY);
		g.drawString("Brightness:", (Layout.centerw(GamePanel.WIDTH / 2) - expand) + 5, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 120);
	}
	
	private void drawTabs(Graphics2D g) {
		for(int i = 0; i < TAB_NAMES.length; i++) {
			if(tab[i] || tabHover[i]) g.setColor(new Color(24, 105, 133));
			else g.setColor(Color.DARK_GRAY);
			adjust = i * 160;
			if(i == 2) adjust -= 20;
			if(tab[i]) g.fillRect((Layout.centerw(GamePanel.WIDTH / 2) - expand) + 5 + adjust, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 80, Layout.getStringWidth(g, TAB_NAMES[i]), 5);
			g.drawString(TAB_NAMES[i], (Layout.centerw(GamePanel.WIDTH / 2) - expand) + 5 + adjust, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 75);
		}
	}
	
	private void drawFoundation(Graphics2D g) {
		g.fillRect(Layout.centerw(GamePanel.WIDTH / 2) - expand, Layout.centerh(GamePanel.HEIGHT / 2) - expand, GamePanel.WIDTH / 2 + expand * 2, GamePanel.HEIGHT / 2 + expand * 2);
		g.setColor(Color.DARK_GRAY);
		g.drawString("Options", Layout.centerw(GamePanel.WIDTH / 2) - expand + 5, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 30);
		
		g.setColor(Color.RED);
		if(hover) g.setColor(Color.BLACK);
		g.fillRoundRect((Layout.centerw(GamePanel.WIDTH / 2) - expand) + GamePanel.WIDTH / 2 + expand * 2 - 50, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 10, 40, 40, 8, 8);
		g.setColor(Color.BLACK);
		if(hover) g.setColor(Color.RED);
		g.fillRoundRect(Layout.centerw(GamePanel.WIDTH / 2) - expand + GamePanel.WIDTH / 2 - 45 + expand * 2, (Layout.centerh(GamePanel.HEIGHT / 2) - expand) + 15, 30, 30, 8, 8);
	}
	
	public void mouseMoved(int x, int y) {
		Rectangle bounds = new Rectangle((Layout.centerw(GamePanel.WIDTH / 2) - expand) + GamePanel.WIDTH / 2 + expand * 2 - 50, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 10, 40, 40);
		if(bounds.contains(x, y)) hover = true;
		else hover = false;
		
		for(int i = 0; i < TAB_NAMES.length; i++) {
			adjust = i * 160;
			if(i == 2) adjust -= 20;
			Rectangle col = new Rectangle((Layout.centerw(GamePanel.WIDTH / 2) - expand) + 5 + (adjust), Layout.centerh(GamePanel.HEIGHT / 2) - expand + 50, 100, 50);
			if(col.contains(x, y)) tabHover[i] = true;
			else tabHover[i] = false;		
		}
	}
	
	public void mousePressed(int x, int y) {
		Rectangle bounds = new Rectangle((Layout.centerw(GamePanel.WIDTH / 2) - expand) + GamePanel.WIDTH / 2 + expand * 2 - 50, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 10, 40, 40);
		if(bounds.contains(x, y) && mode == Options.OPENED) {
			mode = Options.CLOSING;
		}
		
		for(int i = 0; i < TAB_NAMES.length; i++) {
			adjust = i * 160;
			if(i == 2) adjust -= 20;
			Rectangle col = new Rectangle((Layout.centerw(GamePanel.WIDTH / 2) - expand) + 5 + adjust, Layout.centerh(GamePanel.HEIGHT / 2) - expand + 50, 100, 50);
			if(col.contains(x, y)) {
				for(int j = 0; j < TAB_NAMES.length; j++) {
					tab[j] = false;
				}
				tab[i] = true;
			}
		}
	}
	
}
