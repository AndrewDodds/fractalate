package org.ajd.fractalate.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ajd.fractalate.FracConstants;

import javafx.scene.paint.Color;

public class ActiveEntityRec {

	private int mainNumSides; //1 for circle, 2 for rounded rectangle, 3+ for polygon 
	private int numGuns; // 1-4 (max depends on difficulty setting)
	private int numEyes; // 1-5 (max depends on difficulty setting)
	private List<Color[]> colourGradients; // 2
	
	public ActiveEntityRec(double difficulty) {
		// Set up info for Active entity.
		Random r = RandomHolder.getRandom();
		
		mainNumSides = r.nextInt(FracConstants.MAX_ACTIVETILE_SIDES + 1);
		numGuns = r.nextInt(FracConstants.MAX_ACTIVETILE_NUMGUNS + 1);
		numEyes = r.nextInt(FracConstants.MAX_ACTIVETILE_EYES + 1);
		
		// Need to figure out how colours will change - list of 10 or so?
		colourGradients = new ArrayList<>();
		Color c1 = null;
		Color c2 = null;

		switch(r.nextInt(3)) {
		case 0: //Use the rainbow set
			for(int i=0; i<FracConstants.NUM_RAINBOW_COLOURS-1; i++) {
				c1 = new Color(FracConstants.RAINBOWCOLOURS[i][0],FracConstants.RAINBOWCOLOURS[i][1],FracConstants.RAINBOWCOLOURS[i][2], 1.0d);
				c2 = new Color(FracConstants.RAINBOWCOLOURS[i+1][0],FracConstants.RAINBOWCOLOURS[i+1][1],FracConstants.RAINBOWCOLOURS[i+1][2], 1.0d);
				colourGradients.add(new Color[] {c1, c2});
			}
			c1 = new Color(FracConstants.RAINBOWCOLOURS[FracConstants.NUM_RAINBOW_COLOURS-1][0],FracConstants.RAINBOWCOLOURS[FracConstants.NUM_RAINBOW_COLOURS-1][1],FracConstants.RAINBOWCOLOURS[FracConstants.NUM_RAINBOW_COLOURS-1][2], 1.0d);
			c2 = new Color(FracConstants.RAINBOWCOLOURS[0][0],FracConstants.RAINBOWCOLOURS[0][1],FracConstants.RAINBOWCOLOURS[0][2], 1.0d);
			colourGradients.add(new Color[] {c1, c2});
			break;
		case 1: // Create a greyscale set
			for (double d=0.0d; d< 1.05d; d+=0.1d) {
				c1 = new Color(d, d, d, 1.0d);
				c2 = new Color(1.0d-d, 1.0d-d, 1.0d-d, 1.0d);
				colourGradients.add(new Color[] {c1, c2});
			}
			break;
		case 2: // A random set..
		default:
			for(int i=0; i<10; i++) {
				double red = r.nextDouble();
				double green = r.nextDouble();
				double blue = r.nextDouble();
				c1 = new Color(red, green, blue, 1.0d);
				c2 = new Color(1.0d-red, 1.0d-green, 1.0d-blue, 1.0d);
				colourGradients.add(new Color[] {c1, c2});
			}
			break;
		}
		
		
	}

	
	
	public int getMainNumSides() {
		return mainNumSides;
	}

	public int getNumGuns() {
		return numGuns;
	}

	public int getNumEyes() {
		return numEyes;
	}

	public List<Color[]> getColourGradientStops() {
		return colourGradients;
	}		
	
	
}
