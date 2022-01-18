package org.ajd.fractalate;
	

import org.ajd.fractalate.animate.MainAminationTimer;
import org.ajd.fractalate.scenes.MainGameScene;
import org.ajd.fractalate.world.World;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Main extends Application {
	World w;
	
	public static final Point2D SCREEN_SIZE = new Point2D(1500.0d, 1000.0d);
		
	@Override
	public void start(Stage theStage) {
		//TODO allow configurable seeds
		w = new World(1.0d, System.currentTimeMillis());

		try {
	        theStage.setTitle( "Fractalate" );

	        Group root = new Group();

	        MainGameScene theScene = new MainGameScene( root );
	        theStage.setScene( theScene );

	        Canvas canvas = new Canvas(SCREEN_SIZE.getX(), SCREEN_SIZE.getY());
	        root.getChildren().add( canvas );

	        GraphicsContext gc = canvas.getGraphicsContext2D();

	        MainAminationTimer mat = new MainAminationTimer(gc, theScene, SCREEN_SIZE, w);
	        
	        System.out.println("About to start amination..");
	        mat.start();

	        System.out.println("About to show stage..");
	        theStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
        System.out.println("Done..");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
