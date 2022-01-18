package org.ajd.fractalate;

public final class FracConstants {

	private FracConstants() {
	}

	// Will probably have to make this dependent on screen size
	public static final double TILE_SIZE = 200.0d;
	
	// Effective size of world
	public static final long NUM_LAYERS = 5L;
	public static final long GROUND_LEVEL_LAYER = 3L;
	public static final long MIN_X = -20L;
	public static final long MAX_X = 20L;
	public static final long MIN_Y = -5L;
	public static final long MAX_Y = 100L;
	
	// Chance that a given point within the world will be an anchor point.
	public static final double BASE_COMPLEXITY = 0.035d;
	public static final int NUM_CLOSEST_POINTS = 5;
	public static final int MAX_CLOSEST_POINT_DIST = 10;

	// Colour gradient layers
	public static final int MIN_COLOURS = 4;
	public static final int MAX_COLOURS = 6;
	
	public static final int MIN_ITERS = 3;
	public static final int MAX_ITERS = 5;
	
	
}
