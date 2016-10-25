package com.cpjd.hidden.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.OpenWorld;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.ui.hud.HUD;

public class Chapter extends GameState {

	protected TileMap tileMap;
	protected boolean finishedGen;
	
	protected Player player;
	protected List<Enemy> enemies;
	private HUD hud;
	
	private Rectangle winBox = new Rectangle(19 * 64 - 10, 21 * 64 - 10, 64 + 10, 64 + 10);
	
	protected OpenWorld world;

	public Chapter(GameStateManager gsm) {
		super(gsm);
		
		tileMap = new TileMap(16);
		tileMap.setTween(0.07);
		
		hud = new HUD();
	}
	
	@Override
	public void update() {
		if(player == null || !finishedGen) return;
		
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getCollisionBox().intersects(player.getCollisionBox())){
				gsm.setState(gsm.getState());
				System.out.println("you got caught");
			}
		}
		
		if(player.getCollisionBox().intersects(winBox)){
			System.out.println("you won");
			gsm.setState(gsm.getState());
		}
		
		hud.update();
		
		if(hud.isInventoryOpen()) return;
		player.update();
		tileMap.setPosition((GamePanel.WIDTH / 2) - player.getX() - (0.485 * GamePanel.WIDTH - 192), (GamePanel.HEIGHT / 2) - player.getY() - (0.489 * GamePanel.HEIGHT - 109));
		player.setMapPosition();
		
		for(int i = 0; i < enemies.size(); i++){
			//enemies.get(i).update(player.getX(), player.getY());
			//enemies.get(i).setMapPosition();
		}
		hud.update();
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(!finishedGen) return;
		
		tileMap.draw(g);
		
		player.draw(g);
		
		for(int i = 0; i < enemies.size(); i++){
			//enemies.get(i).drawSightArc(g);
		}
		for(int i = 0; i < enemies.size(); i++){
			//enemies.get(i).draw(g);
		}

		//if(enemies.size() > 0 && enemies.get(0) != null)
			//enemies.get(0).drawOverlays(g, player.getX(), player.getY());
	}
	@Override
	public void drawGUI(Graphics2D g) {
		if(!gsm.isPaused() && finishedGen) hud.draw(g);
	}
	@Override
	public void keyPressed(int k) {
		if(player != null) player.keyPressed(k);
		hud.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		if(player != null) player.keyReleased(k);
		
	}
	
	@Override
	public void mousePressed(int x, int y) {
		hud.mousePressed(x, y);
	}

	@Override
	public void mouseReleased(int x, int y) {
		
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		hud.mouseMoved(x, y);
	}
	
	@Override
	public void mouseWheelMoved(int k) {
		hud.mouseWheelMoved(k);
	}

	public void sendSightMessage(double x, double y, int messageRange, Enemy sender) {
		
		int senderIndex = enemies.indexOf(sender);
		
		double distance;
		double changeX;
		double changeY;
		
		for(int i = 0; i < enemies.size(); i++){
			
			if(i == senderIndex) continue;
			
			changeX = enemies.get(i).getX() - x;
			changeY = enemies.get(i).getY() - y;
			distance = changeX * changeX + changeY * changeY;
			
			
			if(distance <= messageRange * messageRange){
				enemies.get(i).recievePlayerLocationMessage(player.getX(), player.getY());
			}
		}
	}


}