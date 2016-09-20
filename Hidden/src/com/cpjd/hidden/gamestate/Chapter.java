package com.cpjd.hidden.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.TileMap;

public class Chapter extends GameState {

	protected TileMap tileMap;

	protected Player player;
	protected List<Enemy> enemies;
	
	public Chapter(GameStateManager gsm) {
		super(gsm);
		
		tileMap = new TileMap(64);
		tileMap.setTween(0.07);
	}
	
	@Override
	public void update() {
		player.update();
		tileMap.setPosition((GamePanel.WIDTH / 2) - player.getX(), (GamePanel.HEIGHT / 2) - player.getY());
		
		player.setMapPosition();
	}

	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
		player.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
		
		if(k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.INTRO);
		}
		
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
		
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
