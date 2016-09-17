package com.cpjd.hidden.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.hidden.toolbox.Layout;

public class Play extends Fragment {
	
	private final int BUTTONS_PER_ROW = 10;
	
	ArrayList<LevelButton> levelButtons = new ArrayList<LevelButton>();
	
	private int row;
	
	public Play() {
		TOTAL_EXPAND = Layout.HEIGHT / 3;
		
		for(int i = 0; i < 50; i++) {
			levelButtons.add(new LevelButton(String.valueOf(i + 1), this));
			
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		for(int i = 0; i < levelButtons.size(); i++) {
			if(i < BUTTONS_PER_ROW) levelButtons.get(i).draw(g, buttonx + 5 * i * 14+ 1, 120);
			if(i > BUTTONS_PER_ROW) levelButtons.get(i).draw(g, (buttonx + 5 * i * 14 + 1) / 2, 240);
		}
		
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		
		for(int i = 0; i < levelButtons.size(); i++) {
			levelButtons.get(i).mouseMoved(x, y);
		}
	}
	
	
}
