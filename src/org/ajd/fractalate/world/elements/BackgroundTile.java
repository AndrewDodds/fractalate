package org.ajd.fractalate.world.elements;

import java.util.Arrays;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.elements.builders.ShapeRenderers;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BackgroundTile extends BaseTile  {

	private Shape theShape;
	
	public BackgroundTile(int xpos, int ypos, Color col1, Color col2, World w) {
		this.init(xpos, ypos, -1, -1.0d, w);
		
		theShape = new Shape(1, 0.1d*FracConstants.TILE_SIZE, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(col1, col2), false, ShapeRenderers::roundedSquareRenderer); 
	}
	

	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
						
		bounds = gc.getCanvas().getBoundsInLocal();

		Coordinate realCenter = this.centerPos.getScreenCoords(bounds, refPos, scaleFactor);

		if(shouldDisplay(realCenter) ) {
			theShape.setCoord(realCenter);
			theShape.render(gc);			
		}
	}


	@Override
	public void update(double timeStepNS) {
		// TODO Auto-generated method stub
		
	}
	

}
