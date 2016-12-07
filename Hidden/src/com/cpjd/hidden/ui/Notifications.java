package com.cpjd.hidden.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.tools.Layout;

/**
 * Manages the game notifications.
 * Pending a notification manager center.
 * @author Will Davies
 *
 */
public class Notifications extends View {
	
	// Settings
	private int FINAL_WIDTH;
	
	// Vars
	public static ArrayList<Notification> NOTIFICATIONS;
	private int notificationsReadStatus;
	private double currentFade;
	public static long ticks;
	private double speed;
	private int yPos;
	
	public Notifications() {
		NOTIFICATIONS = new ArrayList<Notification>();
		notificationsReadStatus = 0;
		currentFade = 1;
		speed = 100;
	}
	
	/**
	 * Adds a notificiation to the queue. Once all prior notifications have been displayed, it will be displayed.
	 * If the request notification contains an icon, the icon will be loaded in an seperate thread before
	 * being displayed (Resulting in notifications being displayed a few seconds after being added to the queue).
	 * @param notification An pre-defined static notification specification
	 */
	public static void addNotification(Notification notification) {
		new NotificationsFetcher(notification);
	}
	
	public void draw(Graphics2D g) {
		FINAL_WIDTH = GamePanel.WIDTH / 4;
		
		if(notificationsReadStatus != NOTIFICATIONS.size()) {
			if(width >= FINAL_WIDTH) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)currentFade));
				if(currentFade <= 0.02) {
					currentFade = 1;
					notificationsReadStatus++;
					width = 0;
					ticks = GameStateManager.ticks;
					speed = 100;
					return;
				} else {
					if((GameStateManager.ticks - ticks) / GamePanel.FPS > NOTIFICATIONS.get(notificationsReadStatus).getLingerTime()) {
						currentFade-=0.02;
					}
				}
			} else {
				width += speed;
				speed += 0.8;
			}
			g.setColor(Color.WHITE);
			g.fillRect(0, Layout.aligny(5), width, Layout.aligny(14));
			g.setColor(Color.BLACK);
			g.setFont(GameStateManager.font.deriveFont((float)(Layout.WIDTH * 0.016229 + 0.0204)));
			yPos = Layout.aligny(8);
			for(String line : NOTIFICATIONS.get(notificationsReadStatus).getMessage().split("\n")) {
				g.drawString(line, width - FINAL_WIDTH, yPos);	
				yPos += Layout.getStringHeight(g);
			}
			if(NOTIFICATIONS.get(notificationsReadStatus).getIcon() != null)
				g.drawImage(NOTIFICATIONS.get(notificationsReadStatus).getIcon(), width - Layout.aligny(8) - 10, Layout.aligny(6),
						Layout.aligny(8), Layout.aligny(8), null);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	
	public static class Notification {
		private String message;
		private BufferedImage icon;
		private String iconPath;
		private double lingerTime;
		
		public Notification(String message, double lingerTime) {
			this.message = message;
			this.lingerTime = lingerTime;
		}
		
		public Notification(String message, String iconPath, double lingerTime) {
			this.message = message;
			this.iconPath = iconPath;
			this.lingerTime = lingerTime;
		}
		public void setIcon(BufferedImage icon) {
			this.icon = icon;
		}
		public String getMessage() {
			return message;
		}
		public double getLingerTime() {
			return lingerTime;
		}
		public BufferedImage getIcon() {
			return icon;
		}
		
		public String getIconPath() {
			if(iconPath == null || iconPath.equals("")) return "null";
			return iconPath;
		}
	}
	
	public static class GameSavedNotification extends Notification {
		public GameSavedNotification() {
			super("Game has been saved.", "/notifications/save.png", 0.7);
		}
	}
	
	public static class Test extends Notification {
		public Test() {
			super("Welcome to Hidden. Where\nwhat's actually hidden is\nthe tileset!", 3.2);
		}
	}
}
