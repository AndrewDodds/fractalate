package org.ajd.fractalate.world.elements;

import java.util.ArrayList;
import java.util.List;

import org.ajd.fractalate.world.Renderable;
import org.ajd.fractalate.world.elements.builders.Renderer;
import org.ajd.fractalate.world.util.Coordinate;
import org.ajd.fractalate.world.util.PointsAsArrays;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class Shape implements Renderable {

	private List<Point2D> initialPoints;
	
	private List<Point2D> points;
	private Point2D centerPoint;
	private PointsAsArrays paa;
	
	public List<Stop> getColGradStops() {
		return colGradStops;
	}

	public Color getFirstCol() {
		return firstCol;
	}

	public boolean isGradient() {
		return isGradient;
	}

	private List<Stop> colGradStops;
	private Color firstCol;
	private Color highlightCol;
	private boolean isHighlight;
	private double scale;
	private boolean isGradient; 
	private boolean isRotateable;

	private Coordinate renderCoord = new Coordinate(0.0d, 0.0d, 0.0d);
	
	private Renderer r; 
	
	private static final double DEGREES_TO_RADS = 57.295779513d;
	
	
	public Shape(int numSides, double scale, double initialRotation, Point2D origin, List<Color> colours, boolean isHighlight, Renderer r) {
		this(numSides, scale, initialRotation, origin, colours, isHighlight, r, null); 
	}
	
	public Shape(int numSides, double scale, double initialRotation, Point2D origin, List<Color> colours, boolean isHighlight, Renderer r, List<Point2D> definedPoints) {
		if(definedPoints == null || definedPoints.isEmpty()) {
			createShape(numSides, scale, initialRotation, origin);
		}
		else {
			this.points = definedPoints.stream().map(p->new Point2D(p.getX()*scale, p.getY()*scale)).toList();
			this.centerPoint = definedPoints.get(0);
		}
		
		this.isHighlight = isHighlight;
		this.scale = scale;
		this.r=r;
		this.isGradient = false;
		isRotateable = false;
		
		colGradStops = new ArrayList<>();
		if(colours == null) {
			highlightCol = Color.WHITE;		
			firstCol = Color.GRAY;
		}
		else if (colours.size() > 1) {
			this.isGradient = true;
			highlightCol = colours.get(0).brighter().brighter();
			firstCol = colours.get(0);
			double pos = 0.0d;
			double posStep = 1.0d/colours.size();
			for(Color c: colours) {
				colGradStops.add(new Stop(pos, c));
				pos += posStep;
			}
		}
		else {
			highlightCol =  colours.get(0).brighter().brighter();
			firstCol = colours.get(0);
		}
		
	}

	public Shape setAsRotateable() {
		isRotateable = true;
		
		initialPoints = points;
		
		return this;
	}
	
	private Point2D rotateBy(Point2D p, double degrees) {
		double rads = degrees / DEGREES_TO_RADS;
		double newX = (p.getX() * Math.cos(rads)) - (p.getY() * Math.sin(rads));
		double newY = (p.getY() * Math.cos(rads)) + (p.getX() * Math.sin(rads));
		return new Point2D(newX, newY);				
	}
	
	
	//Create a shape according to the scale, centered on the origin.
	public void createShape(int numsides, double scale, double initialRotation, Point2D origin) {
		points =  new ArrayList<>(numsides);
		centerPoint = origin;
		double sideAngle = initialRotation;
		double angleIncrement = 360.0d/numsides;
		for(int i=0; i<numsides; i++ ) {
			Point2D p = rotateBy(new Point2D(0.0d, scale/2.0d), sideAngle); // Calculate a vertex based on 0,half scale (scaled from 0 to 0.5)
			points.add(new Point2D(p.getX() + origin.getX(), p.getY() + origin.getY())); // Translate this vertex to the desired point and add to vertex list. 
			sideAngle += angleIncrement;
		}
		this.paa = new PointsAsArrays(points);
	}

	
	public void setRotation(double rotation) {
		if(!isRotateable) {
			throw new UnsupportedOperationException("Need to set a shape to be rotateable before rotating.");
		}
		points = initialPoints.stream().map(p->rotateBy(p, rotation)).toList();
		paa = new PointsAsArrays(points);
	}
	
	@Override
	public void setCoord(Coordinate newCoord) {
		renderCoord = newCoord;
	}

	

	@Override
	public void render(GraphicsContext gc) {	
		
		r.render(this, gc);		
	}



	public Color getHighlightCol() {
		return highlightCol;
	}

	public boolean isHighlight() {
		return isHighlight;
	}

	public double getScale() {
		return scale;
	}

	public Coordinate getRenderCoord() {
		return renderCoord;
	}
	
	public List<Point2D> getPoints() {
		return points;
	}
	
	public Point2D getCenterPoint () {
		return centerPoint;
	}
	
	public PointsAsArrays getPointsAsArrays() {
		return paa;
	}

	
	
		
}
