package com.cpjd.hidden.genworld2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.cpjd.hidden.genworld.Structures;
import com.cpjd.hidden.genworld.TileIDs;
import com.cpjd.tools.Log;

public class PrisonModule extends TurbulenceModule implements Runnable {

	private final int[] VIABLE_TILES = { TileIDs.GRASS_1, TileIDs.GRASS_2, TileIDs.GRASS_3, TileIDs.GRASS_4, TileIDs.FLOWER_1, TileIDs.FLOWER_2,
			TileIDs.FOREST_1, TileIDs.FOREST_2, TileIDs.FOREST_3, TileIDs.FOREST_4 };

	private ArrayList<ArrayList<Point>> prisonLocations;
	private ArrayList<Point> villageLocations;

	private final int[] MINIMUM = { 25, 20, 20, 15, 15, 10, 10, 5, 5, 2 };
	private int[] currentCount;

	public PrisonModule(int[][][] map, ArrayList<Point> villageLocations, ModuleListener listener) {
		this.map = map;
		this.listener = listener;
		this.villageLocations = villageLocations;

		prisonLocations = new ArrayList<ArrayList<Point>>();

		r = new Random();

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		currentCount = new int[10];

		for (int i = 0; i < prisonLocations.size(); i++)
			prisonLocations.add(new ArrayList<Point>());

		generatePrisonLocations(1, (int) (TurbulenceConstants.WIDTH * .3), -1, 800, 6);
		generatePrisonLocations(2, (int) (TurbulenceConstants.WIDTH * .25), -1, 900, 7);
		generatePrisonLocations(3, (int) (TurbulenceConstants.WIDTH * .20), -1, 800, 8);
		generatePrisonLocations(4, (int) (TurbulenceConstants.WIDTH * .25), (int) (TurbulenceConstants.WIDTH * 0.4), 1200, 10);
		generatePrisonLocations(5, (int) (TurbulenceConstants.WIDTH * .23), (int) (TurbulenceConstants.WIDTH * 0.35), 1400, 10);
		generatePrisonLocations(6, (int) (TurbulenceConstants.WIDTH * .22), (int) (TurbulenceConstants.WIDTH * 0.32), 1400, 8);
		generatePrisonLocations(7, (int) (TurbulenceConstants.WIDTH * .21), (int) (TurbulenceConstants.WIDTH * 0.28), 1600, 9);
		generatePrisonLocations(8, 20, (int) (TurbulenceConstants.WIDTH * 0.25), 1800, 9);
		generatePrisonLocations(9, 15, (int) (TurbulenceConstants.WIDTH * 0.20), 1600, 15);
		generatePrisonLocations(10, 10, (int) (TurbulenceConstants.WIDTH * 0.18), 1800, 15);

		// We've got locations for everything now, let's generate the structures
		// around them.
		for (int i = 0; i < prisonLocations.size(); i++) {
			for (int j = 0; j < prisonLocations.get(i).size(); j++) {
				generateStructure(i, prisonLocations.get(i).get(j).x, prisonLocations.get(i).get(j).y);
			}
		}

		listener.moduleFinished(map, 0, 0, map.length, map[0].length, villageLocations, prisonLocations);

		try {
			thread.join();
		} catch (Exception e) {
			System.err.println("Couldn't stop prison module");
		}
	}

	/**
	 * Generates a prison structure based off a location
	 * 
	 * @param structure
	 *            A byte array that contains the tile ids of the structure, must
	 *            contain only 1 center tile, must have odd dimensions
	 * @param centerx
	 *            the x position of the center block
	 * @param centery
	 *            the y position of the center block
	 * @param startx
	 *            the x position offset (from the center) to start generating
	 *            the structure
	 * @param starty
	 *            the y position offset (from the center) to start generating
	 *            the structure
	 */
	private void generateStructure(int tier, int centerx, int centery) {
		byte[][] structure = null;

		switch (tier) {
		case 0:
			structure = Structures.TIER_1;
			break;
		case 1:
			structure = Structures.TIER_2;
			break;
		case 2:
			structure = Structures.TIER_3;
			break;
		case 3:
			structure = Structures.TIER_4;
			break;
		case 4:
			structure = Structures.TIER_5;
			break;
		case 5:
			structure = Structures.TIER_6;
			break;
		case 6:
			structure = Structures.TIER_7;
			break;
		case 7:
			structure = Structures.TIER_8;
			break;
		case 8:
			structure = Structures.TIER_9;
			break;
		case 9:
			structure = Structures.TIER_10;
			break;
		}

		if(structure == null) return;
		for (int row = centery - (int) Math.floor(structure[0].length / 2), y = 0; y < structure.length; row++, y++) {
			for (int col = centerx - (int) Math.floor(structure.length / 2), x = 0; x < structure[0].length; col++, x++) {
				map[row][col][1] = structure[y][x];
			}
		}
	}

	/*
	 * Start border is the distance from the outside it can't spawn under (e.g.,
	 * 5 would mean it can't be tiles 0,1,2,3,4) End border is the maximum range
	 * it can spawn in from the outside (range of 10 would mean it can only
	 * spawn within 10 blocks of the edge)
	 */
	private void generatePrisonLocations(int tier, int startBorder, int endBorder, int prob, int regionSize) {
		prisonLocations.add(new ArrayList<Point>());

		for (int col = 0; col < map.length; col++) {
			if(col < startBorder || col > map.length - startBorder - 1) continue;

			for (int row = 0; row < map[0].length; row++) {
				if(row < startBorder || row > map[0].length - startBorder - 1) continue;
				if(endBorder > 0 && (row > endBorder && row < map.length - 1 - endBorder && col > endBorder && col < map.length - 1 - endBorder))
					continue;
				// Check to make sure we're in a piece of land that's not within
				// 10 tiles of another prison, and not on top of water
				if(checkRegion(col, row, regionSize)
						&& (r.nextInt(prob) <= 1 || (currentCount[tier - 1] < MINIMUM[tier - 1]) && col > currentCount[tier - 1] * 28)) {
					currentCount[tier - 1]++;
					prisonLocations.get(tier - 1).add(new Point(col, row));

					Log.log("Tier " + tier + " prison generated at: " + col + "," + row, 2);
				}
			}
		}
	}

	private boolean checkRegion(int col, int row, int diameter) {
		boolean regionViable;

		/*for (int i = 0; i < villageLocations.size(); i++) {
			if(Math.abs(villageLocations.get(i).x - col) <= TurbulenceConstants.VILLAGE_WIDTH
					&& Math.abs(villageLocations.get(i).y - row) <= TurbulenceConstants.VILLAGE_HEIGHT) {
				return false;
			}
		}*/

		for (int i = 0; i < prisonLocations.size(); i++) {
			for (int j = 0; j < prisonLocations.get(i).size(); j++) {
				if(Math.abs(prisonLocations.get(i).get(j).x - col) <= diameter && Math.abs(prisonLocations.get(i).get(j).y - row) <= diameter) {
					return false;
				}
			}
		}

		for (int x = col - (diameter / 2); x < (col - diameter / 2) + diameter; x++) {
			for (int y = row - (diameter / 2); y < (row - (diameter / 2) + diameter); y++) {
				if(col < 0 || row < 0 || x > map.length || y > map[0].length) return false;

				regionViable = false;
				for (int i = 0; i < VIABLE_TILES.length; i++) {
					if(map[y][x][0] == VIABLE_TILES[i]) {
						regionViable = true;
						break;
					}
				}
				if(!regionViable) return false;
			}
		}

		return true;
	}
}
