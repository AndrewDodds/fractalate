package org.ajd.fractalate.world.elements.builders;

import java.util.ArrayList;
import java.util.List;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.PatchInfoRec;

import javafx.scene.paint.Color;

public class ColourBuilders {
	private ColourBuilders() {
		// don't instantiate
	}
	
	// required because of rounding errors in double..
	private static double checkLimits(double num) {
		if(num > 1.0d) {
			return 1.0d;
		}
		if(num < 0.0d) {
			return 0.0d;
		}
		return num;
			
	}
	
	private static long depthIter(long levelNo) {
		if(levelNo > FracConstants.GROUND_LEVEL_LAYER ) {
			return 0;
		}
		
		if(levelNo < 1) {
			return 4;
		}
		
		return (FracConstants.GROUND_LEVEL_LAYER + 1) - levelNo;
	}

	public static List<Color> getGradientColours(PatchInfoRec crec, long depth) {
		// create array of shaded colours to use, darken with distance.
		List<Color> colourSet = new ArrayList<>();
		
		
		Color c1 = new Color(checkLimits(crec.getRed1()), checkLimits(crec.getGreen1()), checkLimits(crec.getBlue1()), 1.0d);
		Color c2 = new Color(checkLimits(crec.getRed2()), checkLimits(crec.getGreen2()), checkLimits(crec.getBlue2()), 1.0d);
		for(int i=0; i< depthIter(depth); i++) {
			c1=c1.darker();
			c2=c2.darker();
		}
		colourSet.add(c1);
		colourSet.add(c2);

		return colourSet;
	}


	private static final int RED = 0;
	private static final int GREEN = 1;
	private static final int BLUE = 2;
	
	public static List<Color> getRainbowColours(PatchInfoRec crec, long depth) {
		// create array of shaded colours to use, darken with distance.
		List<Color> colourSet1 = new ArrayList<>();
		List<Color> colourSet2 = new ArrayList<>();

		if(crec.getNumcols() < 3) {
			// Avoid any issues with the calculation, eg div0
			colourSet1.add(new Color(FracConstants.RAINBOWCOLOURS[0][RED], FracConstants.RAINBOWCOLOURS[0][GREEN], FracConstants.RAINBOWCOLOURS[0][BLUE], 1.0d));
			colourSet1.add(new Color(FracConstants.RAINBOWCOLOURS[FracConstants.NUM_RAINBOW_COLOURS-1][RED], FracConstants.RAINBOWCOLOURS[FracConstants.NUM_RAINBOW_COLOURS-1][GREEN], FracConstants.RAINBOWCOLOURS[FracConstants.NUM_RAINBOW_COLOURS-1][BLUE], 1.0d));
		}
		
		double step = (double)(FracConstants.NUM_RAINBOW_COLOURS-1)/(double)(crec.getNumcols()-1);
		double pos = 0.0d;
		for(int i=0; i<crec.getNumcols(); i++) {
			int idx = (int) pos;
			colourSet1.add(new Color(FracConstants.RAINBOWCOLOURS[idx][RED], FracConstants.RAINBOWCOLOURS[idx][GREEN], FracConstants.RAINBOWCOLOURS[idx][BLUE], 1.0d));
			pos += step;
		}
		
		for(Color c:colourSet1) {
			for(int i=0; i< depthIter(depth); i++) {
				c=c.darker();
				c=c.darker();
			}
			colourSet2.add(c);
		}

		return colourSet2;
	}
	
	
	
	
}
