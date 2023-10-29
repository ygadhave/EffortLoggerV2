package application;

public class EffortLog {
	private int effortHours;
	private int effortMinutes;
	private double weight;
	private int bias;
	private int storyPoints;
	
	// Default Constructor
	public EffortLog() {
		effortHours = 0;
		effortMinutes = 0;
		weight = 1;
		bias = 0;
		storyPoints = -1;
	}
	
	// Basic Constructor
	public EffortLog(int hours, int minutes) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = 1;
		bias = 0;
		storyPoints = -1;
	}
	
	// Advanced Constructor
	public EffortLog(int hours, int minutes, double w, int b) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = w;
		bias = b;
		storyPoints = -1;
	}
	
	public void SetTime(int hours, int minutes) {
		effortHours = hours;
		effortMinutes = minutes;
	}
	
	public int GetHours() {
		return effortHours;
	}
	
	public int GetMinutes() {
		return effortMinutes;
	}
	
	public void SetWeight(double w) {
		weight = w;
	}
	
	public double GetWeight() {
		return weight;
	}
	
	public void SetBias(int b) {
		weight = b;
	}
	
	public int GetBias() {
		return bias;
	}
	
	public void SetStoryPoints(int points) {
		storyPoints = points;
	}
	
	public int GetStoryPoints() {
		return storyPoints;
	}
}
