package org.ajd.fractalate.world.elements.builders;

import java.util.ArrayList;
import java.util.List;

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

	public static List<Color> getGradientColours(PatchInfoRec crec) {
		// create array of shaded colours to use, darken with distance.
		List<Color> colourSet = new ArrayList<>();

		if(crec.getNumcols() < 3) {
			// Avoid any issues with the calculation, eg div0
			colourSet.add(new Color(crec.getRed1(), crec.getGreen1(), crec.getBlue1(), 1.0d));
			colourSet.add(new Color(crec.getRed2(), crec.getGreen2(), crec.getBlue2(), 1.0d));
		}
		double redStep = (crec.getRed2()-crec.getRed1()) / (crec.getNumcols()-1); //imagine this was 2; we'd want to step the whole distance in one go in the loop below
		double greenStep = (crec.getGreen2()-crec.getGreen1()) / (crec.getNumcols()-1);
		double blueStep = (crec.getBlue2()-crec.getBlue1()) / (crec.getNumcols()-1);
		double r = crec.getRed1();
		double g = crec.getGreen1();
		double b = crec.getBlue1();
		 
		for(int i=0; i<crec.getNumcols(); i++) {
			
			colourSet.add(new Color(checkLimits(r), checkLimits(g), checkLimits(b), 1.0d));
			r += redStep;
			g += greenStep;
			b += blueStep;
		}
		return colourSet;
	}

	private static final int NUM_RAINBOW_COLOURS = 16;
	private static final int RED = 0;
	private static final int GREEN = 1;
	private static final int BLUE = 2;
	private static final double[][] rainbowColours = {
			{1.0d, 0.0d, 0.0d},
			{1.0d, 0.5d, 0.0d},
			{1.0d, 0.8d, 0.0d},
			{1.0d, 1.0d, 0.0d},
			{0.8d, 1.0d, 0.0d},
			{0.5d, 1.0d, 0.0d},
			{0.0d, 1.0d, 0.0d},
			{0.0d, 1.0d, 0.5d},
			{0.0d, 1.0d, 0.8d},
			{0.0d, 1.0d, 1.0d},
			{0.0d, 0.8d, 1.0d},
			{0.0d, 0.5d, 1.0d},
			{0.0d, 0.0d, 1.0d},
			{0.5d, 0.0d, 1.0d},
			{0.8d, 0.0d, 1.0d},
			{1.0d, 0.0d, 1.0d}
	};
	
	public static List<Color> getRainbowColours(PatchInfoRec crec) {
		// create array of shaded colours to use, darken with distance.
		List<Color> colourSet = new ArrayList<>();

		if(crec.getNumcols() < 3) {
			// Avoid any issues with the calculation, eg div0
			colourSet.add(new Color(rainbowColours[0][RED], rainbowColours[0][GREEN], rainbowColours[0][BLUE], 1.0d));
			colourSet.add(new Color(rainbowColours[NUM_RAINBOW_COLOURS-1][RED], rainbowColours[NUM_RAINBOW_COLOURS-1][GREEN], rainbowColours[NUM_RAINBOW_COLOURS-1][BLUE], 1.0d));
		}
		
		double step = (double)(NUM_RAINBOW_COLOURS-1)/(double)(crec.getNumcols()-1);
		double pos = 0.0d;
		for(int i=0; i<crec.getNumcols(); i++) {
			int idx = (int) pos;
			colourSet.add(new Color(rainbowColours[idx][RED], rainbowColours[idx][GREEN], rainbowColours[idx][BLUE], 1.0d));
			pos += step;
		}

		return colourSet;
	}
	
	
	
	
}
