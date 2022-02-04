package org.ajd.fractalate.animate;


import org.ajd.fractalate.scenes.MainGameScene;
import org.ajd.fractalate.world.Background;
import org.ajd.fractalate.world.ScoreCard;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public class MainAminationTimer extends AnimationTimer {

    double xPos = 0.0d; 
    double yPos = 0.0d;
    double prevtime = System.nanoTime();
    Point2D screenSize;
    GraphicsContext gc;
    MainGameScene theScene;
	private ScoreCard scoreCard;
	private Background background;
	World world;
    
    public MainAminationTimer(GraphicsContext gcontext, MainGameScene scene, Point2D theScreenSize, World world) {
    	gc = gcontext;
    	theScene = scene;
    	screenSize  = theScreenSize;
    	scoreCard = new ScoreCard(theScreenSize);
    	background = new Background(theScreenSize);
    	this.world = world;
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
    	
        doUpdates((long)(currentNanoTime - prevtime) / 10000000);

        prevtime = currentNanoTime;
        yPos -= t;
        
        
        drawTheScene();
    }
	
	private void doUpdates(long t) {
		world.update(t);
		scoreCard.update(t);		
	}

	private void drawTheScene() {

        Coordinate c =  new Coordinate(xPos, yPos, screenSize.getX() );
        
        background.render(gc, 1.0d, c);
        
        world.setCoord(c);
        world.render(gc);
                
        scoreCard.render(gc, 1.0d, c);
	}

}
