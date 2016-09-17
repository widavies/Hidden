package com.cpjd.hidden.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class FragmentButton extends Button {
	
	public FragmentButton(String text) {
		super(text, null);
	}
	
	@Override
	public void draw(Graphics2D g, int x, int y) {
		FontMetrics fm = g.getFontMetrics();

		height = fm.getHeight();
		width = fm.stringWidth(text);
		this.x = x;
		this.y = y;
		
		if(fragment != null) fragment.draw(g);

		if(hover) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y - height + PADDING, width + (TOTAL_WIDTH - width) , height);
		}
	 
		g.setColor(Color.BLACK);
		if(hover) g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}
	
}
