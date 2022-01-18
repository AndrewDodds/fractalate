package org.ajd.fractalate.world;

import org.ajd.fractalate.world.util.Coordinate;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
	
	public void setCoord(Coordinate newCoord);
	
	public void render(GraphicsContext gc);
	
}
