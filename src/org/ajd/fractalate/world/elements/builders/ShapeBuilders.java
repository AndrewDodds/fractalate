package org.ajd.fractalate.world.elements.builders;

import java.util.ArrayList;
import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.RandomHolder;
import org.ajd.fractalate.world.elements.Shape;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class ShapeBuilders {

	private ShapeBuilders() {
		// This is a set of shapesbuilders to use, don't instantiate
	}

	private static List<Shape> getShapeSetsFromPoints(PatchInfoRec cRec, Shape ts, double scale, double initialRotation, List<Color> colours) {		
		return ts.getPoints().stream().map(p -> new Shape(cRec.getNumsides(), scale, initialRotation, p, colours, ts.isHighlight(), cRec.getRenderer())).toList();
	}


	public static List<Shape> buildCircleFlairShapes(PatchInfoRec cRec, double depthScale, long depth) {

		double initScale = FracConstants.TILE_SIZE * depthScale * 0.7d;
		double initialRotation = RandomHolder.getRandom().nextDouble(90.0d);
			
		List<Shape> shapes = new ArrayList<>();
		
		List<Shape> tempShapes = new ArrayList<>();
		
		List<Color> colours = cRec.getColourBuilder().getColours(cRec, depth);
		tempShapes.add(new Shape(cRec.getNumsides(), FracConstants.TILE_SIZE * depthScale, initialRotation, new Point2D(0.0d, 0.0d), colours, depth >= FracConstants.GROUND_LEVEL_LAYER, cRec.getRenderer()));
	
		for(int i=1; i<cRec.getNumIters(); i++) {
			shapes.addAll(tempShapes);
			List<Shape> tempShapes2 = new ArrayList<>();
			double nextScale = initScale*0.3d; // TODO parameterise
			double nextInitialRotation = initialRotation + 35.0d; //TODO  
			tempShapes.forEach(ts -> tempShapes2.addAll(getShapeSetsFromPoints(cRec, ts, nextScale,nextInitialRotation, colours)));
			tempShapes = tempShapes2;
			initScale = nextScale;
			initialRotation = nextInitialRotation;
		}
		
		return shapes;
		
	}

	// Make all of this parameterised..
	public static List<Shape> buildSquareShapes(PatchInfoRec cRec, double depthScale, long depth) {

		double initScale = FracConstants.TILE_SIZE * depthScale * 1.4142d; // make the rectangle fill the area..
			
		List<Shape> shapes = new ArrayList<>();
		
		List<Color> colours = cRec.getColourBuilder().getColours(cRec, depth);
		shapes.add(new Shape(4, initScale, 45.0d, new Point2D(0.0d, 0.0d), colours, depth >= FracConstants.GROUND_LEVEL_LAYER, cRec.getRenderer()));
				
		return shapes;
		
	}

	
	
}
