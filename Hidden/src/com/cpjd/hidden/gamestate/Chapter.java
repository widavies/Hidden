package com.cpjd.hidden.gamestate;

import java.awt.Graphics2D;

import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.saves.SaveProfile;

public class Chapter extends GameState {

	private TileMap tileMap;
	private SaveProfile save;
	
	public Chapter(GameStateManager gsm) {
		super(gsm);
		
		tileMap = new TileMap(32);
		tileMap.setTween(0.07);
	}
	
	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		
		
	}

	@Override
	public void keyReleased(int k) {
		
		
	}

	@Override
	public void mousePressed(int x, int y) {
		
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		
		
	}

}
