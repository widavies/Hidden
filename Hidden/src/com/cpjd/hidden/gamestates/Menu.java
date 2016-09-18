package com.cpjd.hidden.gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.hidden.gamestate.GameState;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.toolbox.Layout;
import com.cpjd.hidden.ui.Button;
import com.cpjd.hidden.ui.Credits;
import com.cpjd.hidden.ui.Exit;
import com.cpjd.hidden.ui.Fragment;
import com.cpjd.hidden.ui.Play;
import com.cpjd.hidden.ui.Settings;

public class Menu extends GameState {

	ArrayList<Button> buttons;
	
	public Menu(GameStateManager gsm) {
		super(gsm);
		buttons = new ArrayList<Button>();
		Fragment.ANY_EXPANDED = false;
		buttons.add(new Button("Play", new Play(gsm)));
		buttons.add(new Button("Settings", new Settings(gsm)));
		buttons.add(new Button("Credits", new Credits(gsm)));
		buttons.add(new Button("Exit", new Exit(gsm)));
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
