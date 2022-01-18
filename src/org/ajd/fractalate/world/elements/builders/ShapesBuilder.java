package org.ajd.fractalate.world.elements.builders;

import java.util.List;

import org.ajd.fractalate.world.PatchInfoRec;
import org.ajd.fractalate.world.elements.Shapes;

@FunctionalInterface
public interface ShapesBuilder {
	List<Shapes> buildShapes(PatchInfoRec cRec, double depthScale, long depth);
}
