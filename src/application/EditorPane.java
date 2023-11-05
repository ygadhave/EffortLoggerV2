package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class EditorPane extends VBox {
	private EditorManager manager;
	
	public EditorPane(EditorManager m) {
		manager = m;
	}
}