package com.cpjd.hidden.sound;

import com.cpjd.hidden.toolbox.MessageLog;

/**
 * Dynamic SoundLoader for Hidden. Create a new one of these when a group of sounds needs to be loaded / released. 
 * If the sound doesn't need to be managed after loaded, just let this class sit as it is.
 * @author Will Davies
 *
 */
public class SoundLoader implements Runnable {

	private Thread thread;
	private SoundRequest s;
	public int progress;
	public int total;
	
	public SoundLoader(SoundRequest s) {
		
		this.s = s;
		progress = 0;
		try {
			total = s.getMusic().length + s.getSFX().length;
		} catch(Exception e) {
			MessageLog.log("Error in SoundLoader constructor - couldn't find 'total' value");
		}
	}
	
	public void load() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		try {
			for(int i = 0; i < s.getSFX().length; i++) {
				SoundPlayer.addSound(s.getSFX()[i], SoundKeys.getPath(s.getSFX()[i]));
				progress++;
			}
		} catch(Exception e) {
			MessageLog.log("SoundLoader - Couldn't add sound");
		}
		try {
			for(int i = 0; i < s.getMusic().length; i++) {
				SoundPlayer.addMusic(s.getMusic()[i], SoundKeys.getPath(s.getMusic()[i]));
				progress++;
			}
		} catch(Exception e) {
			MessageLog.log("SoundLoader - Couldn't add music");
		}
		
		try {
			thread.join();
		} catch(Exception e) {
			System.err.println("Couldn't stop SoundLoader thread");
			MessageLog.log("Couldn't stop SoundLoader thread");
		}
	}
	
	public boolean isFinishedLoading() {
		return progress >= total;
	}
	
	/**
	 * Releases all the previously loaded sound from memory
	 */
	public void release() {
		for(int i = 0; i < s.getSFX().length; i++) {
			SoundPlayer.removeSound(s.getSFX()[i]);
		}
		for(int i = 0; i < s.getMusic().length; i++) {
			SoundPlayer.removeMusic(s.getMusic()[i]);
		}
	}
	
}
