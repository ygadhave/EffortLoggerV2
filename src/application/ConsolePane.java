package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

// ConsolePane class written by Donovan Harp

public class ConsolePane extends VBox {

	// Manager
	private ConsoleManager manager;
	
	// UI Elements
	private Label hoursLabel;
	private TextField hoursField;
	private Label minutesLabel;
	private TextField minutesField;
	private Button generateLogButton;
	private Label resultText;
	private Button clearLogsButton;
	
	// Constructor
	public ConsolePane(ConsoleManager m) {
		// Initialize manager and UI elements
		manager = m;
		
		hoursLabel = new Label("Set hours:");
		hoursField = new TextField();
		minutesLabel = new Label("Set minutes:");
		minutesField = new TextField();
		generateLogButton = new Button("Generate Effort Log");
		resultText = new Label("");
		clearLogsButton = new Button("Clear Logs");
		
		//Bind Buttons
		generateLogButton.setOnAction(new GenerateLogButtonHandler());
		clearLogsButton.setOnAction(new ClearLogsButtonHandler());
		
		// Add UI elements to pane
		this.getChildren().addAll(hoursLabel, hoursField, minutesLabel, minutesField, generateLogButton, resultText, clearLogsButton);
	}
	
	// Handles clicking the generate log button
	private class GenerateLogButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			String hoursString = hoursField.getText();
			String minutesString = minutesField.getText();
			
			try {
				//Check if either of the fields is empty.
				if ((hoursString.compareTo("") == 0) || (minutesString.compareTo("") == 0)) {
					throw new Exception("At least one field is empty.");
				}
				
				//Parse hours and minutes.
				int hours = Integer.parseInt(hoursString);
				int minutes = Integer.parseInt(minutesString);
				
				if (manager.CreateNewEffortLog(hours, minutes)) {				
					System.out.println("Successfully created new effort log.");
					resultText.setText("Successfully created new effort log.");
				}
				else {
					System.out.println("Effort log creation failed.");
					resultText.setText("Effort log creation failed.");
				}
			}
			catch (NumberFormatException exception) {
				System.out.println("Hours and minutes must be integers.");
				resultText.setText("Hours and minutes must be integers.");
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
				resultText.setText(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the clear logs button
	private class ClearLogsButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				manager.ClearEffortLogs();
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
}
