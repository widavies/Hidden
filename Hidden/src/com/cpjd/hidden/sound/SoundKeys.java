package com.cpjd.hidden.sound;

public class SoundKeys {
	
	public static final String MENU_HOVER = "m_hover";
	
	public static String getPath(String key) {
		if(key.equals(MENU_HOVER)) return "/sound/interface/menu.ogg";
		else return "null";
	}
}
