package com.cpjd.hidden.genworld2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.cpjd.hidden.genworld.Structures;
import com.cpjd.tools.Log;

public class VillageModule extends TurbulenceModule implements Runnable {

	private ArrayList<Point> villageLocations;
	
	public VillageModule(int[][][] map, ModuleListener listener) {
		this.map = map;
		this.listener = listener;
		
		villageLocations = new ArrayList<Point>();
		
		r = new Random();
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		for (int i = 0; i < TurbulenceConstants.VILLAGES_TO_GENERATE; i++) {
			int x = r.nextInt(map.length - (2 * TurbulenceConstants.VILLAGE_BORDER)) + TurbulenceConstants.VILLAGE_BORDER;
			int y = r.nextInt(map[0].length - (2 * TurbulenceConstants.VILLAGE_BORDER)) + TurbulenceConstants.VILLAGE_BORDER;

			for (int j = 0; j < villageLocations.size(); j++) {
				if(Math.abs(villageLocations.get(j).x - x) <= TurbulenceConstants.VILLAGE_WIDTH && Math.abs(villageLocations.get(j).y - y) <= TurbulenceConstants.VILLAGE_HEIGHT) {
					i--;
					continue;
				}
			}

			villageLocations.add(new Point(x, y));
			Log.log("Generated village at: (" + villageLocations.get(i).x + "," + villageLocations.get(i).y + ")", 4);
		}
		
		for (int i = 0; i < villageLocations.size(); i++) {
			for (int k = villageLocations.get(i).y, y = 0; y < 15; k++, y++) {
				for (int j = villageLocations.get(i).x, l = 0; l < 30; j++, l++) {
					map[k][j][1] = Structures.VILLAGE_1[y][l] - 1;
					progress++;
				}
			}
		}
		
		listener.villagesFinished(map, villageLocations);
		listener.moduleFinished(map, 0, 0, map.length, map[0].length);
		
		try {
			thread.join();
		} catch(Exception e) {
			System.err.println("Failed to stop the village generation module");
		}
	}
}
