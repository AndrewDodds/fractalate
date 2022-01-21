package org.ajd.fractalate.world;

import java.util.Random;

import org.ajd.fractalate.FracConstants;
import org.ajd.fractalate.world.elements.TileStyle;
import org.ajd.fractalate.world.elements.builders.ColourBuilders;
import org.ajd.fractalate.world.elements.builders.ColourSetBuilder;
import org.ajd.fractalate.world.elements.builders.Renderer;
import org.ajd.fractalate.world.elements.builders.ShapeBuilders;
import org.ajd.fractalate.world.elements.builders.ShapeRenderers;
import org.ajd.fractalate.world.elements.builders.ShapeBuilder;


// Contains information about a 'Patch' within the world, colours
public class PatchInfoRec {
	private double red1 = 1.0d;
	private double green1 = 1.0d;
	private double blue1 = 1.0d;
	private double red2 = 0.0d;
	private double green2 = 0.0d;
	private double blue2  = 0.0d;
	private int numcols = 6;
	private int numsides = 6;
	private double darken  = 1.0d; // 0.0...1.0
	private int numIters = 2;
	private TileStyle tStyle = TileStyle.CIRCLE_FLAIR;
	private ShapeBuilder shapesBuilder;
	private Renderer renderer;
	private ColourSetBuilder colourBuilder;
	
	PatchInfoRec(double darken) {			
		Random r = RandomHolder.getRandom();
		if(darken > 1.0d) {
			darken = 1.0d;
		}
		if(darken < 0.4d) {
			darken = 0.4d;
		}

		numcols = r.nextInt(FracConstants.MIN_COLOURS, FracConstants.MAX_COLOURS);
		numsides = r.nextInt(FracConstants.MIN_SIDES, FracConstants.MAX_SIDES);
		numIters = r.nextInt(FracConstants.MIN_ITERS, FracConstants.MAX_ITERS);
		red1 = darken;
		green1 = darken;
		blue1 = darken;

		if(r.nextBoolean()) {
			red1 = 0.0d;
			red2 = darken;
		}
		if(r.nextBoolean()) {
			green1 = 0.0d;
			green2 = darken;
		}
		if(r.nextBoolean()) {
			blue1 = 0.0d;
			blue2 =darken;
		}
		
		switch(r.nextInt(2)) {
		case 0:
			tStyle = TileStyle.CIRCLE_FLAIR;
			shapesBuilder = ShapeBuilders::buildCircleFlairShapes;
			break;
		case 1:
		default:
			tStyle = TileStyle.SQUARE;
			shapesBuilder = ShapeBuilders::buildSquareShapes;
			break;
		}
		
		
		switch(r.nextInt(3)) {
		case 1:
			renderer = ShapeRenderers::circlesRenderer;
			break;
		case 2:
			renderer = ShapeRenderers::polyRenderer;
			break;
		case 0:
		default:
			renderer = ShapeRenderers::squareRenderer;
			break;
		}
			
		
		switch(r.nextInt(2)) {
		case 1:
			colourBuilder = ColourBuilders::getRainbowColours;
			break;
		case 0:
		default:
			colourBuilder = ColourBuilders::getGradientColours;
			break;
		}
			
		
		
	}
	
	public double getRed1() {
		return red1;
	}

	public void setRed1(double red1) {
		this.red1 = red1;
	}

	public double getGreen1() {
		return green1;
	}

	public void setGreen1(double green1) {
		this.green1 = green1;
	}

	public double getBlue1() {
		return blue1;
	}

	public void setBlue1(double blue1) {
		this.blue1 = blue1;
	}

	public double getRed2() {
		return red2;
	}

	public void setRed2(double red2) {
		this.red2 = red2;
	}

	public double getGreen2() {
		return green2;
	}

	public void setGreen2(double green2) {
		this.green2 = green2;
	}

	public double getBlue2() {
		return blue2;
	}

	public void setBlue2(double blue2) {
		this.blue2 = blue2;
	}

	public int getNumcols() {
		return numcols;
	}

	public int getNumsides() {
		return numsides;
	}

	public void setNumcols(int numcols) {
		this.numcols = numcols;
	}

	public double getDarken() {
		return darken;
	}

	public void setDarken(double darken) {
		this.darken = darken;
	}

	public int getNumIters() {
		return numIters;
	}

	public void setNumIters(int numIters) {
		this.numIters = numIters;
	}

	public TileStyle gettStyle() {
		return tStyle;
	}

	public void settStyle(TileStyle tStyle) {
		this.tStyle = tStyle;
	}

	public ShapeBuilder getShapesBuilder() {
		return shapesBuilder;
	}

	public void setShapesBuilder(ShapeBuilder shapesBuilder) {
		this.shapesBuilder = shapesBuilder;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}

	public ColourSetBuilder getColourBuilder() {
		return colourBuilder;
	}

	public void setColourBuilder(ColourSetBuilder colourBuilder) {
		this.colourBuilder = colourBuilder;
	}

	public String toString() {
		return "Color Record: red1: "+red1+" green1: "+green1+"  blue1: "+blue1+"  red2: "+red2+"  green2: "+green2+"  blue2: "+blue2+
				"  numcols: "+numcols+"  darken: "+darken+"   numIters: "+numIters;
	}
}


