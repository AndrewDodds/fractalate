package org.ajd.fractalate.world.util;

import java.util.List;

import javafx.geometry.Point2D;

public class PointsAsArrays {
	private double[] xpoints;
	private double[] ypoints;
	List<Point2D> pts;
	
	public PointsAsArrays(List<Point2D> points) {
		pts = points;
		// calculate the arrays once on a lazy basis, they are not always needed
	}
	
	public double[] getXPoints() {
		if(xpoints != null) {
			return xpoints;
		}
		xpoints = pts.stream().mapToDouble(Point2D::getX).toArray();
		return xpoints;
	}
	
	public double[] getYPoints() {
		if(ypoints != null) {
			return ypoints;
		}
		ypoints = pts.stream().mapToDouble(Point2D::getY).toArray();
		return ypoints;
	}
}
