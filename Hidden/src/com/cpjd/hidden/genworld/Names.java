package com.cpjd.hidden.genworld;

import java.util.Random;

/**
 * Names for randomly generated stuff
 * @author Will Davies
 *
 */
public class Names {
	
	private static final String[] PRISON_NAMES = {
			""
	};
	
	private static final String[] VILLAGE_NAMES = {
			
	};
	
	public static String getPrisonName() {
		return PRISON_NAMES[new Random().nextInt(PRISON_NAMES.length)];
	}
	
	public static String getVillageName() {
		return VILLAGE_NAMES[new Random().nextInt(VILLAGE_NAMES.length)];
	}
	

}
