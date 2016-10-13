package com.cpjd.hidden.sound;

// Should be passed into sound loader prior to playing any of the sounds
public class SoundRequest {
	
	private String[] sfx, music;
	
	public SoundRequest(String[] sfx, String[] music) {
		this.sfx = sfx;
		this.music = music;
	}
	
	public String[] getSFX() {
		return sfx;
	}
	
	public String[] getMusic() {
		return music;
	}
}
