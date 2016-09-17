package com.cpjd.hidden.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.hidden.toolbox.Layout;

public class Play extends Fragment {
	
	ArrayList<LevelButton> levelButtons = new ArrayList<LevelButton>();
	
	private int centeredx;
	
	public Play() {
		TOTAL_EXPAND = Layout.HEIGHT / 3;
		
		for(int i = 0; i < 50; i++) {
			levelButtons.add(new LevelButton(String.valueOf(i + 1), this, true));
			levelButtons.get(i).setIndex(i);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if(exit) return;
		
		g.setColor(Color.BLACK);
		g.drawString("Select chapter:", buttonx + 15, buttony - expand + 50);
		
		for(int i = 0; i < levelButtons.size(); i++) {
			levelButtons.get(i).draw(g, getButtonX(i), getButtonY(i));
		}
		
	}
	
	@Override
	public void update() {
		centeredx = Layout.getObjectCenter(buttonx, buttonWidth + expand, (getButtonX(9) + 134) - getButtonX(0));
		
		if(exit) {
			for(int i = 0; i < levelButtons.size(); i++) {
				levelButtons.get(i).reset(i);
			}
			return;
		}
		
		for(int i = 0; i < levelButtons.size(); i++) {
			if(expanded) levelButtons.get(i).update();
		}
	}
	
	private int getButtonX(int index) {
		return (index % 10) * 70 + buttonx + centeredx;
	}
	
	private int getButtonY(int index) {
		return (index / 10) * 100 + buttony - expand + 100;
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		
		for(int i = 0; i < levelButtons.size(); i++) {
			levelButtons.get(i).mouseMoved(x, y);
		}
	}
}
