package com.cpjd.hidden.ui.elements;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import com.cpjd.hidden.ui.View;

public class UICheckbox extends View {

	// Constants
	private static final int HOVER_WIDTH = 400; // The width of the hovering highlights
	public static final int WHITE_TEXT = 0;
	public static final int BLACK_TEXT = 1;
	
	// Attributes
	private String text;

	// Technical
	private boolean hover;
	private Point original;
	private boolean visible;
	private boolean checked;
	
	public UICheckbox(String text) {
		super();
		
		this.text = text;
		
		visible = true;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void draw(Graphics2D g) {
		if(!visible) return;
		
		g.setFont(font.deriveFont(30f));
		metrics = g.getFontMetrics();
		g.setColor(Color.BLACK);
		Stroke def = g.getStroke();
		g.setStroke(new BasicStroke(5));
		if(!hover) g.drawRoundRect(x, y, 25, 25, 8, 8);
		if(hover || checked) g.fillRoundRect(x, y, 25, 25, 8, 8);
		g.setStroke(def);
		g.setColor(Color.BLACK);
		g.drawString(text, x + 35, y + (int)(metrics.getHeight() / 1.5));
		if(checked) {
			g.setColor(new Color(38, 79, 183));
			g.drawLine(x + 2, y + 2, x + 23, y + 23);
			g.drawLine(x + 2, y + 23, x + 23, y + 2);
		}
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isChecked() {
		return checked;
	}
	public void mouseMoved(int x, int y) {
		hover = intersects(x, y) && focus; 
	}
	public void setOriginalLocation(int x, int y) {
		original = new Point(x, y);
	}
	public int getOriginalX() {
		return original.x;
	}
	public int getOriginalY() {
		return original.y;
	}
	public void mousePressed(int x, int y) {
		if(intersects(x, y) && focus) {
			checked = !checked;
			listener.checkBoxPressed(this, checked);
		}
	}
	public String getText() {
		return text;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = HOVER_WIDTH;
		this.height = new Canvas().getFontMetrics(font).getHeight();
	}
	
	private boolean intersects(int mousex, int mousey) {
		return mousex >= x && mousex <= x + 25 && mousey >= y && mousey <= y + 25;
	}
}
