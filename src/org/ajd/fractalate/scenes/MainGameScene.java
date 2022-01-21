package org.ajd.fractalate.scenes;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

public class MainGameScene extends Scene {

    private Set<String> currentinput = new HashSet<>();
    
    private void addEventHandlers() {
		// Add event handlers for the scene
		// lambda event handlers are so much better..
        this.setOnKeyPressed(e -> currentinput.add(e.getCode().toString()));

	    this.setOnKeyReleased(e -> currentinput.remove(e.getCode().toString()));
    	
    }
    
	// This constructor is the one actually used at the moment 
	public MainGameScene(Parent arg0) {
		super(arg0);
		
		addEventHandlers();
	}

	
	public Set<String> getCurrentInput() {
		return currentinput;
	}
	
	
	
	
	
	// Inherited constructors
	public MainGameScene(Parent arg0, Paint arg1) {
		super(arg0, arg1);
	}

	public MainGameScene(Parent arg0, double arg1, double arg2) {
		super(arg0, arg1, arg2);
	}

	public MainGameScene(Parent arg0, double arg1, double arg2, Paint arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public MainGameScene(Parent arg0, double arg1, double arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public MainGameScene(Parent arg0, double arg1, double arg2, boolean arg3, SceneAntialiasing arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}

}
