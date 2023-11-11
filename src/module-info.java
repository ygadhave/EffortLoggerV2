module EffortLoggerV2_Mainline_Prototype {
	requires javafx.controls;
	requires junit;
	requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
