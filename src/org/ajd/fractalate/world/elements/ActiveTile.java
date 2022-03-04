package org.ajd.fractalate.world.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.ActiveEntityRec;
import org.ajd.fractalate.world.MutableCoordinate;
import org.ajd.fractalate.world.RandomHolder;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.elements.builders.ShapeRenderers;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// A tile that can do something.. (i.e be hostile, be pickup)
public class ActiveTile extends BaseTile {

	private List<Shape> theShapes;
	
	private static final double[][] TURRET_POINTS = {{-0.5d, 0.95d},{0.5d, 0.95d},{0.5d,0.5d},{-0.5d,0.5d}}; 

	private static final double[][] BASE_GUN_POINTS = {{-0.05d, 0.65d},{-0.05d, 1.1d},{0.05d,1.1d},{0.05d,0.65d}}; 

	
	private double targetAngle = 0.0d; 
	
	private double angleChangeSpeed = 0.0d;
	
	private double actualAngle = 0.0d;
	
	enum TileType {STATIC, CONTINUOUS, TRACKING}
	TileType type;
	
	public ActiveTile(int xpos, int ypos, int depth, double height, ActiveEntityRec initInfo, World w) {
		this.init(xpos, ypos, depth, height, w);
		
		buildTile(initInfo);
		
		setInitialRotation();
		
	}
		
	private void setInitialRotation() {
		switch(RandomHolder.getRandom().nextInt(3)) {
		case 0: //Static
			type = TileType.STATIC;
			actualAngle = RandomHolder.getRandom().nextDouble(360.0d);
			break;
		case 1: //Rotate
			targetAngle = RandomHolder.getRandom().nextDouble(360.0d);
			angleChangeSpeed = RandomHolder.getRandom().nextDouble()+0.1d;
			if(RandomHolder.getRandom().nextBoolean()) {
				angleChangeSpeed = -angleChangeSpeed;
			}
			type = TileType.CONTINUOUS;
			break;
		case 2: 
		default:
			actualAngle = RandomHolder.getRandom().nextDouble(360.0d);
			type = TileType.TRACKING;
			break;
		}
		
	}

	private void buildBaseShape(ActiveEntityRec initInfo, double cSize) {
		
		if(initInfo.getMainNumSides() == 1) { // It's a circle...
			theShapes.add( new Shape(1, 0.9d*cSize*2.0d, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::circlesRenderer).setAsRotateable()); 
		}
		else if(initInfo.getMainNumSides() == 2) { // Rounded Rectangle
			theShapes.add( new Shape(1, 0.9d*cSize*2.0d, 0.0d, new Point2D(0.0d, -cSize), Arrays.asList(initInfo.getColourGradientStops().get(0)), true, ShapeRenderers::roundedSquareRenderer).setAsRotateable()); 
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

	// should be evenly spaced from -0.4 to +0.4
	private void buildEyeShapes(ActiveEntityRec initInfo, double cSize) {

		double startXPos = -(0.4d * cSize);
		double deltaX = 0.0d;
		if(initInfo.getNumEyes() > 1) {
			deltaX = (0.8d/(initInfo.getNumEyes()-1)) * cSize; 
		}// Did I mention that I regard the existence of NaN as an abomination ?
		else {
			 startXPos = 0.0d;
		}
		for(int i=0; i<initInfo.getNumEyes(); i++) {
			theShapes.add(new Shape(1, 0.1d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(Color.WHITE), true, ShapeRenderers::circlesRenderer, Arrays.asList(new Point2D(startXPos, 0.0d))).setAsRotateable());
			theShapes.add(new Shape(1, 0.03d*cSize, 0.0d, new Point2D(0.0d, 0.0d), Arrays.asList(Color.BLACK), true, ShapeRenderers::circlesRenderer, Arrays.asList(new Point2D(startXPos, 0.0d))).setAsRotateable());
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

	private void labelThis(GraphicsContext gc, Coordinate realCenter) {
		Color bgColour = Color.BLACK;
		Color fgColour = Color.WHITE;
		gc.setFont(new Font(FracConstants.TILE_SIZE / 10.0d));
		gc.setStroke(fgColour);
		gc.setFill(bgColour);

		gc.strokeText("TargetAngle: "+targetAngle + " ActualAngle: "+actualAngle, realCenter.x(), realCenter.y());

	}

	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
						
		bounds = gc.getCanvas().getBoundsInLocal();

		Coordinate realCenter = this.centerPos.getScreenCoords(bounds, refPos, scaleFactor);
		
		if(shouldDisplay(realCenter) ) {
			theShapes.forEach(s -> {s.setCoord(realCenter); s.setRotation(actualAngle); s.render(gc);}); 
		}
		
	//	labelThis(gc, realCenter);
	}

	@Override
	public void update(double timeStepNS) {
			
		switch(type) {
		case CONTINUOUS:
			actualAngle += (timeStepNS * angleChangeSpeed);
			break;
		case TRACKING:
			actualAngle += angleDiff(this.centerPos, this.world.getPlayer().getPos(), timeStepNS);
			break;
		default:
			break;
		}
		
	}

	private double angleDiff(MutableCoordinate centerPos, MutableCoordinate pos, double timeStepNS) {
				
		double dX = pos.getLastScreenPos().getX() - centerPos.getLastScreenPos().getX();
		double dY = pos.getLastScreenPos().getY() - centerPos.getLastScreenPos().getY();
		
		double hyp = Math.sqrt((dX*dX) + (dY*dY));
		
		if(hyp < 0.0001d) {
			return 0.0d;
		}
		
		targetAngle = Math.asin(dY/hyp) ;

		if(dX > 0.0d) {
			targetAngle = Math.asin(dY/hyp) ;
		}
		else  {
			if(dY > 0.0d) {
				targetAngle = Math.acos(dX/hyp);
			}
			else {
				targetAngle = Math.acos(-dX/hyp) + 3.14159d;
			}
		}

		
		targetAngle = ( targetAngle * FracConstants.DEGREES_TO_RADS) - 90.0d;
		
		double diff = Math.sqrt((targetAngle - actualAngle)*(targetAngle - actualAngle));
		
		if (diff > (timeStepNS / FracConstants.ACTIVETRACKER_SPEEDDIV)) {
			diff = timeStepNS / FracConstants.ACTIVETRACKER_SPEEDDIV;
		}
		
		if(targetAngle > actualAngle) {
			return diff;
		}
		
		return -diff;
	}



}



