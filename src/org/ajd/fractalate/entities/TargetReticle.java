package org.ajd.fractalate.entities;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.World;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TargetReticle extends BaseEntity {
	
	Bounds b = null;
	
	public TargetReticle(int xp, int yp, int zp, double height, World w) {
		this.init(xp, yp, zp, height, w);
		
		this.velocity = new Point2D(0.0d, 0.0d);
	}

	public void accelerateTo(Point2D screenPoint, double rate) {
		if(b == null) {
			return;
		}
		Coordinate screenPos = this.entityPosition.getScreenCoords(b, currentWorldCoord, scaleFactor);
								
		double vX = velocity.getX();
		double vY = velocity.getY();
		double diffX = Math.sqrt( (screenPoint.getX() - screenPos.x()) * (screenPoint.getX() - screenPos.x())) / b.getWidth();
		double diffY = Math.sqrt( (screenPoint.getY() - screenPos.y()) * (screenPoint.getY() - screenPos.y())) / b.getHeight();
		if(screenPoint.getX() > screenPos.x()) {
			vX += (rate * diffX);
		}
		else if(screenPoint.getX() < screenPos.x()) {
			vX -= (rate * diffX);
		}
		if(screenPoint.getY() > screenPos.y()) {
			vY -=  (rate * diffY);
		}
		if(screenPoint.getY() < screenPos.y()) {
			vY +=  (rate * diffY);
		}
		
		velocity = new Point2D(vX*0.95d, vY*0.95); //TODO scale these 
	}

	@Override
	public void update(double timeStepNS) {
		double rate = (timeStepNS / FracConstants.TIME_CONSTANT)*10.0d;
		
		accelerateTo(w.getMousePos(), rate);

		Point2D scaledVelocity = new Point2D(velocity.getX() * rate, velocity.getY() * rate);

		this.entityPosition.moveBy(scaledVelocity);
		
	}

	@Override
	public void render(GraphicsContext gc, double scaleFactor, Coordinate refPos) {
		this.scaleFactor = scaleFactor;
		this.currentWorldCoord = refPos;
		b = gc.getCanvas().getBoundsInLocal();
		
		gc.setFill(Color.AQUA);
		gc.setStroke(Color.AQUA);
		
		Coordinate drawAt = this.entityPosition.getScreenCoords(b, refPos, scaleFactor);
		
		gc.strokeRoundRect(drawAt.x() - 20.0d,  drawAt.y() - 20.0d, 40.0d, 40.0d, 5.0d, 5.0d); //TODO constants
		gc.strokeLine(drawAt.x(),  drawAt.y() - 20.0d,  drawAt.x(),  drawAt.y() + 20.0d);
		gc.strokeLine(drawAt.x() - 20.0d,  drawAt.y() , drawAt.x()+20.0d, drawAt.y());
		

	}

}
