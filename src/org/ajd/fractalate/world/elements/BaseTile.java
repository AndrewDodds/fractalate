package org.ajd.fractalate.world.elements;

import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

// Abstract base for Tiles. 
public abstract class BaseTile {
	// place within the overall grid
	protected long xPos; //Negative to positive
	protected long yPos;
	
	protected double height;
		
	protected List<Shape> shapes;
	
	protected int numColours = 0;
	protected double depthScale = 1.0d;	
		
	protected double xOffset = 0.0d;
	
	protected void init(long xpos, long ypos, long depth, double height) {
		this.xPos = xpos;
		this.yPos = ypos;
		this.height = height;
		
		xOffset = -0.5*FracConstants.TILE_SIZE;
		if(yPos % 2 != 0) {
			xOffset += FracConstants.TILE_SIZE;
		}
		

		// depth is a value from 1 to NUM_LAYERS, bigger values are closer 
		// This should give a scale from 1.0 (closest layer) to 0.5 (most distant)
		depthScale = (double)(depth + FracConstants.NUM_LAYERS) /  (double)(2*FracConstants.NUM_LAYERS);	
	}
	
		
	public double getHeight() {
		return height;
	}
		
	protected Coordinate calculateRawCenter(Coordinate refPos) {
				
		// This is the coordinate within the world (shifted by the player position)
		return new Coordinate( ((xPos * FracConstants.TILE_SIZE) + xOffset + refPos.x()) * depthScale,
				             		((-yPos * FracConstants.TILE_SIZE) - refPos.y()) * depthScale, 
				             		FracConstants.TILE_SIZE * depthScale); // tells us how big a tile is..
		
	}
	
	protected Coordinate getRealCenter(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
		Coordinate  rawCenter = calculateRawCenter(refPos);
		
		double centerX = (rawCenter.x() * scaleFactor) + (gc.getCanvas().getBoundsInLocal().getWidth() / 2.0d);
		double centerY = (rawCenter.y() * scaleFactor)  + gc.getCanvas().getBoundsInLocal().getHeight();
		double tileSize = (rawCenter.z() * scaleFactor);
				
		return new Coordinate(centerX, centerY, tileSize);
	}
	
	protected boolean shouldDisplay(Bounds b, Coordinate realCenter) {
		return (b.contains(new Point2D(realCenter.x()+realCenter.z(), realCenter.y())) ||
				b.contains(new Point2D(realCenter.x()-realCenter.z(), realCenter.y())));

	}
	
	public abstract void render(GraphicsContext gc, double scaleFactor, Coordinate refPos);
		
}
