package org.ajd.fractalate.world.elements.builders;

import java.util.List;

import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.elements.Shape;

@FunctionalInterface
public interface ShapeBuilder {
	List<Shape> buildShapes(PatchInfoRec cRec, double depthScale, long depth);
}
