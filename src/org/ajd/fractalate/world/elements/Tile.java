package org.ajd.fractalate.world.elements;


import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.scene.canvas.GraphicsContext;

// Main tile class. 
public class Tile extends BaseTile {
	
	// Constructor. Depth is basically a layer, height an absolute value (used to calculate depth)
	public Tile(long xpos, long ypos, long depth, double height, PatchInfoRec cRec) {
		if(depth < 1 || depth > FracConstants.NUM_LAYERS) {
			depth = 1L;
		}
		

		this.init(xpos, ypos, depth, height);
		
		shapes = cRec.getShapesBuilder().buildShapes(cRec, depthScale, depth);
	}
	
		

	
	// Scalefactor will usually be 1. But we also scale according to depth. This means we can zoom in/out if we want..
	@Override
	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
						
		Coordinate realCenter = this.getRealCenter(gc, scaleFactor, refPos);
	
		
		if(shouldDisplay(gc.getCanvas().getBoundsInLocal(), realCenter)) {
			
			shapes.forEach(s ->
				{s.setCoord(realCenter); 
				 s.render(gc);});
		}
	}
	
}
