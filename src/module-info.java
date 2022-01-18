module SecondFC {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	opens org.ajd.fractalate to javafx.graphics, javafx.fxml;
	opens examples to javafx.graphics, javafx.fxml;
}
