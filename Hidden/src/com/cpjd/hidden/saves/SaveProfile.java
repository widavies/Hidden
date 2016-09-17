package com.cpjd.hidden.saves;

// The java class representation of all the saved data
public class SaveProfile {
	
	// Settings
	public boolean fullscreen;
	
	// Player
	public int maxPlayerHealth; // The player's highest health upgrade
	public int numCompletedChapters; // How many chapters the player has completed
	
	// Shop
	public int invsibleCloak; // The duration of the invsibility cloak (in ms), leave at 0 for no cloak
	public int decoy; // The amount of decoy grenades the player has purchased
	public int radioJammer; // The amount of radio jammers the player has purchased
	
	// Stats
	private int numberDeaths; // The total number of deaths
	private int enemiesKilled; // The total number of guards killed
	private long playTime; // The total time, in milliseconds, the player has played the game
	private int rank; // The players rank, 50 is the highest rank, generated off several aspects
	
	// Loads initial values, should only be called once at load
	public SaveProfile() {
		
	}
	
	// Saves the current values to the file system
	public void save() {
		
	}
}
