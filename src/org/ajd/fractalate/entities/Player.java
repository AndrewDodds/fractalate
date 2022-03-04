package org.ajd.fractalate.entities;

import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Player extends BaseEntity {
	
	Bounds b = null;
	
	public Player(int xp, int yp, int zp, double height, World w) {
		this.init(xp, yp, zp, height, w);
		
		this.velocity = new Point2D(0.0d, 0.0d);
	}

	@Override
	public void update(double timeStepNS) {

		double deltaX = 0.0d;
		double deltaY = 0.0d;
        if (this.w.getScene().getCurrentInput().contains("LEFT")) {
        	deltaX = -(timeStepNS*2.5d);
        }
        if (this.w.getScene().getCurrentInput().contains("RIGHT")) {
        	deltaX = (timeStepNS*2.5d);
        }	 
        if (this.w.getScene().getCurrentInput().contains("UP")) {
        	deltaY = (timeStepNS*2.5d);
        }
        if (this.w.getScene().getCurrentInput().contains("DOWN")) {
        	deltaY = -(timeStepNS*2.5d);
        }	 
        if(deltaY == 0.0d) {
        	deltaY =  timeStepNS/2.0d;  
        }
        Point2D scaledVelocity = new Point2D(deltaX, deltaY);
 
        this.entityPosition.moveBy(scaledVelocity);
	}

	@Override
	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
		this.scaleFactor = scaleFactor;
		this.currentWorldCoord = refPos;
		b = gc.getCanvas().getBoundsInLocal();
		
		gc.setFill(Color.AQUA);
		
		Coordinate drawAt = this.entityPosition.getScreenCoords(b, refPos, scaleFactor);
		
		gc.fillRoundRect(drawAt.x()- 20.0d,  drawAt.y() - 20.0d, 40.0d, 40.0d, 5.0d, 5.0d);
		
	}

}
