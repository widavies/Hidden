package com.cpjd.hidden.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.gamestate.GameState;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.sound.SoundKeys;
import com.cpjd.hidden.sound.SoundPlayer;
import com.cpjd.hidden.toolbox.Console;
import com.cpjd.hidden.ui.UIListener;
import com.cpjd.hidden.ui.content.CreditsWindow;
import com.cpjd.hidden.ui.content.OptionsWindow;
import com.cpjd.hidden.ui.elements.UIButton;
import com.cpjd.hidden.ui.elements.UICheckbox;
import com.cpjd.hidden.ui.windows.UIDialog;
import com.cpjd.hidden.ui.windows.UIWindow;
import com.cpjd.tools.Layout;

public class Menu extends GameState implements UIListener {
	private UIButton play, options, credits, exit;
	private UIWindow playWindow;
	private CreditsWindow creditsWindow;
	private OptionsWindow optionsWindow;
	private UIDialog exitDialog;
	
	
	public Menu(GameStateManager gsm, Console console) {
		super(gsm, console);

		play = new UIButton("Play");
		options = new UIButton("Options");
		credits = new UIButton("Credits");
		exit = new UIButton("Exit");
		
		play.addUIListener(this);
		options.addUIListener(this);
		credits.addUIListener(this);
		exit.addUIListener(this);
	}
	
	public void update() {
		play.setLocation(Layout.alignx(5), Layout.aligny(40));
		options.setLocation(Layout.alignx(5), Layout.aligny(50));
		credits.setLocation(Layout.alignx(5), Layout.aligny(60));
		exit.setLocation(Layout.alignx(5), Layout.aligny(70));
		
		if(optionsWindow != null) optionsWindow.update();
		if(playWindow != null) playWindow.update();
		if(creditsWindow != null) creditsWindow.update();
		if(exitDialog != null) exitDialog.update();
	}
	public void draw(Graphics2D g) {
		play.draw(g);
		options.draw(g);
		credits.draw(g);
		exit.draw(g);
		
		if(optionsWindow != null) optionsWindow.draw(g);
		if(playWindow != null) playWindow.draw(g);
		if(creditsWindow != null) creditsWindow.draw(g);
		if(exitDialog != null) exitDialog.draw(g);
		
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(40f));
		g.drawString("HIDDEN", Layout.alignx(5), Layout.aligny(10));
	}
	public void keyPressed(int k) {}
	public void keyReleased(int k) {}
	public void mousePressed(int x, int y) {
		play.mousePressed(x, y);
		options.mousePressed(x, y);
		credits.mousePressed(x, y);
		exit.mousePressed(x, y);
		
		if(optionsWindow != null) optionsWindow.mousePressed(x, y);
		if(playWindow != null) playWindow.mousePressed(x, y);
		if(creditsWindow != null) creditsWindow.mousePressed(x, y);
		if(exitDialog != null) exitDialog.mousePressed(x, y);
	}
	public void mouseReleased(int x, int y) {}
	public void mouseMoved(int x, int y) {
		play.mouseMoved(x, y);
		options.mouseMoved(x, y);
		credits.mouseMoved(x, y);
		exit.mouseMoved(x, y);
		if(optionsWindow != null) optionsWindow.mouseMoved(x, y);
		if(playWindow != null) playWindow.mouseMoved(x, y);
		if(creditsWindow != null) creditsWindow.mouseMoved(x, y);
		if(exitDialog != null) exitDialog.mouseMoved(x, y);
	}
	public void mouseWheelMoved(int k) {}

	@Override
	public void buttonPressed(UIButton button) {
		if(options == button) {
			optionsWindow = new OptionsWindow();
			optionsWindow.center((int)(Layout.WIDTH / 1.5), (int)(Layout.HEIGHT / 1.5));
			optionsWindow.addUIListener(this);
			play.setFocus(false);
			options.setFocus(false);
			credits.setFocus(false);
			exit.setFocus(false);
		}
		if(play == button) {
			gsm.setState(GameStateManager.WORLD);
			//soundLoader.release();
			
		}
		if(credits == button) {
			creditsWindow = new CreditsWindow();
			creditsWindow.center(Layout.WIDTH, Layout.HEIGHT);
			creditsWindow.addUIListener(this);
			play.setFocus(false);
			options.setFocus(false);
			credits.setFocus(false);
			exit.setFocus(false);
			SoundPlayer.playMusic(SoundKeys.CREDITS_MUSIC);
		}
		if(exit == button) {
			exitDialog = new UIDialog();
			exitDialog.addUIListener(this);
			play.setFocus(false);
			options.setFocus(false);
			credits.setFocus(false);
			exit.setFocus(false);
		}
	}

	@Override
	public void viewClosed(UIWindow window) {
		if(window == optionsWindow) {
			optionsWindow = null;
			play.setFocus(true);
			options.setFocus(true);
			credits.setFocus(true);
			exit.setFocus(true);
		}
		if(window == playWindow) {
			playWindow = null;
			play.setFocus(true);
			options.setFocus(true);
			credits.setFocus(true);
			exit.setFocus(true);
		}
		if(window == creditsWindow) {
			creditsWindow = null;
			play.setFocus(true);
			options.setFocus(true);
			credits.setFocus(true);
			exit.setFocus(true);
			SoundPlayer.stopMusic(SoundKeys.CREDITS_MUSIC);
		}
		if(window == exitDialog) {
			exitDialog = null;
			play.setFocus(true);
			options.setFocus(true);
			credits.setFocus(true);
			exit.setFocus(true);
		}
	}

	@Override
	public void checkBoxPressed(UICheckbox checkBox, boolean checked) {
		
	}
	
	@Override
	public GameSave getSave(GameSave save) { return null; }
}
