package com.cpjd.hidden.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.cpjd.hidden.toolbox.Layout;

public class Credits extends Fragment {
	
	private int creditsProgress;
	
	/*
	 * Credits formatting tips
	 * add prefix '[l]' for large text 
	 */
	
	private final String[] credits = {
			"Hidden",
			"",
			"A Cats Pajamas Production",
			"",
			"Team",
			"Will D.",
			"",
			"Art resources:",
			""
			
	};
	
	public Credits() {
		TOTAL_EXPAND = Layout.HEIGHT / 3;
	}
	
	@Override
	public void update() {
		super.update();
		if(!expanded) creditsProgress = Layout.HEIGHT - 300;
		
		creditsProgress--;
		
		if((buttony - expand + credits.length * 40 + creditsProgress) < 0) setExiting(true);
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if(!expanded) return;
		g.setColor(Color.BLACK);
		
		for(int i = 0, j = 0; i < credits.length; i++, j += 40) {
			if((buttony - expand + j + creditsProgress) < 0) continue;
			
			if(i > 3) {
				Font font = g.getFont();
				font = font.deriveFont(30f);
				g.setFont(font);
			}
			
			g.drawString(credits[i], buttonx, buttony - expand + j + creditsProgress);
		}
	}
	
}
