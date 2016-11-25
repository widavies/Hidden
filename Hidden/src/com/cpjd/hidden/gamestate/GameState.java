package com.cpjd.hidden.gamestate;

import java.awt.Graphics2D;

import com.cpjd.hidden.toolbox.Console;
import com.cpjd.hidden.ui.hud.HUD;

public abstract class GameState {

	protected GameStateManager gsm;
	protected Console console;
	protected HUD hud;
	
	public GameState(GameStateManager gsm, Console console) {
		this.gsm = gsm;
		this.console = console;
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mousePressed(int x, int y);
	public abstract void mouseReleased(int x, int y);
	public abstract void mouseMoved(int x, int y);
	public abstract void mouseWheelMoved(int k);
}
