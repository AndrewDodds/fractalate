package org.ajd.fractalate.entities;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.Renderable;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;

import javafx.scene.canvas.GraphicsContext;

// Base class for mobile entities
public abstract class BaseEntity implements Renderable {

	protected World w;
	protected long xp;
	protected long yp;
	protected long zp;
	protected double depthScale;
	
	protected double dXPos;
	protected double dYPos;
	protected double dZPos;
	
	protected Point2D velocity;
	
	protected Coordinate currentWorldCoord;
	protected double scaleFactor;
	
	protected void init(long xPos, long yPos, long zPos, double height, World w) {
		this.w = w;
		
		xp = xPos;
		yp = yPos;
		zp = zPos;
		dZPos = height;
		
		calculateWorldPos();

	}
	
	protected void calculateWorldPos() {
		
		depthScale = (dZPos + FracConstants.NUM_LAYERS) /  (2*FracConstants.NUM_LAYERS);	
		dXPos = xp * FracConstants.TILE_SIZE * depthScale;
		dYPos = yp * FracConstants.TILE_SIZE * depthScale;
		
		
	}
	
	protected Coordinate calculateRawCenter(Coordinate refPos) {
		
		// This is the coordinate within the world (shifted by the player position)
		return new Coordinate( dXPos  + (refPos.x() * depthScale),
				              -dYPos - (refPos.y() * depthScale), 
				               FracConstants.TILE_SIZE * depthScale); // tells us how big a tile is..
		
	}
	
	// Translate to get to screen corrdinates
	protected Coordinate getRealCenter(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
		Coordinate  rawCenter = calculateRawCenter(refPos);
		
		double centerX = (rawCenter.x() * scaleFactor) + (gc.getCanvas().getBoundsInLocal().getWidth() / 2.0d);
		double centerY = (rawCenter.y() * scaleFactor)  + gc.getCanvas().getBoundsInLocal().getHeight();
		double tileSize = (rawCenter.z() * scaleFactor);
				
		return new Coordinate(centerX, centerY, tileSize);
	}

	
	protected Point2D screenCoordToWorldCoord(GraphicsContext gc, double scaleFactor, Coordinate refPos, Point2D screenPoint) {
		// Reverse 'realCenter' calculation
		double worldX = (screenPoint.getX() - (gc.getCanvas().getBoundsInLocal().getWidth() / 2.0d)) / scaleFactor;
		double worldY = (screenPoint.getY() - (gc.getCanvas().getBoundsInLocal().getHeight() / 2.0d)) / scaleFactor;
		
		// Reverse 'rawcenter' calculation
		worldX -= (refPos.x() * depthScale);
		worldY = -( worldY + (refPos.y() * depthScale));
				
		return new Point2D(worldX, worldY);
	}
	
	
	
	@Override
	public void update(long timeStepNS) {
		throw new UnsupportedOperationException("Entities should not be using the default implementation of Update");

	}

	@Override
	public void setCoord(Coordinate newCoord) {
		this.currentWorldCoord = newCoord;
	}

	@Override
	public void render(GraphicsContext gc) {
		throw new UnsupportedOperationException("Entities should not be using the default implementation of Update");

	}

}
