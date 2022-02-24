package org.ajd.fractalate.world;

import org.ajd.fractalate.world.util.Coordinate;

import javafx.scene.canvas.GraphicsContext;

// TODO
// Make all tiles and entities updateable and renderable
// So we can collect all renderables and z-sort them
public interface UpdatableRenderable {
	
	public void update(double t2);
	
	public double getHeight();
		
	public void render(GraphicsContext gc,  double scaleFactor, Coordinate refPos);
	
}
