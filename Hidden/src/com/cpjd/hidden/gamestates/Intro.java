package com.cpjd.hidden.gamestates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.hidden.gamestate.GameState;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.sound.SoundKeys;
import com.cpjd.hidden.sound.SoundLoader;
import com.cpjd.hidden.sound.SoundRequest;
import com.cpjd.hidden.toolbox.Console;
import com.cpjd.tools.Layout;
import com.sun.glass.events.KeyEvent;

public class Intro extends GameState implements Runnable {

	private Thread thread;
	private SoundLoader soundLoader;
	private long ticks, seconds;
	
	private BufferedImage logo;
	
	private final String[] name = {"Cats ", "Pajamas ", "Developers"};
	private float[] alphas;
	private int xpos, xcenter;
	private int ext;
	private double ext2;
	
	public Intro(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		alphas = new float[3];
		ext = -100;
		
		try {
			logo = ImageIO.read(getClass().getResourceAsStream("/cpjd/logo.png"));
		} catch(Exception e) {}
		
		
		thread = new Thread(this);
		thread.start();
	}
	
	// All initial game assets that need to be ready when the menu is active should be placed here
	public void run() {
		String[] sounds = {SoundKeys.MENU_HOVER};
		String[] music = {SoundKeys.CREDITS_MUSIC};
		soundLoader = new SoundLoader(new SoundRequest(sounds, music));
		soundLoader.load();
		
		try {
			thread.join();
		} catch(Exception e) {}
	}
	
	@Override
	public void update() {
		ticks++;
		seconds = ticks / GamePanel.FPS;
		if(seconds < 1) {
			alphas[0]+=0.01f;
			if(alphas[0] > 1f) alphas[0] = 1f;
		}
		else if(seconds == 1) {
			alphas[1]+=0.01f;
			if(alphas[1] > 1f) alphas[1] = 1f;
		}
		else if(seconds == 2) {
			alphas[2]+=0.01f;
			if(alphas[2] > 1f) alphas[2] = 1f;
		} 
		else if(ext < GamePanel.HEIGHT / 2 + logo.getHeight() / 8) ext+=3;
		else if(ext2 >= 17 && soundLoader.isFinishedLoading() && seconds >= 6) gsm.setState(GameStateManager.MENU);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.BLACK);
		for(int i = 0; i < name.length; i++) {
			xcenter = Layout.centerString(name[0] + name[1] + name[2], g);
			if(i == 0) xpos = xcenter;
			if(i == 1) xpos = xcenter + Layout.getStringWidth(g, name[0]);
			if(i == 2) xpos = xcenter + Layout.getStringWidth(g, name[0]) + Layout.getStringWidth(g, name[1]);
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphas[i]));
			if(ext >= GamePanel.HEIGHT / 2 + logo.getHeight() / 8) {
				ext2+=0.2;
				if(ext2 > 17) ext2 = 17;
			}
			g.drawString(name[i], xpos, (int)(Layout.centerStringVert(g) + ext2));
		}
		
		g.drawImage(logo, Layout.centerw(logo.getWidth()), ext - logo.getHeight() - Layout.getStringHeight(g) * 2, null);
		
		if(soundLoader.isFinishedLoading()) {
			g.setColor(Color.BLACK);
			g.setFont(GameStateManager.font.deriveFont(24f));
			g.drawString("Press SPACE to skip", 5, Layout.getStringHeight(g));
		}
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_SPACE && soundLoader.isFinishedLoading()) gsm.setState(GameStateManager.MENU);
	}

	@Override
	public void keyReleased(int k) {}
	@Override
	public void mousePressed(int x, int y) {}
	@Override
	public void mouseReleased(int x, int y) {}
	@Override
	public void mouseMoved(int x, int y) {}
	@Override
	public void mouseWheelMoved(int k) {}
}
