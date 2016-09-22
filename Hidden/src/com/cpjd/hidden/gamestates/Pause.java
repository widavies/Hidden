package com.cpjd.hidden.gamestates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.cpjd.hidden.gamestate.GameState;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.ui.FragmentButton;

public class Pause extends GameState {

	private boolean open;
	
	// Technical
	private AlphaComposite composite;
	private AlphaComposite defaultComposite;
	private int xmov;
	
	// UI
	FragmentButton resume, options, exit;
	
	public Pause(GameStateManager gsm) {
		super(gsm);
		
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f);
		defaultComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		
		resume = new FragmentButton("Resume");
		options = new FragmentButton("Options");
		exit = new FragmentButton("Exit");
	}

	
	@Override
	public void update() {
		xmov += 8;
		if(xmov > 500) xmov = 500;
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(!open) return;
		g.setComposite(composite);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setComposite(defaultComposite);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, (GamePanel.HEIGHT / 2) - 250, xmov, 500);
		
		resume.draw(g, 10, (GamePanel.HEIGHT / 2) - 200);
		
		
	}

	public boolean isOpen() {
		return open;
	}
	
	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) open = !open;
		
		if(!open) xmov = 0;
		
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
		resume.mouseMoved(x, y);
		
	}

	@Override
	public void mouseWheelMoved(int k) {
		
		
	}
	
}
