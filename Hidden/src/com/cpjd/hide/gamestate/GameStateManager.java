package com.cpjd.hide.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.InputStream;

import com.cpjd.hide.gamestates.Menu;
import com.cpjd.hide.main.GamePanel;

public class GameStateManager {
	
	public static final int NUM_GAME_STATES = 6;
	public static final int INTRO = 0;

	private GameState[] gameStates;
	private int currentState;

	// The game-wide font
	private Font font;
	
	public GameStateManager() {
		gameStates = new GameState[NUM_GAME_STATES];

		try {
			InputStream inStream = getClass().getResourceAsStream("/fonts/USSR STENCIL WEBFONT.ttf");
			Font rawFont = Font.createFont(Font.TRUETYPE_FONT, inStream);
			font = rawFont.deriveFont(40.0f);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		currentState = INTRO;
		loadState(currentState);
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		if (state == INTRO) gameStates[state] = new Menu(this);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public int getState() {
		return currentState;
	}
	public void update() {
		if (gameStates[currentState] != null) gameStates[currentState].update();
	}

	public void draw(Graphics2D g) {
		// Enable anti-aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
		
		if (gameStates[currentState] != null) gameStates[currentState].draw(g);
	}

	public void keyPressed(int k) {
		if (gameStates[currentState] != null) gameStates[currentState].keyPressed(k);
	}

	public void keyReleased(int k) {
		if (gameStates[currentState] != null) gameStates[currentState].keyReleased(k);
	}
	public void mousePressed(int x, int y) {
		if(gameStates[currentState] != null) gameStates[currentState].mousePressed(x, y);
	}
	public  void mouseReleased(int x, int y) {
		if(gameStates[currentState] != null) gameStates[currentState].mouseReleased(x, y);
	}
	public  void mouseMoved(int x, int y) {
		if(gameStates[currentState] != null) gameStates[currentState].mouseMoved(x, y);
	}
}
