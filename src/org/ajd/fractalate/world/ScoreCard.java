package org.ajd.fractalate.world;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreCard implements Renderable {

	private Point2D screenSize;
	private Coordinate myCoord;
	
	public ScoreCard(Point2D theScreenSize)  {
		screenSize = theScreenSize;
	}

	@Override
	public void render(GraphicsContext gc) {
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
		gc.strokeText("Current coord: "+myCoord.toString(), FracConstants.TILE_SIZE / 2.0d, screenSize.getY()-(FracConstants.TILE_SIZE / 4.0d));
	}

	@Override
	public void setCoord(Coordinate newCoord) {
		myCoord = newCoord;
	}

}
