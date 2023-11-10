package application;

public class DefectLog {
	// Data attributes
	private int defectHours;
	private int defectMinutes;
	private double weight;
	private int bias;
	private int storyPoints;
	private boolean selected;
	
	// Default Constructor
	public DefectLog() {
		defectHours = 0;
		defectMinutes = 0;
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
	}
	
	// Basic Constructor
	public DefectLog(int hours, int minutes) {
		defectHours = Math.abs(hours);
		defectMinutes = Math.abs(minutes);
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
	}
	
	// Advanced Constructor
	public DefectLog(int hours, int minutes, double w, int b) {
		defectHours = Math.abs(hours);
		defectMinutes = Math.abs(minutes);
		weight = w;
		bias = b;
		storyPoints = -1;
		selected = true;
	}
	
	public void setTime(int hours, int minutes) {
		defectHours = hours;
		defectMinutes = minutes;
	}
	
	public int getHours() {
		return defectHours;
	}
	
	public int getMinutes() {
		return defectMinutes;
	}
	
	public void setWeight(double w) {
		weight = w;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setBias(int b) {
		bias = b;
	}
	
	public int getBias() {
		return bias;
	}
	
	public void setStoryPoints(int points) {
		storyPoints = points;
	}
	
	public int getStoryPoints() {
		return storyPoints;
	}
	
	public void setSelected(boolean s) {
		selected = s;
	}
	
	public boolean getSelected() {
		return selected;
	}
}
