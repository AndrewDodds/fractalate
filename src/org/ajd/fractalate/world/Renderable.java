package org.ajd.fractalate.world;

import org.ajd.fractalate.world.util.Coordinate;

import javafx.scene.canvas.GraphicsContext;

// This interface only applies to primitives (spahe at the moment)
public interface Renderable {
		
	public void setCoord(Coordinate newCoord);
	
	public void render(GraphicsContext gc);
	
}
