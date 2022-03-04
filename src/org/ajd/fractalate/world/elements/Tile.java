package org.ajd.fractalate.world.elements;


import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.scene.canvas.GraphicsContext;

// Main tile class. 
public class Tile extends BaseTile {
	
	// Constructor. Depth is basically a layer, height an absolute value (used to calculate depth)
	public Tile(int xpos, int ypos, int depth, double height, PatchInfoRec cRec, World w) {
		if(depth < 1 || depth > FracConstants.NUM_LAYERS) {
			depth = 1;
		}

		this.init(xpos, ypos, depth, height, w);
		
		shapes = cRec.getShapesBuilder().buildShapes(cRec, depthScale, depth);
	}	

	
	// Scalefactor will usually be 1. But we also scale according to depth. This means we can zoom in/out if we want..
	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
						
		bounds = gc.getCanvas().getBoundsInLocal();

		Coordinate realCenter = this.centerPos.getScreenCoords(bounds, refPos, scaleFactor);
		
		if(shouldDisplay(realCenter)) {
			
			shapes.forEach(s ->
				{s.setCoord(realCenter); 
				 s.render(gc);});
		}
	}


	@Override
	public void update(double timeStepNS) {
		// TODO Auto-generated method stub
		
	}
	
}
