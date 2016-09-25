package com.cpjd.hidden.ui.hud;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cpjd.hidden.effects.Effect;
import com.cpjd.hidden.effects.Effect.EffectInfo;
import com.cpjd.hidden.items.Inventory;
import com.cpjd.hidden.items.Inventory.Item;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.toolbox.Layout;

public class HUD {

	// Player stats
	private double health; // Health, as a percent

	// Inventory
	private Inventory inventory;
	private int selected;

	// Effects
	private ArrayList<EffectInfo> effects;
	
	// Technical
	private AlphaComposite composite;
	private AlphaComposite defaultComposite;
	private boolean inv;
	private int mousex, mousey;
	private int[][] bulges = new int[5][6];
	private final int MAX_BULGE = 15;
	private boolean mActionRequired;
	private Point itemPosChange;
	private Item item;
	
	public HUD() {
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f);
		defaultComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		
		inventory = new Inventory(null);
		
		effects = new ArrayList<EffectInfo>();
		
		effects.add(new EffectInfo(Effect.POSION));
		effects.add(new EffectInfo(Effect.POSION));
		effects.add(new EffectInfo(Effect.POSION));
		effects.add(new EffectInfo(Effect.POSION));
	}

	public void setStats(double health, double maxHealth) {
		this.health = health / maxHealth;
	}

	public void addEffect(EffectInfo effectInfo) {
		if(!updateEffect(effectInfo)) effects.add(effectInfo);
	}
	
	public void removeEffect(EffectInfo effectInfo) {
		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i).type == effectInfo.type) {
				effects.remove(i);
				return;
			}
		}
	}
	
	// Returns if the effect was successfully updated
	public boolean updateEffect(EffectInfo effectInfo) {
		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i).type == effectInfo.type) {
				effects.set(i, effectInfo);
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		if(!inv) return;
		
		// Update bulges
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 6; j++) {
				if((containsMouseHotbar(i) && j == 5) || containsMouse(i, j)) {
					bulges[i][j] += 2;
					if(bulges[i][j] > MAX_BULGE) bulges[i][j] = MAX_BULGE;
				} else {
					bulges[i][j] -= 2;
					if(bulges[i][j] < 0) bulges[i][j] = 0;
				}
			}
		}
		
		// Manage inventory moving
		if(mActionRequired) {
			if(itemPosChange != null) { // Object is in hand
				for(int i = 0; i < 5; i++) {
					for(int j = 0; j < 6; j++) {
						if(containsMouse(i, j) || (containsMouseHotbar(i) && j == 5)) {
							inventory.modifyPosition(itemPosChange.x, itemPosChange.y, i, j, item);
							item = null;
							itemPosChange = null;
							mActionRequired = false;
							return;
						}
					}
				}
				// DROP OCCURS
				item = null;
				mActionRequired = false;
				itemPosChange = null;
			}
			for(int i = 0; i < 5; i++) { // Object is being picked up
				for(int j = 0; j < 6; j++) {
					if(containsMouse(i, j) || (containsMouseHotbar(i) && j == 5)) {
						itemPosChange = new Point(i, j);
					}
				}
			}
			if(itemPosChange != null) {
				item = inventory.getItem(itemPosChange.x, itemPosChange.y);
				inventory.removeItem(itemPosChange.x, itemPosChange.y);
			}
			mActionRequired = false;
		}
	}


	public void draw(Graphics2D g) {
		g.setComposite(composite);

		drawStats(g);
		drawHotbar(g);
		drawEffects(g);
		if(inv) drawInventory(g);
		
		g.setComposite(defaultComposite);
	}

	private void drawInventory(Graphics2D g) {
		// Player stats
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(450) - 200, Layout.centerh(450), 200, 300);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Layout.centerw(450) - 200, Layout.centerh(450), 200 - 64, 175);
		for (int i = 0; i < 4; i++) {
			g.setColor(Color.BLACK);
			g.drawRect(Layout.centerw(450) - 64, Layout.centerh(450) + (i * 64), 64, 64);
		}

		// Inventory
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(450), Layout.centerh(450), 450, 450);
		g.setColor(Color.BLACK);
		g.drawRect(Layout.centerw(450), Layout.centerh(450), 450, 450);
		g.drawRect(Layout.centerw(450) - 200, Layout.centerh(450), 200, 300);
		// Inventory slots
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				g.setColor(Color.BLACK);
				g.drawRect(Layout.centerw(450) + (i * 90), Layout.centerh(450) + (j * 90), 90, 90);
			}
		}
		
		// Draw item
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Item item = inventory.getItem(i, j);
				if(item != null) {
					g.drawImage(item.image, Layout.centerw(450) + (i * 90) - (bulges[i][j] / 2), Layout.centerh(450) + (j * 90) - (bulges[i][j] / 2), item.image.getWidth() + bulges[i][j], item.image.getHeight() + bulges[i][j], null);
				}
			}
		}
		
		// Draw tooltip text for selected item
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Item item = inventory.getItem(i, j);
				if(bulges[i][j] == MAX_BULGE && item != null && itemPosChange == null) {
					g.setComposite(defaultComposite);
					g.setColor(Color.WHITE);
					g.fillRect(mousex + 16, mousey, 250, 125);
					g.setComposite(composite);
					g.setColor(Color.BLACK);
					g.drawRect(mousex + 16, mousey, 250, 125);
					Font font = g.getFont();
					font = font.deriveFont(20f);
					g.setFont(font);
					g.drawString(item.name, mousex + 20, mousey + 20);
					font = g.getFont();
					font = font.deriveFont(15f);
					g.setFont(font);
					for(int k = 0; k < item.tooltip.split("\n").length; k++) {
						g.drawString(item.tooltip.split("\n")[k], mousex + 20, mousey + 40 + (g.getFontMetrics().getHeight() * k));
					}
				}
			}
		}
		
		// Draw moving item
		if(item != null) {
			g.drawImage(item.image, mousex, mousey, null);
		}
	}

	private void drawHotbar(Graphics2D g) {
		// Draw hotbar
		for (int i = 0; i < 5; i++) {
			Font font = g.getFont();
			font = font.deriveFont(20f);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.fillRect(Layout.centerw(450) + (i * 90), GamePanel.HEIGHT - 90, 90, 90);
			g.setColor(Color.BLACK);
			if(i == selected) {
				g.setStroke(new BasicStroke(6));
				g.drawRect(Layout.centerw(450) + (i * 90 + 2), GamePanel.HEIGHT - 88, 86, 86);
			} else {
				g.setStroke(new BasicStroke(1));
				g.drawRect(Layout.centerw(450) + (i * 90), GamePanel.HEIGHT - 90, 90, 90);
			}

			g.drawString("" + (i + 1), Layout.centerw(256) + (i * 90) - 90, GamePanel.HEIGHT - 90 + 20);
			g.setStroke(new BasicStroke(1));
		}
		
		// Draw hotbar items
		for(int i = 0; i < 5; i++) {
			Item item = inventory.getItem(i, 5);
			if(item != null) {
				g.drawImage(item.image, Layout.centerw(450) + (i * 90) - (bulges[i][5] / 2), GamePanel.HEIGHT - 88 - (bulges[i][5] / 2), item.image.getWidth() + bulges[i][5], item.image.getHeight() + bulges[i][5], null);
			}
		}
		
	}
	
	private void drawEffects(Graphics2D g) {
		for(int i = 0; i < effects.size(); i++) {
			g.setColor(Color.WHITE);
			g.fillRect(GamePanel.WIDTH - ((i + 1) * 100), 10, 90, 90);
			g.setColor(Color.BLACK);
			g.drawRect(GamePanel.WIDTH - ((i + 1) * 100), 10, 90, 90);
			g.setColor(Color.BLACK);
			double percent = effects.get(i).duration / effects.get(i).maxDuration;
			g.fillRect(GamePanel.WIDTH - ((i + 1) * 100) + 1, (int)(10 + (90 - (percent * 90))) + 1, 89, (int)(percent * 90));
		}
	}
	
	private void drawStats(Graphics2D g) {
		/* Draw player stats */
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 250, 200);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 250, 200);
		// Health
		g.setColor(Color.GRAY);
		g.fillRect(40, 10, 200, 30);
		g.setColor(Color.BLACK);
		g.fillRect(40, 10, (int) ((240 - 40) * health), 30);
		// Food
		g.setColor(Color.GRAY);
		g.fillRect(40, 50, 200, 30);
		g.setColor(Color.BLACK);
		g.fillRect(40, 50, (int) ((240 - 40) * .2), 30);
		// Timer
		g.setColor(Color.GRAY);
		g.fillRect(40, 90, 200, 30);
		g.setColor(Color.BLACK);
		Font font = g.getFont();
		font = font.deriveFont(27f);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.BLACK);
		g.fillRect(40, 90, (int) ((240 - 40) * .75), 30);
		g.setColor(Color.WHITE);
		g.drawString("42 seconds", 45, 83 + fm.getHeight());
	}
	
	private boolean containsMouse(int col, int row) {
		return mousex >= Layout.centerw(450) + (col * 90) && mousex <= Layout.centerw(450) + (col * 90) + 90 && mousey >= Layout.centerh(450) + (row * 90) && mousey <= Layout.centerh(450) + (row * 90) + 90;
	}
	
	private boolean containsMouseHotbar(int col) {
		return mousex >= Layout.centerw(450) + (col * 90) && mousex <= Layout.centerw(450) + (col * 90) + 90 && mousey >= GamePanel.HEIGHT - 90 && mousey <=  GamePanel.HEIGHT;
	}
	
	public boolean isInventoryOpen() {
		return inv;
	}
	
	public void mouseMoved(int x, int y) {
		mousex = x;
		mousey = y;
	}
	
	public void mousePressed(int x, int y) {
		if(!inv) {
			for(int i = 0; i < 5; i++) {
				if(containsMouseHotbar(i)) {
					selected = i;
				}
			}
			return;
		}
		mActionRequired = true;
	}
	
	public void mouseWheelMoved(int k) {
		if(k < 0) selected--;
		if(k > 0) selected++;
		if(selected < 0) selected = 0;
		if(selected > 4) selected = 4;
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_1) selected = 0;
		if(k == KeyEvent.VK_2) selected = 1;
		if(k == KeyEvent.VK_3) selected = 2;
		if(k == KeyEvent.VK_4) selected = 3;
		if(k == KeyEvent.VK_5) selected = 4;
		
		if(k == KeyEvent.VK_E) inv = !inv;
		
		if(!inv) {
			for(int i = 0; i < 5; i++) {
				bulges[i][5] = 0;
			}
		} else {
			item = null;
			itemPosChange = null;
		}
	}
}
