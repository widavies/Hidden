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
			"Lead Programmer - Will Davies#30",
			"Programmer - Alex Harker#30",
			"Artwork/Lead Procrastinator - Daniel Peterson#30",
			"Artwork - Kira Davies#30",
			"",
			"",
			"Artwork Attributions#30",
			"Tileset Art - Thomas Bruno, http://opengameart.org/content/1950s-scifi-setting-indoor-tileset-with-kitchen-appliances#15",
			"Grass - http://opengameart.org/content/17-grass-tiles#15",
			"",
			"",
			"",
			"Sound Attributions#30",
			"Menu sound - strange_dragoon, https://www.freesound.org/people/strange_dragoon/sounds/271139/#15"
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
			//Layout.centerw(metrics.stringWidth(CREDITS[i].split("#")[0]))

			g.drawString(CREDITS[i].split("#")[0], Layout.alignx(15) , (int)ext + (i * 50));
		}
	}
}
