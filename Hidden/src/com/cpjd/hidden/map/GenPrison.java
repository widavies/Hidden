package com.cpjd.hidden.map;

import java.util.Random;

// Manages prison generation for the map
public class GenPrison {

	private byte[][] generation;
	private Random r;
	
	// Constants
	private final int BORDER = 8;
	
	// How the different tiers look
	private final byte[][] TIER1_2_3 = {
			{GenWorld.WALL, GenWorld.WALL, GenWorld.WALL},
			{GenWorld.WALL, GenWorld.CEMENT, GenWorld.WALL},
			{GenWorld.WALL, GenWorld.LOCKED_DOOR, GenWorld.WALL}
	};
	
	private final byte[][] TIER4_5_6 = {
			{GenWorld.WALL, GenWorld.WALL, GenWorld.WALL, GenWorld.WALL, GenWorld.WALL},
			{GenWorld.WALL, GenWorld.WALL, GenWorld.CEMENT, GenWorld.WALL, GenWorld.WALL},
			{GenWorld.WALL, GenWorld.LOCKED_DOOR, GenWorld.LOCKED_DOOR, GenWorld.WALL}
	};
	
	public GenPrison(byte[][] generation) {
		this.generation = generation;
		r = new Random();
		
		generatePrisons();
	}
	
	private void generatePrisons() {
		for(int col = 0; col < generation.length; col++) {
			for(int row = 0; row < generation[0].length; row++) {
				if(col > BORDER && col < generation.length - BORDER && row > BORDER && row < generation.length - BORDER && generation[row + 3][col] <= GenWorld.GRASS_6 && generation[row + 2][col] <= GenWorld.GRASS_6) {
					if(r.nextInt(1000) <= 1) generateTier(1, col, row);
					else if(r.nextInt(1500) <= 1) generateTier(2, col, row);
				}
			}
		}
	}
	
	public byte[][] getMap() {
		return generation;
	}
	
	
	private void generateTier(int tier, int x, int y) {
		byte[][] array = null;
		byte startxOffset = 0, startyOffset = 0;
		
		switch(tier) {
		case 1:
			array = TIER1_2_3;
			startxOffset = 1;
			startyOffset = 1;
			break;
		case 2:
			array = TIER1_2_3;
			startxOffset = 2;
			startyOffset = 1;
			System.out.println("Generating tier 4 bithces");
			break;
		}
		
		for(int row = 0; row < array[0].length; row++) {
			for(int col = 0; col < array.length; col++) {
				generation[y - startyOffset + row][x - startxOffset + col] = array[row][col];
			}
		}
			
	}
	
	
	
	
	
}
