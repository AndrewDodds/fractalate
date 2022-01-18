package org.ajd.fractalate.animate;

import java.util.SortedMap;
import java.util.TreeMap;

import org.ajd.fractalate.scenes.MainGameScene;
import org.ajd.fractalate.world.Renderable;
import org.ajd.fractalate.world.ScoreCard;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MainAminationTimer extends AnimationTimer {

    double xPos = 0.0d; 
    double yPos = 0.0d;
    double prevtime = System.nanoTime();
    Point2D screenSize;
    GraphicsContext gc;
    MainGameScene theScene;
    
    SortedMap<Integer, Renderable> objectsToDraw = new TreeMap<>();

    public MainAminationTimer(GraphicsContext gcontext, MainGameScene scene, Point2D theScreenSize, World world) {
    	gc = gcontext;
    	theScene = scene;
    	screenSize  = theScreenSize;
    	objectsToDraw.put(10, world);
    	objectsToDraw.put(20, new ScoreCard(theScreenSize));
	}
    
	@Override
    public void handle(long currentNanoTime)
    {
        double t = (currentNanoTime - prevtime) / 10000000.0; 
        if (theScene.getCurrentInput().contains("LEFT")) {
            xPos -= t;
        }
        if (theScene.getCurrentInput().contains("RIGHT")) {
            xPos += t;
        }	 
    	
        prevtime = currentNanoTime;
        yPos -= t;
        
        drawTheScene();
    }
	
	private void drawTheScene() {
		Color bgColour = new Color(0.0d, 0.0d, 0.0d, 1.0d);
		gc.setStroke(bgColour);
		gc.setFill(bgColour);
		
        gc.fillRect(0.0d,  0.0d,  screenSize.getX(), screenSize.getY());

        objectsToDraw.forEach((k, r) -> {r.setCoord(new Coordinate(xPos, yPos, screenSize.getX() )); r.render(gc);});
	}

}
