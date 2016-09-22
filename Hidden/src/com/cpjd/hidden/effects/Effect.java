package com.cpjd.hidden.effects;

import java.awt.image.BufferedImage;

import com.cpjd.hidden.entities.Player;

// An effect is a short lived event that modifies something, e.g. an invisiblity cloak for 30 seconds or poision for 10 seconds
public class Effect {

	// IDs
	public static final int INVISIBLITY = 0;
	public static final int POSION = 1;
	public static final int HEAL = 2;
	public static final int QUIET = 3;
	
	public static EffectInfo getEffectInfo(int type) {
		return new EffectInfo(type);
	}
	
	// Returns false if the action duration is over
	public static boolean doAction(EffectInfo info, Player p) {
		if(info.duration <= 0) return false;
		
		return false; // Not done yet
	}
	
	public static class EffectInfo {
		public int type; // The effect id
		public BufferedImage image; // The icon to display in the inventory
		public double duration; // Current time
		public double maxDuration; // Duration, in ms
		
		public EffectInfo(int type) {
			this.type = type;
			this.duration = 75;
			this.maxDuration = 100;
			// Load art, and duration in here
		}
	}
	
}
