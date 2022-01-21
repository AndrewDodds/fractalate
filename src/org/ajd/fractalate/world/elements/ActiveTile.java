package org.ajd.fractalate.world.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.ActiveEntityRec;
import org.ajd.fractalate.world.elements.builders.ShapeRenderers;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// A tile that can do something.. (i.e be hostile, be pickup)
public class ActiveTile extends BaseTile {

	private List<Shape> theShapes;
	
	private static final double[][] TURRET_POINTS = {{-0.5d, 0.95d},{0.5d, 0.95d},{0.5d,0.5d},{-0.5d,0.5d}}; 

	private static final double[][] BASE_GUN_POINTS = {{-0.05d, 0.65d},{-0.05d, 1.1d},{0.05d,1.1d},{0.05d,0.65d}}; 

	
	private double targetAngle = 0.0d; 
	
	public ActiveTile(long xpos, long ypos, long depth, ActiveEntityRec initInfo) {
		this.init(xpos, ypos, depth, depth);
		
		buildTile(initInfo);
		
	}
		
	private void buildBaseShape(ActiveEntityRec initInfo, double cSize) {
		
		if(initInfo.getMainNumSides() == 1) { // It's a circle...
			theShapes.add( new Shape(1, 0.9d*cSize*2.0d, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::circlesRenderer).setAsRotateable()); 
		}
		else if(initInfo.getMainNumSides() == 2) { // Rounded Rectangle
			theShapes.add( new Shape(1, 0.9d*cSize*2.0d, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::roundedSquareRenderer).setAsRotateable()); 
		}
		else {
			theShapes.add( new Shape(initInfo.getMainNumSides(), 0.9d*cSize*2.0d, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer).setAsRotateable()); 
			
		}
	}

	private void buildTurretShape(ActiveEntityRec initInfo, double cSize) {
		List<Point2D> points = Arrays.stream(TURRET_POINTS).map(tp -> new Point2D(tp[0], tp[1])).toList();
		theShapes.add( new Shape(4, 1.0d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer, points).setAsRotateable()); 
	}

	private void buildGunShapes(ActiveEntityRec initInfo, double cSize) {
		List<Point2D> points = null;
		switch(initInfo.getNumGuns()) {
		case 1:
			points = Arrays.stream(BASE_GUN_POINTS).map(tp -> new Point2D(tp[0], tp[1])).toList();
			theShapes.add( new Shape(4, 1.0d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer, points).setAsRotateable());
			break;
		case 2:
			points = Arrays.stream(BASE_GUN_POINTS).map(tp -> new Point2D(tp[0]-0.25d, tp[1])).toList();
			theShapes.add( new Shape(4, 1.0d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer, points).setAsRotateable());

			points = Arrays.stream(BASE_GUN_POINTS).map(tp -> new Point2D(tp[0]+0.25d, tp[1])).toList();
			theShapes.add( new Shape(4, 1.0d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer, points).setAsRotateable());
			break;
		case 3:
		default:
			points = Arrays.stream(BASE_GUN_POINTS).map(tp -> new Point2D(tp[0]-0.40d, tp[1])).toList();
			theShapes.add( new Shape(4, 1.0d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer, points).setAsRotateable());

			points = Arrays.stream(BASE_GUN_POINTS).map(tp -> new Point2D(tp[0], tp[1])).toList();
			theShapes.add( new Shape(4, 1.0d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer, points).setAsRotateable());

			points = Arrays.stream(BASE_GUN_POINTS).map(tp -> new Point2D(tp[0]+0.40d, tp[1])).toList();
			theShapes.add( new Shape(4, 1.0d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::polyRenderer, points).setAsRotateable());
			break;		
		}
	}

	private void buildEyeShapes(ActiveEntityRec initInfo, double cSize) {
		double startXPos = 0.0d - (0.15d * (initInfo.getNumEyes()-1));
		double deltaX = 0.0d;
		if(initInfo.getNumEyes() > 1) {
			deltaX = 1.6d/(initInfo.getNumEyes()-1); // Did I mention that I regard the existence of NaN as an abomination ?
		}
		
		
		for(int i=0; i<initInfo.getNumEyes(); i++) {
			theShapes.add(new Shape(1, 0.1d*cSize, 0.0d, new Point2D(startXPos, 0.0d), Arrays.asList(Color.WHITE), true, ShapeRenderers::circlesRenderer).setAsRotateable());
			theShapes.add(new Shape(1, 0.03d*cSize, 0.0d, new Point2D(startXPos, 0.01d), Arrays.asList(Color.BLACK), true, ShapeRenderers::circlesRenderer).setAsRotateable());
			startXPos += deltaX;	
		}
	}

	private void buildTile(ActiveEntityRec initInfo) {		
		theShapes = new ArrayList<>();
		double cSize = FracConstants.TILE_SIZE*depthScale*0.5d;
		
		buildBaseShape(initInfo, cSize);
		
		buildTurretShape(initInfo, cSize);
		
		buildGunShapes(initInfo, cSize);
		
		buildEyeShapes(initInfo, cSize);
		
	}

	public void setRotation(double angle) {
		targetAngle = angle;
	}

	@Override
	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
						
		Coordinate realCenter = this.getRealCenter(gc, scaleFactor, refPos);
		
		if(shouldDisplay(gc.getCanvas().getBoundsInLocal(), realCenter) ) {
			theShapes.forEach(s -> {s.setCoord(realCenter); s.setRotation(targetAngle); s.render(gc);}); 
		}
	}

}


