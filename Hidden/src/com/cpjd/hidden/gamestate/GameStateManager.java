package com.cpjd.hidden.gamestate;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.InputStream;

import com.cpjd.hidden.chapters.World;
import com.cpjd.hidden.gamestates.Menu;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.toolbox.Console;
import com.cpjd.hidden.ui.UIListener;
import com.cpjd.hidden.ui.content.PauseWindow;
import com.cpjd.hidden.ui.elements.UIButton;
import com.cpjd.hidden.ui.elements.UICheckbox;
import com.cpjd.hidden.ui.windows.UIWindow;
import com.cpjd.tools.Layout;
import com.cpjd.tools.Usage;

public class GameStateManager implements UIListener {

	private Console console;
	private PauseWindow pauseWindow;
	
	public static final int NUM_GAME_STATES = 2;
	public static final int MENU = 0;
	public static final int CH1 = 1;

	private GameState[] gameStates;
	private int currentState;

	// The game-wide font
	public static Font font;
	public static int ticks = 0;
	
	public GameStateManager() {
		gameStates = new GameState[NUM_GAME_STATES];
		try {
			InputStream inStream = getClass().getResourceAsStream("/fonts/USSR STENCIL WEBFONT.ttf");
			Font rawFont = Font.createFont(Font.TRUETYPE_FONT, inStream);
			font = rawFont.deriveFont(40.0f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		currentState = MENU;
		loadState(currentState);

		console = new Console(this);
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	private void loadState(int state) {
		if(state == MENU) gameStates[state] = new Menu(this);
		if(state == CH1) gameStates[state] = new World(this);
	}
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	public int getState() {
		return currentState;
	}
	public void update() {
		console.update();
		ticks++;
		if(ticks % 120 == 0 && Console.showMemory) System.out.println(Usage.calcMemory());
		
		if(pauseWindow != null) {
			pauseWindow.update();
			return;
		}
		
		if(console.isOpen()) return;
		
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	public boolean isPaused() {
		return pauseWindow != null;
	}
	public void draw(Graphics2D g) {
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Layout.WIDTH, Layout.HEIGHT);
		
		if(pauseWindow != null) g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		if(pauseWindow != null) pauseWindow.draw(g);
		console.draw(g);
	}
	public void keyPressed(int k) {
		if(console.keyPressed(k)) return;
		
		if(k == KeyEvent.VK_ESCAPE && currentState > MENU && pauseWindow == null) {
			pauseWindow = new PauseWindow(this);
			pauseWindow.center((int)(Layout.WIDTH / 4), (int)(Layout.HEIGHT / 4.5));
			pauseWindow.addUIListener(this);
		}
		
		if(gameStates[currentState] != null) gameStates[currentState].keyPressed(k);
		
		if(k == KeyEvent.VK_F6) GamePanel.DEBUG = !GamePanel.DEBUG;
	}

	public void keyReleased(int k) {
		if(console.isOpen()) return;
		
		if(gameStates[currentState] != null) gameStates[currentState].keyReleased(k);
	}

	public void mousePressed(int x, int y) {
		if(pauseWindow != null) pauseWindow.mousePressed(x, y);
		
		if(gameStates[currentState] != null) gameStates[currentState].mousePressed(x, y);
	}

	public void mouseReleased(int x, int y) {
		if(gameStates[currentState] != null) gameStates[currentState].mouseReleased(x, y);
	}

	public void mouseMoved(int x, int y) {
		if(pauseWindow != null) pauseWindow.mouseMoved(x, y);
		
		if(gameStates[currentState] != null) gameStates[currentState].mouseMoved(x, y);
		
		console.mouseMoved(x, y);
	}
	
	public void mouseWheelMoved(int k) {
		if(gameStates[currentState] != null) gameStates[currentState].mouseWheelMoved(k);
	}

	@Override
	public void buttonPressed(UIButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBoxPressed(UICheckbox checkBox, boolean checked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewClosed(UIWindow window) {
		if(window == pauseWindow) pauseWindow = null;
		
	}
}
