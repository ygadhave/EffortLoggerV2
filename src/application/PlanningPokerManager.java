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
	// TODO: Currently does not show an error if the list sizes are different. Might be a good idea
	//       to look into adding that.
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

	// Saves the currently set weight, bias, and selected values of the currently displayed defect log
	// list to the database.
	// TODO: Currently does not show an error if the list sizes are different. Might be a good idea
	//       to look into adding that.
	public void saveDefectLogSettings(VBox defectLogsList, EffortLog currentlySelectedLog) {
		// Check if an effort log has had its defect logs displayed.
		if (currentlySelectedLog == null) {
			System.out.println("Warning: No defect log list shown.");
			return;
		}
		
		// Check if the defect log display has any defect logs displayed.
		if (defectLogsList.getChildren().size() < 1) {
			System.out.println("Error: Defect log display is empty.");
			return;
		}

		// Get the defect logs from the currently selected log.
		ArrayList<DefectLog> defectLogs = currentlySelectedLog.getDefectLogs();
		
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
			Label idLabelD = (Label)defectItem.getChildren().get(0);
			TextField weightFieldD = (TextField)defectItem.getChildren().get(5);
			TextField biasFieldD = (TextField)defectItem.getChildren().get(6);
			CheckBox selectedBoxD = (CheckBox)defectItem.getChildren().get(7);
			
			// Get the needed values from the components.
			int idD = Integer.parseInt(idLabelD.getText());
			double weightD = Double.parseDouble(weightFieldD.getText());
			int biasD = Integer.parseInt(biasFieldD.getText());
			boolean selectedD = selectedBoxD.isSelected();
			
			// Update the corresponding defect log with these values.
			DefectLog logToUpdateD = defectLogs.get(idD);
			logToUpdateD.setWeight(weightD);
			logToUpdateD.setBias(biasD);
			logToUpdateD.setSelected(selectedD);
		}
	}
	
	// Function to calculate the story points for an effort log
	public void calculateEffortPoints(EffortLog log, int pointsPerHour) {
		int hours = log.getHours();
		int minutes = log.getMinutes();
		int points = (hours * pointsPerHour) + (minutes * (pointsPerHour / 60));
		log.setStoryPoints(points);
	}
	
	public void calculateDefectPoints(DefectLog log, int pointsPerHour) {
		int hours = log.getHours();
		int minutes = log.getMinutes();
		int points = (hours * pointsPerHour) + (minutes * (pointsPerHour / 60));
		log.setStoryPoints(points);
	}
	
	// Function to calculate story points for every effort log
	public void calculateAllStoryPoints(ArrayList<EffortLog> logsToCalculate, int pointsPerHour) {
		for (int i = 0; i < logsToCalculate.size(); i++) {
			calculateEffortPoints(logsToCalculate.get(i), pointsPerHour);
		}
	}
	
	// Function to calculate the weighted and biased average of the story points for all effort logs
	//    (Calls story points function)
	public int calculateAverage(ArrayList<EffortLog> logsToCalculate, int pointsPerHour) {
		// Check if there are no logs in the list
		if (logsToCalculate.size() < 1) {
			System.out.println("Warning: No effort logs selected.");
			return 0;
		}
		
		// Calculate un-weighted/biased story points for each log
		calculateAllStoryPoints(logsToCalculate, pointsPerHour);
		
		// Calculate the average
		double total = 0;
		for (int i = 0; i < logsToCalculate.size(); i++) {
			EffortLog log = logsToCalculate.get(i);
			
			// If the weight was put in as a negative, treat it like a positive
			double logWeight = log.getWeight();
			if (logWeight < 0) {
				logWeight *= -1;
				System.out.println("Warning: Log " + i + "'s weight is below zero. Using absolute value.");
			}
			
			double logPoints = ((log.getStoryPoints() + log.getBias()) * logWeight);
			if (logPoints > 0) {
				total += logPoints;
			}
			else {
				System.out.println("Warning: Bias decreases log " + i + "'s points below zero. Setting to zero instead.");
			}
		}
		double average = total / logsToCalculate.size();
		
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
			DefectLog newDefect = new DefectLog(hours, minutes);
			newLog.addDefectLog(newDefect);
		}
		
		// Add the new log to the database
		p.addLog(newLog);
	}
}
