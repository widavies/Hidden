package com.cpjd.hidden.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.ui.hud.HUD;
import com.cpjd.tools.Layout;

public class Chapter extends GameState {

	protected TileMap tileMap;

	protected Player player;
	protected List<Enemy> enemies;
	private HUD hud;
	
	private Rectangle winBox = new Rectangle(19 * 64 - 10, 21 * 64 - 10, 64 + 10, 64 + 10);
	
	// Scaling gameplay drawing
	public static final int SCALE = 4;
	private Graphics2D gameGraphics;
	private BufferedImage gameImage;
	
	public Chapter(GameStateManager gsm) {
		super(gsm);
		
		tileMap = new TileMap(16);
		tileMap.setTween(0.07);
		
		hud = new HUD();
		
		gameImage = new BufferedImage(Layout.WIDTH, Layout.HEIGHT, BufferedImage.TYPE_INT_RGB);
		gameGraphics = (Graphics2D) gameImage.getGraphics();
	}
	
	@Override
	public void update() {
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
		tileMap.setPosition((GamePanel.WIDTH / 2) - player.getX(), (GamePanel.HEIGHT / 2) - player.getY());

		
		player.setMapPosition();
		
		for(int i = 0; i < enemies.size(); i++){
			//enemies.get(i).update(player.getX(), player.getY());
			//enemies.get(i).setMapPosition();
		}
		hud.update();
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		gameGraphics.setColor(Color.BLACK);
		gameGraphics.fillRect(0, 0, gameImage.getWidth(), gameImage.getHeight());
		
		tileMap.draw(gameGraphics);
		
		player.draw(gameGraphics);
		
		for(int i = 0; i < enemies.size(); i++){
			//enemies.get(i).drawSightArc(g);
		}
		for(int i = 0; i < enemies.size(); i++){
			//enemies.get(i).draw(g);
		}

		if(enemies.size() > 0 && enemies.get(0) != null)
			//enemies.get(0).drawOverlays(g, player.getX(), player.getY());
		
		if(!gsm.isPaused()) hud.draw(g);
		
		g.drawImage(gameImage, 0, 0, gameImage.getWidth() * SCALE, gameImage.getHeight() * SCALE, null);

		
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
		hud.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
		
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