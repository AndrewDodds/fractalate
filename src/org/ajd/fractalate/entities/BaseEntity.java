package org.ajd.fractalate.entities;

import org.ajd.fractalate.world.MutableCoordinate;
import org.ajd.fractalate.world.UpdatableRenderable;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;

import javafx.scene.canvas.GraphicsContext;

// Base class for mobile entities
public abstract class BaseEntity implements UpdatableRenderable {

	protected World w;

	protected double height;
	
	protected Point2D velocity;
	
	protected Coordinate currentWorldCoord;
	protected double scaleFactor= 1.0d;
	
	MutableCoordinate entityPosition;
	
	protected void init(int xPos, int yPos, int zPos, double height, World w) {
		this.w = w;
		
		entityPosition = new MutableCoordinate(xPos, yPos, zPos);
		
		this.height = height;
		
	}
		
	@Override
	public double getHeight() {
		return height;
	}
	
	@Override
	public void update(double timeStepNS) {
		throw new UnsupportedOperationException("Entities should not be using the default implementation of Update");

	}


	@Override
	public void render(GraphicsContext gc,  double scaleFactor, Coordinate refPos) {
		throw new UnsupportedOperationException("Entities should not be using the default implementation of Render");

	}

}
