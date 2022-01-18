package org.ajd.fractalate.world.elements;

import java.util.ArrayList;
import java.util.List;

import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.Renderable;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shapes implements Renderable {

	List<Shape> shapeSet;
	int numSides;
	PatchInfoRec cRec;
	boolean isHighlight;
	
	// So, from a given point we generate a set of shapes that will give us a colour gradient.
	// colourRecord gives us the number of shapes to ceate, which is the same as the number of colours
	public Shapes(int numSides, double scale, double initialRotation, Point2D origin, PatchInfoRec colourRecord, boolean isHighlight){
		this.numSides = numSides;
		this.cRec = colourRecord;
		this.isHighlight = isHighlight;
		
		double scaleStep = scale / colourRecord.getNumcols();
		double initScale = scale;
		
		List<Color> colourSet = colourRecord.getColourBuilder().getColours(colourRecord);
		
		shapeSet = new ArrayList<>();
		for(int i=0; i<colourRecord.getNumcols(); i++) {
			getShapeSet().add(new Shape(numSides, initScale, initialRotation, origin, colourSet.get(i), isHighlight, colourRecord.getRenderer()));
			initScale -= scaleStep;
		}
	}
	
	@Override
	public void setCoord(Coordinate newCoord) {
		getShapeSet().forEach(s -> s.setCoord(newCoord));
	}

	@Override
	public void render(GraphicsContext gc) {
		getShapeSet().forEach(s -> s.render(gc));		
	}

	public List<Shape> getShapeSet() {
		return shapeSet;
	}

	public int getNumSides() {
		return numSides;
	}

	public PatchInfoRec getcRec() {
		return cRec;
	}

	public boolean isHighlight() {
		return isHighlight;
	}
	
}
