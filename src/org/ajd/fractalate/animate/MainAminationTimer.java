package org.ajd.fractalate.animate;


import org.ajd.fractalate.scenes.MainGameScene;
import org.ajd.fractalate.world.Background;
import org.ajd.fractalate.world.ScoreCard;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


public class MainAminationTimer extends AnimationTimer {

    double deltaX = 0.0d;
    double deltaY = 0.0d;
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
    	scoreCard = new ScoreCard(theScreenSize, world);
    	background = new Background(theScreenSize);
    	theScene.addEventFilter(MouseEvent.MOUSE_MOVED, world::mouseMove);
     	this.world = world;
     	this.world.setScene(scene);
	}
    
	@Override
    public void handle(long currentNanoTime)
    {
		deltaX = 0.0d;
		deltaY = 0.0d;
        double t = (currentNanoTime - prevtime) / 10000000.0; 
        if (theScene.getCurrentInput().contains("LEFT")) {
        	deltaX = (t*5.0d);
        }
        if (theScene.getCurrentInput().contains("RIGHT")) {
        	deltaX = -(t*5.0d);
        }	 
        if (theScene.getCurrentInput().contains("UP")) {
        	deltaY = -(t*5.0d);
        }
        if (theScene.getCurrentInput().contains("DOWN")) {
        	deltaY = (t*5.0d);
        }	 
    	
        doUpdates(t);

        prevtime = currentNanoTime;
        if(deltaY == 0.0d) { 
        	deltaY = -t;
        }

        
        drawTheScene();
    }
	
	private void doUpdates(double t) {
		world.update(t);
		scoreCard.update(t);		
	}

	private void drawTheScene() {

        Coordinate c =  new Coordinate(0.0d, 0.0d, screenSize.getX() );
        
        background.render(gc, 1.0d, c);
        
        world.updateCoord(new Point2D(deltaX, deltaY));
        world.render(gc);
                
        scoreCard.render(gc, 1.0d, c);
	}

}
