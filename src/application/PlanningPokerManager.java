package application;

import java.util.ArrayList;

public class PlanningPokerManager {
	private Database database;
	
	public PlanningPokerManager(Database d) {
		database = d;
	}
	
	public ArrayList<EffortLog> GetEffortLogs() {
		return database.GetEffortLogs();
	}
	
	// Function to calculate the story points for an effort log
	public void CalculateStoryPoints(EffortLog log, int pointsPerHour) {
		int hours = log.GetHours();
		int minutes = log.GetMinutes();
		int points = (hours * pointsPerHour) + (minutes * (pointsPerHour / 60));
		log.SetStoryPoints(points);
	}
	
	// Function to calculate story points for every effort log
	public void CalculateAllStoryPoints(ArrayList<EffortLog> logsToCalculate, int pointsPerHour) {
		for (int i = 0; i < logsToCalculate.size(); i++) {
			CalculateStoryPoints(logsToCalculate.get(i), pointsPerHour);
		}
	}
	
	// Function to calculate the weighted and biased average of the story points for all effort logs
	//    (Calls story points function)
	public int CalculateAverage(ArrayList<EffortLog> logsToCalculate, int pointsPerHour) {
		// Check if there are no logs in the list
		if (logsToCalculate.size() < 1) {
			System.out.println("Warning: No effort logs selected.");
			return 0;
		}
		
		// Calculate un-weighted/biased story points for each log
		CalculateAllStoryPoints(logsToCalculate, pointsPerHour);
		
		// Calculate the average
		double total = 0;
		for (int i = 0; i < logsToCalculate.size(); i++) {
			EffortLog log = logsToCalculate.get(i);
			
			// If the weight was put in as a negative, treat it like a positive
			double logWeight = log.GetWeight();
			if (logWeight < 0) {
				logWeight *= -1;
				System.out.println("Warning: Log " + i + "'s weight is below zero. Using absolute value.");
			}
			
			double logPoints = ((log.GetStoryPoints() + log.GetBias()) * logWeight);
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
}
