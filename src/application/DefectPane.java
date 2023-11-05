package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class DefectPane extends VBox {
	private DefectManager manager;
	
	public DefectPane(DefectManager m) {
		manager = m;
	}
}