package org.ajd.fractalate.world.elements;

import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

// Main tile class. TODO interface/subclass to get a variety (Enumerate so as to allow ranadom selection?)
public class Tile {
	// place within the overall grid
	private long xPos; //Negative to positive
	private long yPos;
	
	private double height;
		
	private List<Shapes> shapes;
	
	int numColours = 0;
	double depthScale = 1.0d;	
	
	private Coordinate rawCenter;
	
	private double xOffset = 0.0d;
	
	// Constructor. Depth is basically a layer, height an absolute value (used to calculate depth)
	public Tile(long xpos, long ypos, long depth, double height, PatchInfoRec cRec) {
		this.xPos = xpos;
		this.yPos = ypos;
		this.height = height;
		
		if(depth < 1 || depth > FracConstants.NUM_LAYERS) {
			depth = 1L;
		}
		
		xOffset = -0.5*FracConstants.TILE_SIZE;
		if(yPos % 2 != 0) {
			xOffset += FracConstants.TILE_SIZE;
		}
		

		// depth is a value from 1 to NUM_LAYERS, bigger values are closer 
		// This should give a scale from 1.0 (closest layer) to 0.5 (most distant)
		depthScale = (double)(depth + FracConstants.NUM_LAYERS) /  (double)(2*FracConstants.NUM_LAYERS);	
		
		shapes = cRec.getShapesBuilder().buildShapes(cRec, depthScale, depth);
	}
	
	public double getHeight() {
		return height;
	}
		
	private void calculateRawCenter(Coordinate refPos) {
				
		// This is the coordinate within the world (shifted by the player position)
		rawCenter = new Coordinate( ((xPos * FracConstants.TILE_SIZE) + xOffset + refPos.x()) * depthScale,
				             		((-yPos * FracConstants.TILE_SIZE) - refPos.y()) * depthScale, 
				             		FracConstants.TILE_SIZE * depthScale); // tells us how big a tile is..
		
	}
		
	
	// Scalefactor will usually be 1. But we also scale according to depth. This means we can zoom in/out if we want..
	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
		calculateRawCenter(refPos);
		
	  //procedural rendering, eventually.	
		double centerX = (rawCenter.x() * scaleFactor) + (gc.getCanvas().getBoundsInLocal().getWidth() / 2.0d);
		double centerY = (rawCenter.y() * scaleFactor)  + gc.getCanvas().getBoundsInLocal().getHeight();
		double tileSize = (rawCenter.z() * scaleFactor);
						
		if(gc.getCanvas().getBoundsInLocal().contains(new Point2D(centerX-tileSize, centerY))
				||gc.getCanvas().getBoundsInLocal().contains(new Point2D(centerX+tileSize, centerY))) {
			
			shapes.forEach(s ->
				{s.setCoord(new Coordinate(centerX, centerY, tileSize)); 
				 s.render(gc);});
		}
	}
	
}
