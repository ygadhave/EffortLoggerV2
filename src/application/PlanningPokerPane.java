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
	
	private HBox viewerButtons;
	private Button refreshButton;
	private Button saveSettingsButton;
	
	private ScrollPane effortLogScrollPane;
	private VBox effortLogListArea;
	
	private ScrollPane defectLogScrollPane;
	private VBox defectLogListArea;
	
	private Button generateEstimateButton;
	private Label estimateLabel;
	private Label errorLabel;
	
	// Variable to store which effort log has its defect logs shown
	private EffortLog currentlySelectedLog;
	
	// Constructor
	public PlanningPokerPane(PlanningPokerManager m) {
		// Initialize the manager and UI elements
		manager = m;
		
		storyPointsLabel = new Label("Story Points Per Hour:");
		storyPointsField = new TextField("60");
		
		viewerButtons = new HBox();
		refreshButton = new Button("Refresh Effort Log List");
		saveSettingsButton = new Button("Save Settings");
		viewerButtons.getChildren().addAll(refreshButton, saveSettingsButton);
		
		effortLogScrollPane = new ScrollPane();
		effortLogListArea = new VBox();
		effortLogScrollPane.setContent(effortLogListArea);
		
		defectLogScrollPane = new ScrollPane();
		defectLogListArea = new VBox();
		defectLogScrollPane.setContent(defectLogListArea);
		
		generateEstimateButton = new Button("Generate Estimate");
		estimateLabel = new Label("Estimate Not Yet Generated");
		errorLabel = new Label("");
		
		refreshButton.setOnAction(new RefreshButtonHandler());
		saveSettingsButton.setOnAction(new SaveSettingsButtonHandler());
		generateEstimateButton.setOnAction(new GenerateEstimateButtonHandler());
		
		// Add the UI elements to the pane
		this.getChildren().addAll(storyPointsLabel, storyPointsField);
		this.getChildren().addAll(viewerButtons, effortLogScrollPane, defectLogScrollPane);
		this.getChildren().addAll(generateEstimateButton, estimateLabel, errorLabel);
		
		// Initialize currentlySelectedLog
		currentlySelectedLog = null;
	}
	
	// Updates the displayed effort log list to the current list of effort logs
	public void updateEffortListArea(ArrayList<EffortLog> newLogList) {
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
			int hours = newLogList.get(i).getHours();
			int minutes = newLogList.get(i).getMinutes();
			double weight = newLogList.get(i).getWeight();
			int bias = newLogList.get(i).getBias();
			boolean selected = newLogList.get(i).getSelected();
			
			// Set the labels and text fields
			Label id = new Label(Integer.toString(i));
			Label hoursTitle = new Label("Hours: ");
			Label hoursNumber = new Label("" + hours);
			Label minutesTitle = new Label("Minutes: ");
			Label minutesNumber = new Label("" + minutes);
			TextField weightField = new TextField("" + weight);
			TextField biasField = new TextField("" + bias);
			CheckBox selectBox = new CheckBox();
			selectBox.setSelected(selected);
			Button defectViewButton = new Button("View Defect Logs");
			defectViewButton.setOnAction(new DefectViewButtonHandler());
			
			effortLogDisplay.setSpacing(5);
			
			// Add the elements to the log's display and add the display to the list
			effortLogDisplay.getChildren().addAll(id, hoursTitle, hoursNumber, minutesTitle, minutesNumber, weightField, biasField, selectBox, defectViewButton);
			
			effortLogListArea.getChildren().add(effortLogDisplay);		
		}
	}
	
	// Updates the displayed defect log list for a certain effort log
	public void updateDefectListArea(ArrayList<DefectLog> newLogList) {
		// Clear the current display
		defectLogListArea.getChildren().clear();
		
		// If the current list of defect logs is zero, do nothing
		if (newLogList.size() < 1) {
			return;
		}
		
		// Loop through the new effort log list and create a display element for each log
		for (int i = 0; i < newLogList.size(); i++) {
			// Initialize the log's display
			HBox defectLogDisplay = new HBox();
			
			// Get effort data from the log
			int hours = newLogList.get(i).getHours();
			int minutes = newLogList.get(i).getMinutes();
			double weight = newLogList.get(i).getWeight();
			int bias = newLogList.get(i).getBias();
			boolean selected = newLogList.get(i).getSelected();
			
			// Set the labels and text fields
			Label id = new Label(Integer.toString(i));
			Label hoursTitle = new Label("Hours: ");
			Label hoursNumber = new Label("" + hours);
			Label minutesTitle = new Label("Minutes: ");
			Label minutesNumber = new Label("" + minutes);
			TextField weightField = new TextField("" + weight);
			TextField biasField = new TextField("" + bias);
			CheckBox selectBox = new CheckBox();
			selectBox.setSelected(selected);
			
			// Add the elements to the log's display and add the display to the list
			defectLogDisplay.getChildren().addAll(id, hoursTitle, hoursNumber, minutesTitle, minutesNumber, weightField, biasField, selectBox);
			
			defectLogListArea.getChildren().add(defectLogDisplay);		
		}
	}
	
	// Handles clicking the refresh button
	private class RefreshButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Refresh the list display area
				ArrayList<EffortLog> newLogList = manager.GetEffortLogs();
				updateEffortListArea(newLogList);
				if (currentlySelectedLog != null) {
					ArrayList<DefectLog> newDefectLogList = currentlySelectedLog.getDefectLogs();
					updateDefectListArea(newDefectLogList);
				}
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the save settings button
	private class SaveSettingsButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				manager.saveEffortLogSettings(effortLogListArea);
				manager.saveDefectLogSettings(defectLogListArea, currentlySelectedLog);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the generate estimate button
	// TODO: Update to calculate with all defect logs as well as all effort logs
	private class GenerateEstimateButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Save current settings to the database
				manager.saveEffortLogSettings(effortLogListArea);
				manager.saveDefectLogSettings(defectLogListArea, currentlySelectedLog);
				
				// Initialize a new array list to store all of the logs to be used in the calculation
				ArrayList<EffortLog> effortLogsToCalculate = new ArrayList<EffortLog>();
				
				// Get the story points per hour conversion rate
				int pointsPerHour = Integer.parseInt(storyPointsField.getText());
				
				// For each element in the display list, get the data from that element and add it to a log
				// in the logs to calculate array list
				for (int i = 0; i < effortLogListArea.getChildren().size(); i++) {
					
					HBox listElement = (HBox)effortLogListArea.getChildren().get(i);
					
					CheckBox selectBox = (CheckBox)listElement.getChildren().get(7);
					if (selectBox.isSelected()) {
						Label hoursLabel = (Label)listElement.getChildren().get(2);
						Label minutesLabel = (Label)listElement.getChildren().get(4);
						TextField weightField = (TextField)listElement.getChildren().get(5);
						TextField biasField = (TextField)listElement.getChildren().get(6);
						
						int hours = Integer.parseInt(hoursLabel.getText());
						int minutes = Integer.parseInt(minutesLabel.getText());
						double weight = Double.parseDouble(weightField.getText());
						int bias = Integer.parseInt(biasField.getText());
						
						EffortLog log = new EffortLog(hours, minutes, weight, bias);
						effortLogsToCalculate.add(log);
					}
					
					ArrayList<DefectLog> defectLogs = manager.GetEffortLogs().get(i).getDefectLogs();
					if (defectLogs.size() > 0) {
						for (int j = 0; j < defectLogs.size(); j++) {
							if (defectLogs.get(j).getSelected()) { 
								EffortLog log = new EffortLog(defectLogs.get(j).getHours(), 
															  defectLogs.get(j).getMinutes(), 
															  defectLogs.get(j).getWeight(), 
															  defectLogs.get(j).getBias());
								effortLogsToCalculate.add(log);
							}
						}
					}
					
				}
				
				// Generate the estimate
				int estimate = manager.calculateAverage(effortLogsToCalculate, pointsPerHour);
				estimateLabel.setText("Estimate: " + estimate);
			}
			catch (NumberFormatException exception) {
				System.out.println("Error: One of the weights or biases is in the incorrect format.");
				System.out.println(exception.getMessage());
				errorLabel.setText("Error: One of the weights or biases is in the incorrect format.");
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
				
				// v Doesn't work for some reason v
				errorLabel.setText(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the defect view button for an effort log
	private class DefectViewButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Save the currently selected defect logs list
				manager.saveDefectLogSettings(defectLogListArea, currentlySelectedLog);
				
				// Refresh the list display area
				Button sourceButton = (Button)event.getSource();
				HBox listItem = (HBox)sourceButton.getParent();
				Label idLabel = (Label)listItem.getChildren().get(0);
				EffortLog effortLog = manager.GetEffortLogs().get(Integer.parseInt(idLabel.getText()));
				ArrayList<DefectLog> defectLogs = effortLog.getDefectLogs();
				updateDefectListArea(defectLogs);
				currentlySelectedLog = effortLog;
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
}