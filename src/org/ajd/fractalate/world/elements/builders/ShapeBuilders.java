package org.ajd.fractalate.world.elements.builders;

import java.util.ArrayList;
import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.RandomHolder;
import org.ajd.fractalate.world.elements.Shapes;

import javafx.geometry.Point2D;

public class ShapeBuilders {

	private ShapeBuilders() {
		// This is a set of shapesbuilders to use, don't instantiate
	}

	private static List<Shapes> getShapeSetsFromPoints(Shapes ts, double scale, double initialRotation) {		
		return ts.getShapeSet().get(0).getPoints().stream().map(p -> new Shapes(ts.getNumSides(), scale, initialRotation, p, ts.getcRec(), ts.isHighlight())).toList();
	}


	public static List<Shapes> buildCircleFlairShapes(PatchInfoRec cRec, double depthScale, long depth) {

		double initScale = FracConstants.TILE_SIZE * depthScale * 0.7d;
		double initialRotation = RandomHolder.getRandom().nextDouble(90.0d);
			
		List<Shapes> shapes = new ArrayList<>();
		
		List<Shapes> tempShapes = new ArrayList<>();
		tempShapes.add(new Shapes(cRec.getNumcols(), initScale, initialRotation, new Point2D(0.0d, 0.0d), cRec, depth >= FracConstants.GROUND_LEVEL_LAYER));
		for(int i=1; i<cRec.getNumIters(); i++) {
			shapes.addAll(tempShapes);
			List<Shapes> tempShapes2 = new ArrayList<>();
			double nextScale = initScale*0.3d; // TODO parameterise
			double nextInitialRotation = initialRotation + 35.0d; //TODO  
			tempShapes.forEach(ts -> tempShapes2.addAll(getShapeSetsFromPoints(ts, nextScale,nextInitialRotation)));
			tempShapes = tempShapes2;
			initScale = nextScale;
			initialRotation = nextInitialRotation;
		}
		
		return shapes;
		
	}

	// Make all of this parameterised..
	public static List<Shapes> buildSquareShapes(PatchInfoRec cRec, double depthScale, long depth) {

		double initScale = FracConstants.TILE_SIZE * depthScale * 1.4142d; // make the rectangle fill the area..
			
		List<Shapes> shapes = new ArrayList<>();
		
		shapes.add(new Shapes(4, initScale, 45.0d, new Point2D(0.0d, 0.0d), cRec, depth >= FracConstants.GROUND_LEVEL_LAYER));
				
		return shapes;
		
	}


}
