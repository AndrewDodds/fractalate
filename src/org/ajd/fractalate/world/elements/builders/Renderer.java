package org.ajd.fractalate.world.elements.builders;

import org.ajd.fractalate.world.elements.Shape;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface Renderer {
	void render(Shape s, GraphicsContext gc);
}
