package org.ajd.fractalate.world.elements;

import java.util.List;

import org.ajd.fractalate.world.MutableCoordinate;
import org.ajd.fractalate.world.UpdatableRenderable;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;


// Abstract base for Tiles. 
public abstract class BaseTile implements UpdatableRenderable {
	
	protected double height;
	protected MutableCoordinate centerPos;
	protected World world;
	protected Bounds bounds;
	
	protected List<Shape> shapes;
	
	protected int numColours = 0;
	protected double depthScale = 1.0d;	
			
	protected void init(int xpos, int ypos, int depth, double height, World w) {
		this.height = height;
		this.world = w;
		
		centerPos = new MutableCoordinate(xpos, ypos, depth);
		
	}
		
	public double getHeight() {
		return height;
	}
		
	
	protected boolean shouldDisplay(Coordinate realCenter) {
		if(bounds == null) {
			return false;
		}
		
		return (bounds.contains(new Point2D(realCenter.x()+realCenter.z(), realCenter.y())) ||
				bounds.contains(new Point2D(realCenter.x()-realCenter.z(), realCenter.y())));

	}
			
}
