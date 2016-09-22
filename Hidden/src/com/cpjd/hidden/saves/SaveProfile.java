package com.cpjd.hidden.saves;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;


/*
 * 
 * Example save file:
 * 
 * * Settings:true
 * * Player:50:2
 * * Shop:0:5:2
 * * Stats:2:17:47939392:7
 * 
 * 
 * Format:
 * 
 * settings tag, fullscreen
 * player tag, maxPlayerHealth, numCompletedChapters
 * shop tag, cloak, decoy, jammer
 * stats tag, numDeaths, enemiesKilled, playTime, rank
 * 
 */


// The java class representation of all the saved data
public class SaveProfile {
	
	// Settings
	public boolean fullscreen;
	
	// Player
	public int maxPlayerHealth; // The player's highest health upgrade
	public int numCompletedChapters; // How many chapters the player has completed
	
	// Inventory
	public String[][] items = new String[5][5];
	
	// Shop
	public int invisibleCloak; // The duration of the invisibility cloak (in ms), leave at 0 for no cloak
	public int decoy; // The amount of decoy grenades the player has purchased
	public int radioJammer; // The amount of radio jammers the player has purchased
	
	// Stats
	private int numberDeaths; // The total number of deaths
	private int enemiesKilled; // The total number of guards killed
	private long playTime; // The total time, in milliseconds, the player has played the game
	private int rank; // The players rank, 50 is the highest rank, generated off several aspects
	
	//save stuff
	private final String DIVIDER = ":";
	
	
	
	// Loads initial values, should only be called once at load
	public SaveProfile() throws Exception{
		
		FileReader fr = null;
        URI url = new URI(getBaseDirectory() + "/HiddenSave.save");
        
        fr = new FileReader(new File(url));
        BufferedReader reader = new BufferedReader(fr);
        
		String line;
		while((line = reader.readLine()) != null){
			
			String[] lineArray = line.split(DIVIDER);
			
			if(lineArray[0].equals("Settings")){
				
				fullscreen = Boolean.parseBoolean(lineArray[1]);
				
			}else if(lineArray[0].equals("Player")){
				
				maxPlayerHealth = Integer.parseInt(lineArray[1]);
				numCompletedChapters = Integer.parseInt(lineArray[2]);
				
			}else if(lineArray[0].equals("Shop")){
				
				invisibleCloak = Integer.parseInt(lineArray[1]);
				decoy = Integer.parseInt(lineArray[2]);
				radioJammer = Integer.parseInt(lineArray[3]);
				
			}else if(lineArray[0].equals("Stats")){
				
				numberDeaths = Integer.parseInt(lineArray[1]);
				enemiesKilled = Integer.parseInt(lineArray[2]);
				playTime = Integer.parseInt(lineArray[3]);
				rank = Integer.parseInt(lineArray[4]);
				
			}
			
		}
		
		fr.close();
		reader.close();
		
	}

	// Saves the current values to the file system
	public void save() throws Exception {
		
		//create directory
		File directory = null;
		directory = new File(new URI(getBaseDirectory()));
		
		//make dir if it doesn't exist
		if(!directory.isDirectory()){
			directory.mkdir();
		}
		
		//create save file
		File saveFile = null;
		saveFile = new File(new URI(getBaseDirectory() + "/HiddenSave.save"));
		
		//automatically does not create file if it already exists
		saveFile.createNewFile();
		
		FileWriter fileWriter = null;
		fileWriter = new FileWriter(saveFile);		
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		//save settings
		writer.write("Settings" + DIVIDER + Boolean.toString(fullscreen));
		writer.newLine();
		
		//save player
		writer.write("Player" + DIVIDER + maxPlayerHealth + DIVIDER + numCompletedChapters);
		writer.newLine();
		
		//save shop
		writer.write("Shop" + DIVIDER + invisibleCloak + DIVIDER + decoy + DIVIDER + radioJammer);
		writer.newLine();
		
		//save stats
		writer.write("Stats" + DIVIDER + numberDeaths + DIVIDER + enemiesKilled + DIVIDER + playTime + DIVIDER + rank);
		
		fileWriter.close();
		writer.close();
	}
	
	private String getBaseDirectory() {
		String OS = System.getProperty("os.name").toUpperCase();
		
	    if (OS.contains("WIN"))
	        return System.getenv("APPDATA/Hidden");
	    else if (OS.contains("MAC"))
	        return System.getProperty("user.home") + "/Library/Application Support/Hidden";
	    
	    System.err.println("OS unsupported");
	    return null;
	}
}
