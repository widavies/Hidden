package com.cpjd.hide.ui;

import java.awt.Color;
import java.awt.Graphics2D;

public class Exit extends Fragment {
	
	FragmentButton okay, cancel;
	
	public Exit() {
		TOTAL_EXPAND = 100;
		
		okay = new FragmentButton("Exit");
		cancel = new FragmentButton("Cancel");
	}
	
	@Override
	public void update() {
		super.update();
		
		okay.update();
		cancel.update();
		
		if(okay.isClicked()) {
			System.exit(0);
		}
		
		if(cancel.isClicked()) {
			this.setExiting(true);
			cancel.setClicked(false);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

		if(!expanded) return;
		
		g.setColor(Color.BLACK);
		g.drawString("Exit game?", buttonx + 5, buttony - expand + 50 - PADDING);
		
		okay.draw(g, buttonx + 5, buttony - expand + 200 - PADDING);
		cancel.draw(g, buttonx + 5, buttony - expand + 250 - PADDING);
	}
	
	@Override
	public void mousePressed(int x, int y) {
		super.mousePressed(x, y);
		
		okay.mousePressed(x, y);
		cancel.mousePressed(x, y);
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		
		okay.mouseMoved(x, y);
		cancel.mouseMoved(x, y);
	}
	
	
}
