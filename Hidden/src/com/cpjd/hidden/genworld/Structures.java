package com.cpjd.hidden.genworld;

public class Structures {

	public static final byte[][] TIER_1 = {
		{TileIDs.GLASS, TileIDs.GLASS, TileIDs.GLASS},
		{TileIDs.GLASS, TileIDs.CEMENT_1, TileIDs.GLASS},
		{TileIDs.GLASS, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.GLASS}
	};
	
	public static final byte[][] TIER_2 = {
			{TileIDs.GLASS, TileIDs.GLASS, TileIDs.GLASS, TileIDs.GLASS},
			{TileIDs.GLASS, TileIDs.CEMENT_1, TileIDs.CEMENT_1, TileIDs.GLASS},
			{TileIDs.GLASS, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.GLASS}
	};
	
	public static final byte[][] TIER_3 = {
			{TileIDs.WALL, TileIDs.WALL, TileIDs.WALL, TileIDs.WALL},
			{TileIDs.WALL, TileIDs.CEMENT_1, TileIDs.CEMENT_1, TileIDs.WALL},
			{TileIDs.WALL, TileIDs.CEMENT_1, TileIDs.CEMENT_1, TileIDs.WALL},
			{TileIDs.WALL, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL}
	};
	
	public static final byte[][] TIER_4 = {
			{TileIDs.WALL, TileIDs.GLASS, TileIDs.GLASS, TileIDs.WALL},
			{TileIDs.GLASS, TileIDs.CEMENT_1, TileIDs.CEMENT_1, TileIDs.GLASS},
			{TileIDs.GLASS, TileIDs.CEMENT_1, TileIDs.CEMENT_1, TileIDs.GLASS},
			{TileIDs.WALL, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL}
	};
	
	public static final byte[][] TIER_5 = {
			{TileIDs.WALL_2, TileIDs.WALL_2, TileIDs.WALL_2, TileIDs.WALL_2},
			{TileIDs.WALL_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.WALL_2},
			{TileIDs.WALL_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.WALL_2},
			{TileIDs.WALL_2, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL_2}
	};
	
	public static final byte[][] TIER_6 = {
			{TileIDs.WALL_2, TileIDs.WALL_2, TileIDs.WALL_2, TileIDs.WALL_2, TileIDs.WALL_2},
			{TileIDs.WALL_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.WALL_2},
			{TileIDs.WALL_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.WALL_2},
			{TileIDs.WALL_2, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.WALL_2, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL_2}
	};
	
	public static final byte[][] TIER_7 = {
			{TileIDs.WALL_2, TileIDs.GLASS, TileIDs.WALL_2, TileIDs.GLASS, TileIDs.WALL_2},
			{TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.GLASS},
			{TileIDs.WALL_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.WALL_2},
			{TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.GLASS},
			{TileIDs.WALL_2, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.WALL_2, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL_2}
	};
	
	public static final byte[][] TIER_8 = {
			{TileIDs.WALL_3, TileIDs.GLASS, TileIDs.WALL_3, TileIDs.GLASS, TileIDs.WALL_3},
			{TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.GLASS},
			{TileIDs.WALL_3, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.WALL_3},
			{TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.GLASS},
			{TileIDs.WALL_3, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.WALL_2, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL_3}
	};
	
	public static final byte[][] TIER_9 = {
			{TileIDs.GRASS_1, TileIDs.DEAD_GRASS, TileIDs.DEAD_GRASS, TileIDs.GRASS_1, TileIDs.DEAD_GRASS, TileIDs.GRASS_4, TileIDs.GRASS_1, TileIDs.DEAD_GRASS},
			{TileIDs.GRASS_3,TileIDs.WALL_3, TileIDs.GLASS, TileIDs.WALL_3, TileIDs.WALL_3, TileIDs.GLASS, TileIDs.WALL_3, TileIDs.DEAD_GRASS},
			{TileIDs.GRASS_2,TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2,TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.GLASS, TileIDs.DEAD_GRASS},
			{TileIDs.DEAD_GRASS,TileIDs.WALL_3, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2,TileIDs.CEMENT_2, TileIDs.WALL_3, TileIDs.DEAD_GRASS},
			{TileIDs.GRASS_4,TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2,TileIDs.CEMENT_2, TileIDs.GLASS, TileIDs.GRASS_4},
			{TileIDs.DEAD_GRASS,TileIDs.WALL_3, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.WALL_3, TileIDs.WALL_3, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL_3, TileIDs.DEAD_GRASS},
			{TileIDs.DEAD_GRASS,TileIDs.DEAD_GRASS, TileIDs.DEAD_GRASS, TileIDs.GRASS_1, TileIDs.DEAD_GRASS, TileIDs.GRASS_4, TileIDs.GRASS_1, TileIDs.DEAD_GRASS}
	};
	
	public static final byte[][] TIER_10 = {
			{TileIDs.WALL_2, TileIDs.GLASS, TileIDs.WALL_2, TileIDs.GLASS, TileIDs.WALL_2},
			{TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.GLASS},
			{TileIDs.WALL_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.WALL_2},
			{TileIDs.GLASS, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.CEMENT_2, TileIDs.GLASS},
			{TileIDs.WALL_2, TileIDs.DOOR_LOCKED_LEFT_CEMENT, TileIDs.WALL_2, TileIDs.DOOR_LOCKED_RIGHT_CEMENT, TileIDs.WALL_2}
	};
	
	public static final byte[][] VILLAGE_1 = {
			{8,7,9,8,7,43,43,42,42,42,42,42,43,43,2,2,2,31,31,31,31,31,31,8,43,43,43,43,43,43},
			{7,12,12,11,8,43,15,15,15,15,15,15,15,43,2,2,2,31,106,5,5,106,31,11,43,76,15,15,78,43},
			{8,11,12,12,9,42,15,31,31,31,31,31,15,42,2,2,2,31,5,5,5,5,31,8,43,15,15,15,15,43},
			{10,11,12,12,9,42,15,15,15,15,15,15,15,42,2,3,2,31,5,5,5,5,31,7,43,15,15,15,15,43},
			{9,7,8,11,8,43,15,15,15,15,15,15,15,43,2,2,2,31,5,5,5,5,31,10,43,15,15,15,15,43},
			{7,9,7,8,9,43,79,43,43,43,43,43,80,43,2,2,2,31,31,49,50,31,31,9,43,43,79,80,43,43},
			{7,3,2,2,3,2,2,2,2,3,2,2,2,2,3,2,2,2,2,3,2,2,2,2,2,3,2,3,2,2},
			{9,2,3,2,2,2,2,3,3,2,2,2,3,2,2,2,2,2,3,2,2,2,2,3,2,2,2,3,2,3},
			{2,8,2,3,2,2,2,2,2,2,3,2,3,2,2,3,2,2,2,3,2,2,3,2,2,2,2,2,3,2},
			{7,8,7,11,8,7,43,43,79,80,43,43,2,3,2,43,43,79,80,43,43,8,31,79,31,31,31,31,80,31},
			{12,32,8,32,32,9,43,76,15,15,15,43,3,2,3,43,78,15,15,78,43,10,31,15,15,15,15,15,15,31},
			{7,32,32,32,32,7,42,15,15,15,15,42,4,3,4,42,76,15,15,76,42,9,31,15,15,15,15,15,15,31},
			{9,32,32,32,32,8,42,15,15,15,15,42,3,2,2,42,15,15,15,15,42,12,31,15,43,43,43,43,15,31},
			{7,8,32,32,12,9,43,15,15,15,15,43,2,4,3,43,15,15,15,15,43,10,31,15,15,15,15,15,15,31},
			{7,11,9,9,7,7,43,43,42,42,43,43,2,4,2,43,43,42,42,43,43,9,31,31,31,31,31,31,31,31}
	};
	
}
