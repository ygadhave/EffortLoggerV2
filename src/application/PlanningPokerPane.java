package application;

import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;

// PlanningPokerPane class written by Donovan Harp

public class PlanningPokerPane extends VBox {
	
	// Manager
	private PlanningPokerManager manager;
	
	// Project Info Window
	Stage infoStage;
	
	// Header Elements
	private Project selectedProject;
	private String selectedProjectName;
	private String selectedProjectNumber;
	private String userStoryName;
	private String userStoryDescription;
	private Button projectInfoButton;
	
	// Header UI Elements
	private Label projectLabel;
	private Label userStoryNameLabel;
	private Label userStoryDescriptionLabel;
	
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
	
	// Temporary log generator for testing
	private Button randomLogButton;
	
	// Constructor
	public PlanningPokerPane(PlanningPokerManager m) {
		// Initialize the manager
		manager = m;
		
		// Initialize project and header information
		selectedProject = manager.getProjects().get(0);
		selectedProjectName = selectedProject.getProjectName();
		selectedProjectNumber = selectedProject.getProjectNumber();
		userStoryName = selectedProject.getUserStoryName();
		userStoryDescription = selectedProject.getUserStoryDescription();
		
		// Initialize UI Elements
		projectLabel = new Label("Project #" + selectedProjectNumber + ": " + selectedProjectName);
		userStoryNameLabel = new Label(userStoryName);
		userStoryDescriptionLabel = new Label(userStoryDescription);
		projectInfoButton = new Button("Update Project Info");
		projectInfoButton.setOnAction(new ProjectInfoButtonHandler());
		
		storyPointsLabel = new Label("Story Points Per Hour:");
		storyPointsField = new TextField("60");
		
		viewerButtons = new HBox();
		refreshButton = new Button("Refresh Log Lists");
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
		
		randomLogButton = new Button("Generate Random Log (For Testing)");
		randomLogButton.setOnAction(new RandomLogButtonHandler());
		
		// Add the UI elements to the pane
		this.getChildren().addAll(projectLabel, userStoryNameLabel, userStoryDescriptionLabel, projectInfoButton);
		this.getChildren().addAll(storyPointsLabel, storyPointsField);
		this.getChildren().addAll(viewerButtons, effortLogScrollPane, defectLogScrollPane);
		this.getChildren().addAll(generateEstimateButton, estimateLabel, errorLabel);
		this.getChildren().add(randomLogButton);
	}
	
	public void setSelectedProject(Project p) {
		selectedProject = p;
	}
	
	public Project getSelectedProject() {
		return selectedProject;
	}
	
	public void openProjectInfoWindow() {
		// Create the project info window
		PlanningPokerProjectInfoWindow projectInfoWindow = new PlanningPokerProjectInfoWindow(this, manager, selectedProject);
		Scene mainScene = new Scene(projectInfoWindow, 800, 600);
		
		// Show the project info window
		infoStage = new Stage();
		infoStage.setScene(mainScene);
		infoStage.show();
	}
	
	public void openLogInfoWindow(EffortLog log) {
		// Create the effort log info window
		PlanningPokerEffortLogInfoWindow logInfoWindow = new PlanningPokerEffortLogInfoWindow(this, manager, log);
		Scene mainScene = new Scene(logInfoWindow, 200, 100);
		
		// Show the project info window
		infoStage = new Stage();
		infoStage.setScene(mainScene);
		infoStage.show();
	}
	
	public void updateProjectInfo(Project newProject) {
		// Get currently selected project and its info
		selectedProject = newProject;
		selectedProjectName = selectedProject.getProjectName();
		selectedProjectNumber = selectedProject.getProjectNumber();
		userStoryName = selectedProject.getUserStoryName();
		userStoryDescription = selectedProject.getUserStoryDescription();
		
		// Set the project info UI elements on the planning poker tab
		projectLabel.setText("Project #" + selectedProjectNumber + ": " + selectedProjectName);
		userStoryNameLabel.setText(userStoryName);
		userStoryDescriptionLabel.setText(userStoryDescription);
		
		// Close the project info window
		infoStage.hide();
	}
	
	// Updates the displayed effort log list to the current list of effort logs
	public void updateEffortListArea(Project project) {
		// Clear the current display
		effortLogListArea.getChildren().clear();
		
		// Get the effort logs from the project
		ArrayList<EffortLog> newLogList = project.getEffortLogs();
		
		// If the current list of effort logs is zero, do nothing
		if (newLogList.size() < 1) {
			return;
		}
		
		// Loop through the new effort log list and create a display element for each log
		for (int i = 0; i < newLogList.size(); i++) {
			// Initialize the log's display
			HBox effortLogDisplay = new HBox();
			
			// Get effort data from the log
			int hours = newLogList.get(i).getEffortHours();
			int minutes = newLogList.get(i).getEffortMinutes();
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
			Button logInfoButton = new Button("View Info");
			logInfoButton.setOnAction(new LogInfoButtonHandler());
			
			effortLogDisplay.setSpacing(5);
			
			// Add the elements to the log's display and add the display to the list
			effortLogDisplay.getChildren().addAll(id, hoursTitle, hoursNumber, minutesTitle, minutesNumber, weightField, biasField, selectBox, logInfoButton);
			
			// Format the effort log display element
			effortLogDisplay.setSpacing(10);
			
			effortLogListArea.getChildren().add(effortLogDisplay);		
		}
	}
	
	// Updates the displayed defect log list for a certain effort log
	public void updateDefectListArea(Project project) {
		// Clear the current display
		clearDefectListArea();
		
		// Get the defect logs from the project
		ArrayList<DefectLog> defectLogs = project.getDefectLogs();
		
		// If the current project's list of defect logs is zero, do nothing
		if (defectLogs.size() < 1) {
			return;
		}
		
		// Loop through the new effort log list and create a display element for each log
		for (int i = 0; i < defectLogs.size(); i++) {
			// Initialize the log's display
			HBox defectLogDisplay = new HBox();
			
			// Get data from the log
			int storyPoints = defectLogs.get(i).getStoryPoints();
			boolean selected = defectLogs.get(i).getSelected();
			
			// Set the labels and text fields
			Label id = new Label(Integer.toString(i));
			Label storyPointsLabel = new Label("Story Points:");
			TextField storyPointsField = new TextField("" + storyPoints);
			CheckBox selectBox = new CheckBox();
			selectBox.setSelected(selected);
			
			// Add the elements to the log's display and add the display to the list
			defectLogDisplay.getChildren().addAll(id, storyPointsLabel, storyPointsField, selectBox);
			
			// Format the defect log display element
			defectLogDisplay.setSpacing(10);
			
			defectLogListArea.getChildren().add(defectLogDisplay);		
		}
	}
	
	// Clears the currently displayed defect list (DOES NOT SAVE!!!)
	public void clearDefectListArea() {
		// Clear the current display
		defectLogListArea.getChildren().clear();
	}
	
	public void saveSettings() {
		try {
			manager.saveEffortLogSettings(effortLogListArea, selectedProject);
			manager.saveDefectLogSettings(defectLogListArea, selectedProject);
		}
		catch (NumberFormatException exception) {
			System.out.println("Error: One of the weights, biases, or story points values is in the incorrect format.");
			System.out.println(exception.getMessage());
			errorLabel.setText("Error: One of the weights, biases, or story points values is in the incorrect format.");
		}
		catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	// Handles clicking the update project info button
	private class ProjectInfoButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				openProjectInfoWindow();
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the refresh button
	private class RefreshButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Refresh the list display areas
				updateEffortListArea(selectedProject);
				updateDefectListArea(selectedProject);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the save settings button
	private class SaveSettingsButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			saveSettings();
		}
	}
	
	// Handles clicking the generate estimate button
	// TODO: Update to calculate with all defect logs as well as all effort logs
	private class GenerateEstimateButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Save current settings to the database
				saveSettings();
				
				// Get the story points per hour conversion rate
				int pointsPerHour = Integer.parseInt(storyPointsField.getText());
				
				// Initialize a new array list to store all of the logs to be used in the calculation
				ArrayList<EffortLog> effortLogsToCalculate = new ArrayList<EffortLog>();
				for (int i = 0; i < selectedProject.getEffortLogs().size(); i++) {
					if (selectedProject.getEffortLogs().get(i).getSelected() == true) {
						effortLogsToCalculate.add(selectedProject.getEffortLogs().get(i));
					}
				}
				
				ArrayList<DefectLog> defectLogsToCalculate = new ArrayList<DefectLog>();
				for (int i = 0; i < selectedProject.getDefectLogs().size(); i++) {
					if (selectedProject.getDefectLogs().get(i).getSelected() == true) {
						defectLogsToCalculate.add(selectedProject.getDefectLogs().get(i));
					}
				}
				
				// Generate the estimate
				int estimate = manager.calculateAverage(effortLogsToCalculate, defectLogsToCalculate, pointsPerHour);
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
	
	// Handles clicking the save settings button
	private class LogInfoButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				// Get the effort log associated with this list element
				Button sourceButton = (Button)event.getSource();
				HBox listItem = (HBox)sourceButton.getParent();
				Label idLabel = (Label)listItem.getChildren().get(0);
				EffortLog effortLog = selectedProject.getEffortLogs().get(Integer.parseInt(idLabel.getText()));
				
				// Open the info window
				openLogInfoWindow(effortLog);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the save settings button
	private class RandomLogButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				manager.generateRandomLog(selectedProject);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
}