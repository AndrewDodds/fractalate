package org.ajd.fractalate.world.elements;

import java.util.Arrays;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.elements.builders.ShapeRenderers;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BackgroundTile extends BaseTile  {

	private Shape theShape;
	
	public BackgroundTile(long xpos, long ypos, Color col1, Color col2) {
		this.init(xpos, ypos, -1, -1.0d);
		
		theShape = new Shape(1, 0.1d*FracConstants.TILE_SIZE, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(col1, col2), false, ShapeRenderers::roundedSquareRenderer); 
	}
	

	@Override
	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
						
		Coordinate realCenter = this.getRealCenter(gc, scaleFactor, refPos);
		
		if(shouldDisplay(gc.getCanvas().getBoundsInLocal(), realCenter) ) {
			theShape.setCoord(realCenter);
			theShape.render(gc);			
		}
	}
	

}
