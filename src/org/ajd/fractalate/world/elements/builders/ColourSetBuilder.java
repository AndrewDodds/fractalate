package org.ajd.fractalate.world.elements.builders;

import java.util.List;

import org.ajd.fractalate.world.PatchInfoRec;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface ColourSetBuilder {
	List<Color> getColours(PatchInfoRec crec, long depth);
} 
