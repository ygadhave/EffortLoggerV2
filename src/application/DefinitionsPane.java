package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class DefinitionsPane extends VBox {
	private DefinitionsManager manager;
	
	public DefinitionsPane(DefinitionsManager m) {
		manager = m;
	}
}