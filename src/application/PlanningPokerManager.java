package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

// PlanningPokerManager class written by Donovan Harp

public class PlanningPokerManager {
	private Database database;
	
	public PlanningPokerManager(Database d) {
		database = d;
	}
	
	public ArrayList<Project> getProjects() {
		return database.getProjects();
	}
	
	// Saves the currently set weight, bias, and selected values of the currently displayed effort log
	// list to the database.
	public void saveEffortLogSettings(VBox effortLogsList, Project currentProject) {
		// Check if the defect log display has any defect logs displayed.
		if (effortLogsList.getChildren().size() < 1) {
			System.out.println("Error: Effort log display is empty.");
			return;
		}
		
		// Get the currently displayed effort logs list.
		ArrayList<EffortLog> databaseLogs = currentProject.getEffortLogs();
		
		// Check if the currently selected log has the same number of defect logs as the
		// currently displayed defect log list.
		if (effortLogsList.getChildren().size() != databaseLogs.size()) {
			System.out.println("Error: Effort log list in database is not same size as the displayed list.");
			return;
		}
		
		// Loop through the currently displayed effort logs list.
		for (int i = 0; i < effortLogsList.getChildren().size(); i++) {
			// Get the effort log display item.
			HBox effortItem = (HBox)effortLogsList.getChildren().get(i);
			
			// Get the relevant components of the display item.
			Label idLabel = (Label)effortItem.getChildren().get(0);
			TextField weightField = (TextField)effortItem.getChildren().get(5);
			TextField biasField = (TextField)effortItem.getChildren().get(6);
			CheckBox selectedBox = (CheckBox)effortItem.getChildren().get(7);

			// Get the needed values from the components.
			int id = Integer.parseInt(idLabel.getText());
			double weight = Double.parseDouble(weightField.getText());
			int bias = Integer.parseInt(biasField.getText());
			boolean selected = selectedBox.isSelected();
			
			// Update the corresponding effort log with these values.
			EffortLog logToUpdate = databaseLogs.get(id);
			logToUpdate.setWeight(weight);
			logToUpdate.setBias(bias);
			logToUpdate.setSelected(selected);
		}
	}

	// Saves the currently set story poitns and selected values of the currently displayed defect log
	// list to the database.
	public void saveDefectLogSettings(VBox defectLogsList, Project currentProject) {
		// Check if an effort log has had its defect logs displayed.
		if (currentProject == null) {
			System.out.println("Warning: No project selected.");
			return;
		}
		
		// Check if the defect log display has any defect logs displayed.
		if (defectLogsList.getChildren().size() < 1) {
			System.out.println("Error: Defect log display is empty.");
			return;
		}

		// Get the defect logs from the currently selected log.
		ArrayList<DefectLog> defectLogs = currentProject.getDefectLogs();
		
		// Check if the currently selected log has the same number of defect logs as the
		// currently displayed defect log list.
		if (defectLogsList.getChildren().size() != defectLogs.size()) {
			System.out.println("Error: Defect log list in database is not same size as the displayed list.");
			return;
		}
		
		// Loop through each element of the displayed defect logs list.
		for (int j = 0; j < defectLogsList.getChildren().size(); j++) {
			// Get the defect log display item.
			HBox defectItem = (HBox)defectLogsList.getChildren().get(j);
			
			// Get the relevant components of the display item.
			Label idLabel = (Label)defectItem.getChildren().get(0);
			TextField storyPointsField = (TextField)defectItem.getChildren().get(2);
			CheckBox selectedBox = (CheckBox)defectItem.getChildren().get(3);
			
			// Get the needed values from the components.
			int id = Integer.parseInt(idLabel.getText());
			int storyPoints = Integer.parseInt(storyPointsField.getText());
			boolean selected = selectedBox.isSelected();
			
			// Update the corresponding defect log with these values.
			DefectLog logToUpdate = defectLogs.get(id);
			logToUpdate.setStoryPoints(storyPoints);
			logToUpdate.setSelected(selected);
		}
	}
	
	// Function to calculate the story points for an effort log
	public void calculateEffortPoints(EffortLog log, int pointsPerHour, int index) {
		// Calculate the unweighted and unbiased story points value
		int hours = log.getEffortHours();
		int minutes = log.getEffortMinutes();
		int logPoints = (hours * pointsPerHour) + (minutes * (pointsPerHour / 60));
		
		// Weight the base story points value
		// If the weight was put in as a negative, treat it like a positive
		double logWeight = log.getWeight();
		if (logWeight < 0) {
			logWeight *= -1;
			System.out.println("Warning: Log " + index + "'s weight is below zero. Using absolute value.");
		}
		
		// Bias the story points value
		logPoints = (int)Math.floor(((logPoints + log.getBias()) * logWeight));
		
		// Make sure the points value isn't in the negative and set it to 0 if it is
		if (logPoints < 0) {
			logPoints = 0;
			System.out.println("Warning: Bias decreases log " + index + "'s points below zero. Setting to zero instead.");
		}
		
		// Set the log's weighted and biased story points value
		log.setStoryPoints(logPoints);
	}
	
	// Function to calculate the weighted and biased average of the story points for all effort logs
	//    (Calls story points function)
	public int calculateAverage(ArrayList<EffortLog> effortLogs, ArrayList<DefectLog> defectLogs, int pointsPerHour) {
		// Check if there are no logs in the list
		if (effortLogs.size() < 1 && defectLogs.size() < 1) {
			System.out.println("Warning: No logs selected.");
			return 0;
		}
		
		// Calculate un-weighted/biased story points for each log
		for (int i = 0; i < effortLogs.size(); i++) {
			calculateEffortPoints(effortLogs.get(i), pointsPerHour, i);
		}
		
		// Calculate the total
		double total = 0;
		for (int i = 0; i < effortLogs.size(); i++) {
			total += effortLogs.get(i).getStoryPoints();
		}
		for (int i = 0; i < defectLogs.size(); i++) {
			total += defectLogs.get(i).getStoryPoints();
		}
		
		// Calculate the average
		double average = total / (effortLogs.size() + defectLogs.size());
		
		// Return the average as a floored int
		return (int)Math.floor(average);
	}
	
	// Generates a random effort log with random defect logs for testing
	public void generateRandomLog(Project p) {
		// Initialize the random number generator
		Random rand = new Random();
		
		// Get random minutes and hours
		int minutes = rand.nextInt(600);
		int hours = minutes / 60;
		minutes = minutes % 60;
		
		// Create a new effort log
		EffortLog newLog = new EffortLog(hours, minutes);
		
		// Set placeholder definitions
		newLog.setDefinitions(new Definitions("Name", "Category", "Deliverable"));
		
		// Create a list of random defect logs
		int numDefectLogs = rand.nextInt(10);
		for (int i = 0; i < numDefectLogs; i++) {
			minutes = rand.nextInt(600);
			hours = minutes / 60;
			minutes = minutes % 60;
		}
		
		// Add the new log to the database
		p.addLog(newLog);
	}
}
