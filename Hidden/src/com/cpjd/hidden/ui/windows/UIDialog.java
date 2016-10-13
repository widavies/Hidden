package com.cpjd.hidden.ui.windows;

import java.awt.Color;
import java.awt.Graphics2D;

import org.lwjgl.openal.AL;

import com.cpjd.hidden.ui.elements.UIButton;
import com.cpjd.tools.Layout;

public class UIDialog extends UIWindow {
	
	private String title;
	
	public UIDialog() {
		super();
		
		center(Layout.WIDTH / 3, Layout.HEIGHT / 4);
		
		addButton("Cancel", 5, (int)(Layout.centerh(Layout.HEIGHT / 4) / 2.1), 400, false);
		addButton("Exit", 5, (int)(Layout.centerh(Layout.HEIGHT / 4) / 1.6), 400, false);
		
		title = "Exit game?";
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.setColor(Color.BLACK);
		g.drawString(title, x + ext + 5, y + ext + 35);
	}
	
	public void setTitle(String text) {
		this.title = text;
	}
	
	@Override
	public void buttonPressed(UIButton button) {
		super.buttonPressed(button);
		
		if(button.getText().equals("Exit")) {
			AL.destroy();
			System.exit(0);
		}
		else if(button.getText().equals("Cancel")) closing = true;

	}
}
