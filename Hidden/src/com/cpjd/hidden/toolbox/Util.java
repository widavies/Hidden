package com.cpjd.hidden.toolbox;

/**
 * For any random useful stuff
 * @author Alex Harker
 *
 */
public class Util {
	
	/**
	 * Converts a String[] to a String
	 * 
	 * @param space If true, this method will insert a space between each index of array
	 * @param array The array to use to find the String
	 * @return String that is a combination of the Strings in the array
	 */
	public static String getString(String [] array, boolean space){
		StringBuilder builder = new StringBuilder();
		for(String s : array) {
		    builder.append(s);
		   if(space) builder.append(" ");
		}
		return builder.toString();
	}
	
}
