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

public class Shape implements Renderable {

	private List<Point2D> points;
	private PointsAsArrays paa;
	private Color col;
	private Color highlightCol;
	private boolean isHighlight;
	private double scale;

	private Coordinate renderCoord = new Coordinate(0.0d, 0.0d, 0.0d);
	
	private Renderer r; 
	
	private static final double DEGREES_TO_RADS = 57.295779513d;
	
	public Shape(int numSides, double scale, double initialRotation, Point2D origin, Color colour, boolean isHighlight, Renderer r) {
		createShape(numSides, scale, initialRotation, origin);
		
		this.isHighlight = isHighlight;
		this.scale = scale;
		this.r=r;
		
		col = colour;
		highlightCol = col.brighter().brighter();
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
		
		double sideAngle = initialRotation;
		double angleIncrement = 360.0d/numsides;
		for(int i=0; i<numsides; i++ ) {
			Point2D p = rotateBy(new Point2D(0.0d, scale/2.0d), sideAngle); // Calculate a vertex based on 0,half scale (scaled from 0 to 0.5)
			points.add(new Point2D(p.getX() + origin.getX(), p.getY() + origin.getY())); // Translate this vertex to the desired point and add to vertex list. 
			sideAngle += angleIncrement;
		}
		this.paa = new PointsAsArrays(points);
	}

	@Override
	public void setCoord(Coordinate newCoord) {
		renderCoord = newCoord;
		
	}

	

	@Override
	public void render(GraphicsContext gc) {
		
		r.render(this, gc);
		
	}

	public Color getCol() {
		return col;
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
	
	public PointsAsArrays getPointsAsArrays() {
		return paa;
	}
	
	
		
}
