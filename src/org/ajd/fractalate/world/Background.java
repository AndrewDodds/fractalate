package org.ajd.fractalate.world;


import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Background implements UpdatableRenderable {

	private Point2D screenSize;
	
	public Background(Point2D theScreenSize)  {
		screenSize = theScreenSize;
	}

	@Override
	public void render(GraphicsContext gc,  double scaleFactor, Coordinate refPos) {
		
		Color bgColour = new Color(0.0d, 0.0d, 0.0d, 1.0d);
		gc.setStroke(bgColour);
		gc.setFill(bgColour);
		
        gc.fillRect(0.0d,  0.0d,  screenSize.getX(), screenSize.getY());

	}


	@Override
	public void update(double timeStepNS) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHeight() {
		return -1000.0d;
	}

}
