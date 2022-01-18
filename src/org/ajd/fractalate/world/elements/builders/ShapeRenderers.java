package org.ajd.fractalate.world.elements.builders;

import org.ajd.fractalate.world.elements.Shape;

import javafx.scene.canvas.GraphicsContext;

public class ShapeRenderers {

	private ShapeRenderers() {
		// private constructor, this is not instantiated..
	}
	
	// Draw a square, centered on each point given 
	public static void squareRenderer(Shape s, GraphicsContext gc) {
		gc.setFill(s.getCol());

		// Draw a square exactly at 
		double offset = s.getScale() / 2.0d;
		s.getPoints().forEach((p) -> gc.fillRect(p.getX()+s.getRenderCoord().x()- offset,  p.getY() + s.getRenderCoord().y() - offset, s.getScale(), s.getScale())); 
				
		if(s.isHighlight()) {
			gc.setStroke(s.getHighlightCol());
			s.getPoints().forEach((p) -> gc.strokeRect(p.getX()+s.getRenderCoord().x()- offset,  p.getY() + s.getRenderCoord().y() - offset, s.getScale(), s.getScale())); 
		}
	}

	// Draw a circle centered on each point given for the shape
	public static void circlesRenderer(Shape s, GraphicsContext gc) {
		gc.setFill(s.getCol());
		
		// Circle rendering
		// Plug in renderers - for example, this one plots a circle at each point generated previously.
		double offset = s.getScale() / 2.0d;
		s.getPoints().forEach((p) -> gc.fillOval( p.getX()+s.getRenderCoord().x()- offset,  p.getY() + s.getRenderCoord().y() - offset, s.getScale(), s.getScale()));
				
		if(s.isHighlight()) {
			gc.setStroke(s.getHighlightCol());
			s.getPoints().forEach((p) -> gc.strokeOval( p.getX() + s.getRenderCoord().x() - offset,  p.getY() + s.getRenderCoord().y() - offset, s.getScale(), s.getScale()));
		}	

	}

	// Draw a polygon, from all the points 
	public static void polyRenderer(Shape s, GraphicsContext gc) {
		gc.setFill(s.getCol());

		// Draw a square exactly at 
		double[] xp = s.getPointsAsArrays().getXPoints().clone();
		double[] yp = s.getPointsAsArrays().getYPoints().clone();
		for(int i=0; i<xp.length; i++) {
			xp[i] += s.getRenderCoord().x();
			yp[i] += s.getRenderCoord().y();
		}
		gc.fillPolygon(xp, yp, xp.length);
		
		if(s.isHighlight()) {
			gc.setStroke(s.getHighlightCol());
			gc.strokePolygon(xp, yp, xp.length);
		}
	}


}
