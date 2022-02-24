package org.ajd.fractalate.world;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.util.Coordinate;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;


public class MutableCoordinate {

	
	public class IntCoord { // Would like to make these records, but this all has to be mutable 
		int x;
		int y;
		int z;
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getZ() {
			return z;
		}
		public void setZ(int z) {
			this.z = z;
		}
	
		public IntCoord(int x, int y, int z) {
			this.x=x;
			this.y=y;
			this.z=z;
		}
		
		public String toString() {
			return "IntCoord x:"+x+" y:"+y+" z:"+z;
		}
		
	}
	
	double depthScale;
	IntCoord gridCoord;
	Point3D worldCoord;
	Point3D screenCoord;
	Coordinate refPos;
	Coordinate rawCenter;
	
	public MutableCoordinate(int x, int y, int z) {
		gridCoord = new IntCoord(x, y, z);
		
		depthScale = (z + FracConstants.NUM_LAYERS) /  (double)(2*FracConstants.NUM_LAYERS);	
		
		calcWorldPosFromGrid();
		
		setRefPos(new Coordinate (0.0d, 0.0d, 0.0d));
	}
	
	
	
	public void calcWorldPosFromGrid() {
		
		worldCoord = new Point3D (gridCoord.getX() * FracConstants.TILE_SIZE * depthScale, 	gridCoord.getY() * FracConstants.TILE_SIZE * depthScale, 0.0d);
		
	}
	
	public void calcGridPosFromWorldPos() {
		
		gridCoord.setX((int)(worldCoord.getX() / (FracConstants.TILE_SIZE * depthScale)));
		gridCoord.setY((int)(worldCoord.getY() / (FracConstants.TILE_SIZE * depthScale)));
	}
	
	public Point3D getWorldPos() {
		return worldCoord;
	}
	
	public IntCoord getGridPos() {
		return gridCoord;
	}
	
	public void setRefPos(Coordinate refPos) {
		this.refPos = refPos;
		calculateRawCenter();
	}
	
	public void moveBy(Point2D move) {
		worldCoord = new Point3D (worldCoord.getX() + (move.getX()*depthScale), worldCoord.getY() + (move.getY()*depthScale), 0.0d);
		
		calcGridPosFromWorldPos();
	}
	
	public void moveTo(Point2D to) {
		worldCoord = new Point3D (to.getX()*depthScale, to.getY()*depthScale, 0.0d);
		calcGridPosFromWorldPos();
	}
	
	
	private void calculateRawCenter() {
		
		// This is the coordinate within the world (shifted by the player position)
		rawCenter = new Coordinate( worldCoord.getX()  + (refPos.x() * depthScale),
				              -worldCoord.getY() - (refPos.y() * depthScale), 
				               FracConstants.TILE_SIZE * depthScale); // tells us how big a tile is..
		
	}
	
	// Translate to get to screen coordinates
	public Coordinate getScreenCoords(Bounds b, Coordinate refPos, double scaleFactor) {
		
		setRefPos(refPos);
		
		double centerX = (rawCenter.x() * scaleFactor) + (b.getWidth() / 2.0d);
		double centerY = (rawCenter.y() * scaleFactor)  + b.getHeight();
		double tileSize = (rawCenter.z() * scaleFactor);
				
		return new Coordinate(centerX, centerY, tileSize);
	}

	// This should go back from screen cordinates to world coordinates
	protected Point2D screenCoordToWorldCoord(Bounds b, double scaleFactor, Point2D screenPoint) {
		// Reverse 'realCenter' calculation
		double worldX = (screenPoint.getX() - (b.getWidth() / 2.0d)) / scaleFactor;
		double worldY = (screenPoint.getY() - b.getHeight()) / scaleFactor;
		
		// Reverse 'rawcenter' calculation
		worldX -= (refPos.x() * depthScale);
		worldY = -( worldY + (refPos.y() * depthScale));
				
		return new Point2D(worldX, worldY);
	}

	public String toString() {
		return "MutCoord:"+gridCoord.toString()+" WorldCoord:"+worldCoord.toString();
	}
	
}
