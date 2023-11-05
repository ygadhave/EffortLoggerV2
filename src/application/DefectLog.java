package application;

public class DefectLog {
	// Data attributes
	private int effortHours;
	private int effortMinutes;
	private double weight;
	private int bias;
	private int storyPoints;
	
	// Default Constructor
	public DefectLog() {
		effortHours = 0;
		effortMinutes = 0;
		weight = 1;
		bias = 0;
		storyPoints = -1;
	}
	
	// Basic Constructor
	public DefectLog(int hours, int minutes) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = 1;
		bias = 0;
		storyPoints = -1;
	}
	
	// Advanced Constructor
	public DefectLog(int hours, int minutes, double w, int b) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = w;
		bias = b;
		storyPoints = -1;
	}
}
