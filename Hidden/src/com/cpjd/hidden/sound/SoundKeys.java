package com.cpjd.hidden.sound;

public class SoundKeys {
	
	public static final String MENU_HOVER = "m_hover";
	
	public static String getPath(String key) {
		switch(MENU_HOVER) {
			case MENU_HOVER: return "/sound/interface/menu.ogg";
			default: return "null";
		}
	}
}
