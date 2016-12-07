package com.cpjd.hidden.ui;

import javax.imageio.ImageIO;

import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.sound.SoundKeys;
import com.cpjd.hidden.sound.SoundPlayer;
import com.cpjd.hidden.ui.Notifications.Notification;

public class NotificationsFetcher implements Runnable {

	private Notification notification;
	private Thread thread;
	
	public NotificationsFetcher(Notification notification) {
		this.notification = notification;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		if(!notification.getIconPath().equals("null")) {
			try {
				notification.setIcon(ImageIO.read(getClass().getResourceAsStream(notification.getIconPath())));
			} catch (Exception e) {
				System.err.println("Notification icon could not be loaded");
			}
		}

		SoundPlayer.playSound(SoundKeys.NOTIFY);
		Notifications.ticks = GameStateManager.ticks;
		Notifications.NOTIFICATIONS.add(notification);
		
		try {
			thread.join();
		} catch(Exception e) {
			System.err.println("Could not stop notification fetcher thread.");
		}
	}
	
}
