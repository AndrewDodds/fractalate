package org.ajd.fractalate.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.entities.BaseEntity;
import org.ajd.fractalate.entities.Player;
import org.ajd.fractalate.entities.TargetReticle;
import org.ajd.fractalate.scenes.MainGameScene;
import org.ajd.fractalate.world.elements.ActiveTile;
import org.ajd.fractalate.world.elements.BackgroundTile;
import org.ajd.fractalate.world.elements.BaseTile;
import org.ajd.fractalate.world.elements.Tile;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class World  {
	private List<BaseTile> tiles;
	private List<BackgroundTile> backgroundTiles;
	
	private int xSize = (int) ((FracConstants.MAX_X - FracConstants.MIN_X) + 1);
	private int ySize = (int) ((FracConstants.MAX_Y - FracConstants.MIN_Y) + 1);
	
	private double worldScale = 1.0d;
	private MutableCoordinate myCoord = new MutableCoordinate(0, 0, 0);
	
	private List<BaseEntity> entities = new ArrayList<>();
	
	private Point2D mousePos = new Point2D(0.0d, 0.0d);
	private MainGameScene scene;
	private Player player;

	public World(double difficulty, long seed) {
		tiles = new ArrayList<>();
		
		RandomHolder.initRandom(seed);
		
		buildBackgroundTiles();
		
		calcHeightMapBuildTiles(difficulty, FracConstants.BASE_COMPLEXITY);
		
		addPlayerEntity();
		addReticleEntity();
				
	}
	
	private void addPlayerEntity() {
		player = new Player(0, 1, (int) FracConstants.NUM_LAYERS, FracConstants.NUM_LAYERS + 0.1d, this);
		entities.add(player);
		
	}

	private void addReticleEntity() {
		entities.add(new TargetReticle(0, 2, (int) FracConstants.NUM_LAYERS, FracConstants.NUM_LAYERS + 0.2d, this));
		
	}

	public void updateCoord(Point2D delta) {
		myCoord.moveBy(delta);
	}
	
	private void checkAndAddActiveTile(double height, int xPos, int yPos, int zPos, double difficulty) {
		if((height <  (zPos+1) && height <= FracConstants.GROUND_LEVEL_LAYER) && (RandomHolder.getRandom().nextDouble() < (FracConstants.ACTIVE_TILE_FREQ*difficulty))) {
			// This is the 'top' of a stack		
			tiles.add(new ActiveTile(xPos, yPos, zPos+1,  height+0.1d, new ActiveEntityRec(difficulty), this));
		}							
		
	}
	
	private void buildTiles(double[][] heightArray, PatchInfoRec[][] colArray, double difficulty) {
		// Create all tiles - start from the bottom, so ArrayList ordering should(!) give us free z buffering
		// And here we build the list of tiles from the bottom up, so we don't need to worry about Z buffering later
		tiles = new ArrayList<>();
		for(int i=0; i<xSize; i++) {
			for(int j=0; j<ySize; j++) {
				for(int h = 1; h<=FracConstants.NUM_LAYERS; h++) {
					if(heightArray[i][j] > h) {
						double high = (heightArray[i][j] > h + 1) ? h : heightArray[i][j];
						tiles.add( new Tile(i-(xSize/2), j, h, high, colArray[i][j], this));
						checkAndAddActiveTile(heightArray[i][j], i-(xSize/2), j, h, difficulty);
					}
					else {
						break;
					}
				}
			}
		}
				
		tiles.sort(Comparator.comparing(BaseTile::getHeight));
		
	}
	
	private void buildBackgroundTiles() {
		
		// Create background tiles
		backgroundTiles = new ArrayList<>();
		for(int j=0; j<ySize; j++) {
			int colIdx1 = RandomHolder.getRandom().nextInt(FracConstants.NUM_RAINBOW_COLOURS);
			Color col1 = new Color(FracConstants.RAINBOWCOLOURS[colIdx1][0],FracConstants.RAINBOWCOLOURS[colIdx1][1],FracConstants.RAINBOWCOLOURS[colIdx1][2], 1.0d);
			int colIdx2 = RandomHolder.getRandom().nextInt(FracConstants.NUM_RAINBOW_COLOURS);
			while(colIdx1 != colIdx2) {
				colIdx2 = RandomHolder.getRandom().nextInt(FracConstants.NUM_RAINBOW_COLOURS);
			}
			Color col2 = new Color(FracConstants.RAINBOWCOLOURS[colIdx2][0],FracConstants.RAINBOWCOLOURS[colIdx2][1],FracConstants.RAINBOWCOLOURS[colIdx2][2], 1.0d);
			for(int i=0; i<xSize; i++) {
				backgroundTiles.add( new BackgroundTile(i-(xSize/2), j, col1, col2, this));
			}
		}
	}
		
	

	private void calcHeightMapBuildTiles(double diff, double complex) {
		
		// This goes from a random value (e.g. 0.0001) to a value going from 0..NUM_LAYERS*2
		double rescale = (1/complex) * FracConstants.NUM_LAYERS * 2.0d;


		// Pick random points to be random heights - height weighting is down to difficulty level.
		// calculate which of the values are to be heights and set the anchor point height
		double[] randVals = RandomHolder.getRandom().doubles().limit((long)xSize*ySize).map(d -> d < complex ? ((d * rescale) -  FracConstants.NUM_LAYERS + diff) : -100.0d).toArray();
		
		
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
					colArray[i][j] = new PatchInfoRec(1.0d);
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

		buildTiles(heightArray, colArray, diff);
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
		
		Coordinate c = new Coordinate( myCoord.getWorldPos().getX(), myCoord.getWorldPos().getY(), myCoord.getWorldPos().getZ());
		backgroundTiles.forEach(t -> t.render(gc, worldScale, c));					
		
		tiles.forEach(t -> t.render(gc, worldScale, c));		
		
		entities.forEach(t -> t.render(gc, worldScale, c));	
	}


	public void update(double t2) {
		tiles.forEach(t -> t.update(t2));
		
		entities.forEach(t -> t.update(t2));
		
	}

	public void mouseMove(MouseEvent e) {
		mousePos = new Point2D(e.getSceneX(), e.getSceneY());
	}
	
	public Point2D getMousePos() {
		return mousePos;
	}

	public void setScene(MainGameScene scene) {
		this.scene = scene;
	}
	
	public MainGameScene getScene() {
		return scene;
	}

	public Player getPlayer() {
		return player;
	}

}
