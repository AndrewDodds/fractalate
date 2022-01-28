package org.ajd.fractalate.entities;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends BaseEntity {
	
	
	public Player(long xp, long yp, long zp, double height, World w, Point2D velocity) {
		this.init(xp, yp, zp, height, w);
		
		this.velocity = velocity;
	}

	public void accelerateTo(GraphicsContext gc, double scaleFactor, Point2D screenPoint, long timeStepNS) {
		Point2D worldPos = this.screenCoordToWorldCoord(gc, scaleFactor, currentWorldCoord, screenPoint);
		
		double rate = timeStepNS / FracConstants.TIME_CONSTANT;
		// TODO mutable coordinate
		double vX = velocity.getX();
		double vY = velocity.getY();
		if(worldPos.getX() > this.dXPos) {
			vX += rate;
		}
		else if(worldPos.getX() < this.dXPos) {
			vX -= rate;
		}
		if(worldPos.getY() > this.dYPos) {
			vY += rate;
		}
		if(worldPos.getY() < this.dYPos) {
			vY += rate;
		}
		
		velocity = new Point2D(vX, vY);
	}

	@Override
	public void update(long timeStepNS) {
		double rate = timeStepNS / FracConstants.TIME_CONSTANT;

		this.dXPos += (velocity.getX() * rate);
		this.dYPos += (velocity.getY() * rate);

	}

	@Override
	public void render(GraphicsContext gc) {
		
		 
		gc.setFill(Color.AQUA);
		
		Coordinate drawAt = getRealCenter(gc, 1.0d, this.currentWorldCoord);
		
		gc.fillRoundRect(drawAt.x()- 20.0d,  drawAt.y() - 20.0d, 40.0d, 40.0d, 5.0d, 5.0d);

	}

}
