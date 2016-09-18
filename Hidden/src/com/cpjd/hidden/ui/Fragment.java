package com.cpjd.hidden.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.hidden.gamestate.GameStateManager;

public abstract class Fragment {
	
	protected final int PADDING = 10;
	
	public static boolean ANY_EXPANDED = false;
	
	protected int TOTAL_EXPAND;
	
	protected int expand;
	protected boolean expanded;
	protected boolean hover;
	protected boolean exit;
	
	protected int buttonx, buttony, buttonWidth, buttonHeight;
	
	protected GameStateManager gsm;
	
	public Fragment(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public void setButtonSize(int x, int y, int width, int height) {
		this.buttonx = x; this.buttony = y; this.buttonWidth = width; this.buttonHeight = height;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(buttonx, buttony - expand, buttonWidth + expand, buttonHeight + (expand * 2));
		
		if(expanded) {
			g.setColor(Color.RED);
			if(hover) g.setColor(Color.BLACK);
			g.fillRoundRect(buttonx + buttonWidth + expand - 50, buttony - expand + 10, 40, 40, 8, 8);
			g.setColor(Color.BLACK);
			if(hover) g.setColor(Color.RED);
			g.fillRoundRect(buttonx + buttonWidth + expand - 45, buttony - expand + 15, 30, 30, 8, 8);
		}
	}
	
	public void update() {
		
	}
	
	public void expand() {
		if(expanded) return;
		
		ANY_EXPANDED = true;
		
		if(expand < TOTAL_EXPAND) expand+=12; 
		else expanded = true;
	}
	
	public void close() {
		if(!expanded) return;
		
		if(expand > 0) expand-=12;
		else expanded = false;
		
		ANY_EXPANDED = false;
	}
	
	public boolean isExpanded() {
		return expanded;
	}
	
	public boolean isExiting() {
		return exit;
	}
	
	public void setExiting(boolean exit) {
		this.exit = exit;
	}
	
	public void mouseMoved(int x, int y) {
		Rectangle bounds = new Rectangle(buttonx + buttonWidth + expand - 50, buttony - expand + 10, 40, 40);
		if(bounds.contains(x, y)) hover = true;
		else hover = false;
	}
	
	public void mousePressed(int x, int y) {
		Rectangle bounds = new Rectangle(buttonx + buttonWidth + expand - 50, buttony - expand + 10, 40, 40);
		if(bounds.contains(x, y)) exit = true;
	}
	
}
