package com.cpjd.hidden.sound;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.cpjd.hidden.toolbox.ErrorLog;

// Alpha 0.13 Sound Rework
public class SoundPlayer {
	
	public static boolean mute = false;
	
	private static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	private static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	private static float SFXVol = 1.4f;
	public static float MusicVol = 0.05f;
	
	public static void addSound(String key, String path) {
		try {
			soundMap.put(key, new Sound(SoundPlayer.class.getResource(path)));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void addMusic(String key, String path) {
		try {
			musicMap.put(key, new Music(SoundPlayer.class.getResource(path)));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void setVolume(String key, Float f) {
		musicMap.get(key).setVolume(f);
		SFXVol = f;
		MusicVol = f;
	}
	
	public static void removeAllSound() {
		soundMap.clear();
	}
	
	public static void removeAllMusic() {
		musicMap.clear();
	}
	
	public static void removeSound(String key) {
		soundMap.remove(key);
	}
	
	public static void removeMusic(String key) {
		musicMap.remove(key);
	}
	
	public static void playSound(String key) {
		try{
			if(!mute) soundMap.get(key).play(1f, SFXVol);
		}catch(NullPointerException e){
			ErrorLog.log("Null Pointer caught at SoundPlayer playSound(String)");
		}
	}
	
	public static void playMusic(String key) {
		if(!mute) musicMap.get(key).play(1f, MusicVol);;
	}
	public static boolean isSoundRunning(String key) {
		return soundMap.get(key).playing();
	}
	public static boolean isMusicRunning(String key) {
		return musicMap.get(key).playing();
	}
	public static void stopSound(String key) {
		soundMap.get(key).stop();
	}
	public static void stopMusic(String key) {
		musicMap.get(key).stop();
	}
	public static void pauseMusic(String key) {
		musicMap.get(key).pause();
	}
	public static void resumeMusic(String key) {
		musicMap.get(key).resume();
	}
	public static void loopMusic(String key) {
		if(!mute) musicMap.get(key).loop(1f, MusicVol);
	}
	public static void loopSound(String key) {
		if(!mute) soundMap.get(key).loop(1f, SFXVol);
	}
}
