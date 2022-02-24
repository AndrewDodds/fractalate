package org.ajd.fractalate.world;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreCard implements UpdatableRenderable {

	private Point2D screenSize;
	
	public ScoreCard(Point2D theScreenSize)  {
		screenSize = theScreenSize;
	}

	@Override
	public void render(GraphicsContext gc,  double scaleFactor, Coordinate refPos) {
		Color bgColour = new Color(0.0d, 0.0d, 1.0d, 1.0d);
		Color fgColour = new Color(0.0d, 1.0d, 1.0d, 1.0d);
		gc.setFont(new Font(FracConstants.TILE_SIZE / 4.0d));
		gc.setStroke(fgColour);
		gc.setFill(bgColour);
		
        gc.fillRect(0.0d,  0.0d,  screenSize.getX(), FracConstants.TILE_SIZE / 2.0d);
		gc.strokeText("Factalate! ", FracConstants.TILE_SIZE / 2.0d, FracConstants.TILE_SIZE / 2.5d);
		
		bgColour = new Color(0.0d, 0.8d, 0.0d, 1.0d);
		fgColour = new Color(0.7d, 1.0d, 0.7d, 1.0d);
		gc.setStroke(fgColour);
		gc.setFill(bgColour);
		
        gc.fillRect(0.0d,  screenSize.getY()-(FracConstants.TILE_SIZE / 2.0d),  screenSize.getX(),  screenSize.getY());
		gc.strokeText("Current coord: "+refPos.toString(), FracConstants.TILE_SIZE / 2.0d, screenSize.getY()-(FracConstants.TILE_SIZE / 4.0d));
		// TODO add in coordinate of mouse, draw at mouse.
	}

	@Override
	public void update(double timeStepNS) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHeight() {
		return 1000.0d;
	}

}
