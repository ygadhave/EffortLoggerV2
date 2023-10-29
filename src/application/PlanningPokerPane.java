package application;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

// PlanningPokerPane class written by Donovan Harp

public class PlanningPokerPane extends VBox {
	
	// Manager
	private PlanningPokerManager manager;
	
	// UI Elements
	private Label storyPointsLabel;
	private TextField storyPointsField;
	private Button refreshButton;
	private VBox effortLogListArea;
	private Button generateEstimateButton;
	private Label estimateLabel;
	
	// Constructor
	public PlanningPokerPane(PlanningPokerManager m) {
		// Initialize the manager and UI elements
		manager = m;
		
		storyPointsLabel = new Label("Story Points Per Hour:");
		storyPointsField = new TextField("60");
		
		refreshButton = new Button("Refresh Effort Log List");
		effortLogListArea = new VBox();
		generateEstimateButton = new Button("Generate Estimate");
		estimateLabel = new Label("Estimate Not Yet Generated");
		
		refreshButton.setOnAction(new RefreshButtonHandler());
		generateEstimateButton.setOnAction(new GenerateEstimateButtonHandler());
		
		// Add the UI elements to the pane
		this.getChildren().addAll(storyPointsLabel, storyPointsField);
		this.getChildren().addAll(refreshButton, effortLogListArea);
		this.getChildren().addAll(generateEstimateButton, estimateLabel);
	}
	
	// Updates the displayed effort log list to the current list of effort logs
	public void UpdateListArea(ArrayList<EffortLog> newLogList) {
		// Clear the current display
		effortLogListArea.getChildren().clear();
		
		// If the current list of effort logs is zero, do nothing
		if (newLogList.size() < 1) {
			return;
		}
		
		// Loop through the new effort log list and create a display element for each log
		for (int i = 0; i < newLogList.size(); i++) {
			// Initialize the log's display
			HBox effortLogDisplay = new HBox();
			
			// Get effort data from the log
			int hours = newLogList.get(i).GetHours();
			int minutes = newLogList.get(i).GetMinutes();
			double weight = newLogList.get(i).GetWeight();
			int bias = newLogList.get(i).GetBias();
			
			// Set the labels and text fields
			Label hoursTitle = new Label("Hours: ");
			Label hoursNumber = new Label("" + hours);
			Label minutesTitle = new Label("Minutes: ");
			Label minutesNumber = new Label("" + minutes);
			TextField weightField = new TextField("" + weight);
			TextField biasField = new TextField("" + bias);
			
			// Add the elements to the log's display and add the display to the list
			effortLogDisplay.getChildren().addAll(hoursTitle, hoursNumber, minutesTitle, minutesNumber, weightField, biasField);
			
			effortLogListArea.getChildren().add(effortLogDisplay);		
		}
	}
	
	// Handles clicking the refresh button
	private class RefreshButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Refresh the list display area
				ArrayList<EffortLog> newLogList = manager.GetEffortLogs();
				UpdateListArea(newLogList);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the generate estimate button
	private class GenerateEstimateButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Initialize a new array list to store all of the logs to be used in the calculation
				ArrayList<EffortLog> logsToCalculate = new ArrayList<EffortLog>();
				
				// Get the story points per hour conversion rate
				int pointsPerHour = Integer.parseInt(storyPointsField.getText());
				
				// For each element in the display list, get the data from that element and add it to a log
				// in the logs to calculate array list
				for (int i = 0; i < effortLogListArea.getChildren().size(); i++) {
					// TO DO: Add a checkbox to each display list element and check if it is checked.
					//        If it is not, skip that log and loop to the next display element
					
					HBox listElement = (HBox)effortLogListArea.getChildren().get(i);
					
					Label hoursLabel = (Label)listElement.getChildren().get(1);
					Label minutesLabel = (Label)listElement.getChildren().get(3);
					TextField weightField = (TextField)listElement.getChildren().get(4);
					TextField biasField = (TextField)listElement.getChildren().get(5);
					
					int hours = Integer.parseInt(hoursLabel.getText());
					int minutes = Integer.parseInt(minutesLabel.getText());
					double weight = Double.parseDouble(weightField.getText());
					int bias = Integer.parseInt(biasField.getText());
					
					EffortLog log = new EffortLog(hours, minutes, weight, bias);
					logsToCalculate.add(log);
				}
				
				// Generate the estimate
				int estimate = manager.CalculateAverage(logsToCalculate, pointsPerHour);
				estimateLabel.setText("Estimate: " + estimate);
			}
			catch (NumberFormatException exception) {
				System.out.println("Error: One of the weights or biases is in the incorrect format.");
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
}