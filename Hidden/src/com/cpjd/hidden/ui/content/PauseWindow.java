package com.cpjd.hidden.ui.content;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.ui.elements.UIButton;
import com.cpjd.hidden.ui.windows.UIWindow;
import com.cpjd.tools.Layout;

public class PauseWindow extends UIWindow {
	
	private GameStateManager gsm;
	private OptionsWindow optionsWindow;
	
	public PauseWindow(GameStateManager gsm) {
		super();
		
		this.gsm = gsm;
		
		animationSpeed = Layout.HEIGHT / 80;
		
		addButton("Resume", 5, Layout.alignPoints(20, 0, (int) (Layout.HEIGHT / 4.5)),(int)(Layout.WIDTH / 4) - 10, true);
		addButton("Options", 5, Layout.alignPoints(55, 0, (int) (Layout.HEIGHT / 4.5)), (int)(Layout.WIDTH / 4) - 10, true);
		addButton("Exit to Menu", 5, Layout.alignPoints(90, 0, (int) (Layout.HEIGHT / 4.5)), (int)(Layout.WIDTH / 4) - 10, true);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
		g.fillRect(x + ext, y + ext, width - 2*ext, height - 2*ext);
		g.setColor(Color.WHITE);
		g.drawString("P A U S E D", Layout.centerString("P A U S E D", g), Layout.aligny(15));
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g);
		}
		
		if(optionsWindow != null) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			optionsWindow.draw(g);
		}
	}
	
	@Override
	public void update() {
		super.update();
	
		for(int i = 0; i < buttons.size(); i++) {
			if(ext <= 0) buttons.get(i).setVisible(true);
		}
		
		if(optionsWindow != null) optionsWindow.update();
	}
	
	@Override
	public void buttonPressed(UIButton b) {
		if(optionsWindow != null) return;
		
		if(b.getText().equals("Resume")) {
			for(int i = 0; i < buttons.size(); i++) {
				buttons.get(i).setVisible(false);
			}
			closing = true;
		}
		if(b.getText().equals("Exit to Menu")) {
			for(int i = 0; i < buttons.size(); i++) {
				buttons.get(i).setVisible(false);
			}
			closing = true;
			gsm.setState(GameStateManager.MENU);
		}
		if(b.getText().equals("Options")) {
			optionsWindow = new OptionsWindow();
			optionsWindow.center((int)(Layout.WIDTH / 1.5), (int)(Layout.HEIGHT / 1.5));
			optionsWindow.addUIListener(this);
			optionsWindow.setAnimationSpeed(10);
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).mouseMoved(x, y);
		}
		if(optionsWindow != null) optionsWindow.mouseMoved(x, y);
	}
	@Override
	public void mousePressed(int x, int y) {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).mousePressed(x, y);
		}
		if(optionsWindow != null) optionsWindow.mousePressed(x, y);
	}
	@Override
	public void viewClosed(UIWindow window) {
		optionsWindow = null;
		buttons.clear();
		addButton("Resume", 5, Layout.alignPoints(20, 0, (int) (Layout.HEIGHT / 4.5)),(int)(Layout.WIDTH / 4) - 10, true);
		addButton("Options", 5, Layout.alignPoints(55, 0, (int) (Layout.HEIGHT / 4.5)), (int)(Layout.WIDTH / 4) - 10, true);
		addButton("Exit to Menu", 5, Layout.alignPoints(90, 0, (int) (Layout.HEIGHT / 4.5)), (int)(Layout.WIDTH / 4) - 10, true);
	}
}
