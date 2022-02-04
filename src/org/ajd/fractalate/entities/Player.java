package org.ajd.fractalate.entities;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends BaseEntity {
	
	
	public Player(int xp, int yp, int zp, double height, World w, Point2D velocity) {
		this.init(xp, yp, zp, height, w);
		
		this.velocity = velocity;
	}

	public void accelerateTo(Bounds b, double scaleFactor, Point2D screenPoint, long timeStepNS) {
		Coordinate screenPos = this.entityPosition.getScreenCoords(b, scaleFactor);
				
		double rate = timeStepNS / FracConstants.TIME_CONSTANT;		
		
		double vX = velocity.getX();
		double vY = velocity.getY();
		if(screenPoint.getX() > screenPos.x()) {
			vX += rate;
		}
		else if(screenPoint.getX() < screenPos.x()) {
			vX -= rate;
		}
		if(screenPoint.getY() > screenPos.y()) {
			vY += rate;
		}
		if(screenPoint.getY() < screenPos.y()) {
			vY += rate;
		}
		
		velocity = new Point2D(vX, vY);
	}

	@Override
	public void update(long timeStepNS) {
		double rate = timeStepNS / FracConstants.TIME_CONSTANT;

		Point2D scaledVlocity = new Point2D(velocity.getX() * rate, velocity.getY() * rate);
		this.entityPosition.moveBy(scaledVlocity);
	}

	@Override
	public void render(GraphicsContext gc,  double scaleFactor, Coordinate refPos) {
		
		Bounds b = gc.getCanvas().getBoundsInLocal();
		
		gc.setFill(Color.AQUA);
		
		Coordinate drawAt = this.entityPosition.getScreenCoords(b, scaleFactor);
		
		gc.fillRoundRect(drawAt.x()- 20.0d,  drawAt.y() - 20.0d, 40.0d, 40.0d, 5.0d, 5.0d);

	}

}
