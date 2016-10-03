package com.cpjd.hidden.ui;

import java.awt.Font;
import java.awt.FontMetrics;

import com.cpjd.hidden.gamestate.GameStateManager;

public abstract class View {
	protected int x, y, width, height;
	protected UIListener listener;
	protected Font font;
	protected FontMetrics metrics;
	protected boolean focus;
	
	public View() {
		font = GameStateManager.font;
		focus = true;
	}
	
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	
	public boolean getFocus() {
		return focus;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.x = x; this.y = y; this.width = width; this.height = height;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setFontSize(float fontSize) {
		font = GameStateManager.font.deriveFont(fontSize);
	}
	
	public void addUIListener(UIListener listener) {
		this.listener = listener;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
