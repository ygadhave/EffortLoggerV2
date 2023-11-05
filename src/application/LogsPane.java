package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class LogsPane extends VBox {
	private LogsManager manager;
	
	public LogsPane(LogsManager m) {
		manager = m;
	}
}