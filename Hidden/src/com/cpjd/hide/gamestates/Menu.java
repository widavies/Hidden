package com.cpjd.hide.gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.hide.gamestate.GameState;
import com.cpjd.hide.gamestate.GameStateManager;
import com.cpjd.hide.toolbox.Layout;
import com.cpjd.hide.ui.Button;
import com.cpjd.hide.ui.Credits;
import com.cpjd.hide.ui.Exit;
import com.cpjd.hide.ui.Fragment;
import com.cpjd.hide.ui.Play;
import com.cpjd.hide.ui.Settings;

public class Menu extends GameState {

	ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Menu(GameStateManager gsm) {
		super(gsm);
		
		buttons.add(new Button("Play", new Play()));
		buttons.add(new Button("Settings", new Settings()));
		buttons.add(new Button("Credits", new Credits()));
		buttons.add(new Button("Exit", new Exit()));
	}
	
	public void update() {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).update();
		}
	}
	public void draw(Graphics2D g) {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g, Layout.alignx(5), Layout.aligny((i + 6) * 7));
		}
		
		if(Fragment.ANY_EXPANDED) return;
		
		Font font = g.getFont();
		font = font.deriveFont(font.getSize() * 10);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("HIDDEN", Layout.alignx(5), Layout.aligny(20));
		
	}
	public void keyPressed(int k) {}
	public void keyReleased(int k) {}
	public void mousePressed(int x, int y) {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).mousePressed(x, y);
		}
	}
	public void mouseReleased(int x, int y) {}
	public void mouseMoved(int x, int y) {
		for(int i = 0; i < buttons.size();i++) {
			buttons.get(i).mouseMoved(x, y);
		}
	}

}
