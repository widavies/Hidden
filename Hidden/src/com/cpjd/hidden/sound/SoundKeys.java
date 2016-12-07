package com.cpjd.hidden.sound;

public class SoundKeys {
	
	public static final String MENU_HOVER = "menu_hover";
	public static final String CREDITS_MUSIC = "credits_music";
	public static final String NOTIFY = "notification";
	
	public static String getPath(String key) {
		if(key.equals(MENU_HOVER)) return "/sound/interface/menu.ogg";
		if(key.equals(CREDITS_MUSIC)) return "/sound/interface/credits_music.ogg";
		if(key.equals(NOTIFY)) return "/sound/interface/notification.ogg";
		
		else return "null";
	}
}
