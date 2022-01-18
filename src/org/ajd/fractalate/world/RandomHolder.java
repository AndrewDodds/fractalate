package org.ajd.fractalate.world;

import java.util.Random;

public class RandomHolder {

	private RandomHolder() {
		
	}
	
	private static Random r;
	
	public static void initRandom(long seed) {
		r = new Random(seed);
	}
	
	public static Random getRandom() {
		if(r == null ) {
			initRandom(1); // Make sure this shows up!
		}
		return r;
	}
	
}
