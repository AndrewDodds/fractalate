package org.ajd.fractalate;

public final class FracConstants {

	private FracConstants() {
	}
	
	public static final double DEGREES_TO_RADS = 57.295779513d;
	


	// Will probably have to make this dependent on screen size
	public static final double TILE_SIZE = 200.0d;
	
	public static final double TIME_CONSTANT = 10.0d;
	
	// Effective size of world
	public static final long NUM_LAYERS = 5L;
	public static final long GROUND_LEVEL_LAYER = 3L;
	public static final long MIN_X = -20L;
	public static final long MAX_X = 20L;
	public static final long MIN_Y = -5L;
	public static final long MAX_Y = 100L;
	
	// Chance that a given point within the world will be an anchor point.
	public static final double BASE_COMPLEXITY = 0.035d;
	
	public static final double ACTIVE_TILE_FREQ = 0.2d;
	
	// For detection of closet point within grid
	public static final int NUM_CLOSEST_POINTS = 5;
	public static final int MAX_CLOSEST_POINT_DIST = 10;

	public static final int MIN_SIDES = 3;
	public static final int MAX_SIDES = 8;

	// Colour gradient layers
	public static final int MIN_COLOURS = 3;
	public static final int MAX_COLOURS = 5;
	
	// Recursive iterations for World Tile generation
	public static final int MIN_ITERS = 3;
	public static final int MAX_ITERS = 5;
	
	public static final int NUM_RAINBOW_COLOURS = 16;
	public static final double[][] RAINBOWCOLOURS = {
			{1.0d, 0.0d, 0.0d},
			{1.0d, 0.5d, 0.0d},
			{1.0d, 0.8d, 0.0d},
			{1.0d, 1.0d, 0.0d},
			{0.8d, 1.0d, 0.0d},
			{0.5d, 1.0d, 0.0d},
			{0.0d, 1.0d, 0.0d},
			{0.0d, 1.0d, 0.5d},
			{0.0d, 1.0d, 0.8d},
			{0.0d, 1.0d, 1.0d},
			{0.0d, 0.8d, 1.0d},
			{0.0d, 0.5d, 1.0d},
			{0.0d, 0.0d, 1.0d},
			{0.5d, 0.0d, 1.0d},
			{0.8d, 0.0d, 1.0d},
			{1.0d, 0.0d, 1.0d}
	};

	// Active tile parameters
	public static final int MAX_ACTIVETILE_SIDES = 8;
	public static final int MAX_ACTIVETILE_NUMGUNS = 4;
	public static final int MAX_ACTIVETILE_EYES = 5;
	public static final int ACTIVETILE_COLOURS = 16;
	public static final double ACTIVETRACKER_SPEEDDIV = 5.0d;
	
}
