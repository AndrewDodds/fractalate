package org.ajd.fractalate.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.elements.Tile;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.scene.canvas.GraphicsContext;

public class World implements Renderable {
	private List<Tile> tiles;
	
	private int xSize = (int) ((FracConstants.MAX_X - FracConstants.MIN_X) + 1);
	private int ySize = (int) ((FracConstants.MAX_Y - FracConstants.MIN_Y) + 1);
	
	private double worldScale = 1.0d;
	private Coordinate myCoord = new Coordinate(100.0d, 100.0d, 100.0d);

	public World(double difficulty, long seed) {
		tiles = new ArrayList<>();
		
		RandomHolder.initRandom(seed);
		
		calcHeightMapBuildTiles(difficulty, FracConstants.BASE_COMPLEXITY);
	}
	
	public void setCoord(Coordinate newCoord) {
		myCoord = newCoord;
	}
	
	
	private void buildTiles(double[][] heightArray, PatchInfoRec[][] colArray) {
		
		// Create all tiles - start from the bottom, so ArrayList ordering should(!) give us free z buffering
		// And here we build the list of tiles from the bottom up, so we don;t need to worry about Z buffering later
		tiles = new ArrayList<>();
		for(int h = 1; h<=FracConstants.NUM_LAYERS; h++) {
			for(int i=0; i<xSize; i++) {
				for(int j=0; j<ySize; j++) {
					if(heightArray[i][j] > h) {
						tiles.add( new Tile(i-(long)(xSize/2), j, h, heightArray[i][j], colArray[i][j]));
					}
				}
			}
		}
		
	}
	

	private void calcHeightMapBuildTiles(double diff, double complex) {
		
		// This goes from a random value (e.g. 0.0001) to a value going from 0..NUM_LAYERS*2
		double rescale = (1/complex) * FracConstants.NUM_LAYERS * 2.0d;


		// Pick random points to be random heights - height weighting is down to difficulty level.
		// calculate which of the values are to be heights and set the anchor point height
		double[] randVals = RandomHolder.getRandom().doubles().limit((long)xSize*ySize).map(d -> d < complex ? ((d * rescale) -  FracConstants.NUM_LAYERS) : -100.0d).toArray();
		
		
		// Assign heightmap. A 2D array is appropriate here because we know the size and the array matches the 2D overhead view of the world.
		boolean[][] anchorPoints = new boolean[xSize][ySize];
		double[][] heightArray = new double[xSize][ySize];
		PatchInfoRec[][] colArray = new PatchInfoRec[xSize][ySize];
		int rpos = 0;
		for(int i=0; i<xSize; i++) {
			for(int j=0; j<ySize; j++) {
				if (randVals[rpos] >= -99.0d) {
					anchorPoints[i][j] = true;
					heightArray[i][j] = randVals[rpos];
					colArray[i][j] = new PatchInfoRec(randVals[rpos]/FracConstants.NUM_LAYERS);
				}
				else {
					anchorPoints[i][j] = false;
				}
				rpos++;
			}
		}
			
		// Interpolate the height of each point from anchor points with more weighting to nearer points
		for(int i=0; i<xSize; i++) {
			for(int j=0; j<ySize; j++) {
				if(!anchorPoints[i][j]) {
					heightArray[i][j] = calcPointHeight(i, j, anchorPoints, heightArray);
					colArray[i][j] = getClosestColour(i, j, anchorPoints, colArray);
				}
			}
		}

		buildTiles(heightArray, colArray);
	}
	
	private PatchInfoRec getClosestColour(int x, int y, boolean[][] anchorPoints, PatchInfoRec[][] colArray) {
		
		int dist = 99999999;

		PatchInfoRec retVal = null;
		for(int xp = 0; xp<xSize; xp++ ) {
			for(int yp=0; yp<ySize; yp++) {
				if(anchorPoints[xp][yp]) {
					int dist2 = (xp-x)*(xp-x) + (yp-y)*(yp-y);
					if(dist2 < dist) {
						dist = dist2;
						retVal = colArray[xp][yp];
					}
				}
			}	
		}
		
		return retVal == null ? new PatchInfoRec(1.0d) : retVal;
	}
	
	private record Prec(Double dist, Double height) {}
	
	private double calcPointHeight(int x, int y, boolean[][] anchorPoints, double[][] heightArray) {
		// need to find n closest points
		double retVal = 3.0d;
		
		// Restrict the search area in y, so we don't iterate over the whole world.
		int sy = y-FracConstants.MAX_CLOSEST_POINT_DIST;
		if(sy < 0) {
			sy = 0;
		}
		int ey = y+FracConstants.MAX_CLOSEST_POINT_DIST;
		if(ey >= ySize) {
			ey = ySize-1;
		}
		
		List<Prec> points = new ArrayList<>();
		for(int xp = 0; xp<xSize; xp++ ) {
			for(int yp=sy; yp<ey; yp++) {
				if(anchorPoints[xp][yp]) {
					if(xp == x && yp == y) {
						System.out.println("Serious error, should not be calculating the height of an anchor point");
					}
					else {
						points.add(new Prec(calcDist(x, y, xp, yp) ,heightArray[xp][yp]));
					}
				}
			}
		}
		
		if(!points.isEmpty()) {
			Comparator<Prec> sortDist = (p, q) -> p.dist.compareTo(q.dist()); 
			
			// Find the 5 closest points
			List<Prec> usePoints  = points.stream().sorted(sortDist).limit(FracConstants.NUM_CLOSEST_POINTS).toList();
			
			// Get the total distance sum
			double totalDist = usePoints.stream().mapToDouble(p -> 1.0d/p.dist).sum();
			
			// Weight by 1 over the distance calculated below
			retVal = usePoints.stream().mapToDouble(p -> p.height * (1.0d/p.dist)).sum() / totalDist;
		}
		
		return retVal;
	}

	private double calcDist(int x, int y, int xp, int yp) {
		double xsq = (x-xp) * (double)(x-xp);
		double ysq = (y-yp) * (double)(y-yp);
		
		// double sqrt, adding more weight to closer points than just linear.
		double dist = Math.sqrt(xsq + ysq);
		return Math.sqrt(dist);
	}

	public void render(GraphicsContext gc) {
		gc.clearRect(worldScale, ySize, xSize, worldScale);
		
		tiles.forEach(t -> t.render(gc, worldScale, myCoord));		
	}
	
}
