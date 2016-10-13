package com.cpjd.hidden.ui.content;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.ui.windows.UIWindow;
import com.cpjd.tools.Layout;

public class CreditsWindow extends UIWindow {

	private double ext;
	
	// Credits format: <text>#size
	private static final String[] CREDITS = {
			"Hidden#100",
			"by Cats Pajamas Developers#40",
			"",
			"",
			"Team#30",
			"Engine and UI Programmer - Will D.#30",
			"AI and Gameplay - Alex H.#30",
			"Level Design and Artwork - Daniel P.#30",
			"",
			"",
			"Artwork#30",
			"Tileset Art - Thomas Bruno, http://opengameart.org/content/1950s-scifi-setting-indoor-tileset-with-kitchen-appliances#15"
	};
	
	public CreditsWindow() {
		super();
		
		ext = Layout.HEIGHT;
		
		setFontSize(20);
	}
	
	@Override
	public void update() {
		super.update();
		
		ext-=0.5;
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.setColor(Color.BLACK);
		for(int i = 0; i < CREDITS.length; i++) {
			if(CREDITS[i].equals("")) continue;
			
			g.setFont(font.deriveFont(Float.parseFloat(CREDITS[i].split("#")[1])));
			
			metrics = g.getFontMetrics();
			g.drawString(CREDITS[i].split("#")[0], Layout.centerw(metrics.stringWidth(CREDITS[i].split("#")[0])), (int)ext + (i * 50));
		}
	}
}
