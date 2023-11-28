package application;

import java.io.Serializable;

// EffortLog class written by Donovan Harp and Yashwant Gadhave
public class EffortLog implements Serializable {
	private static final long serialVersionUID = 1L;
	// Data attributes
	private int effortHours;
	private int effortMinutes;
	private double weight;
	private int bias;
	private int storyPoints;
	private boolean selected;
	private Definitions definitions;
	private String startTime;
    private String stopTime;
	
	// Default Constructor
	public EffortLog() {
		effortHours = 0;
		effortMinutes = 0;
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
	}
	
	// Basic Constructor
	public EffortLog(int hours, int minutes) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
	}
	
	// Advanced Constructor
	public EffortLog(int hours, int minutes, double w, int b) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = w;
		bias = b;
		storyPoints = -1;
		selected = true;
	}
	
	public void setTime(int hours, int minutes) {
		effortHours = hours;
		effortMinutes = minutes;
	}
	
	public int getEffortHours() {
		return effortHours;
	}
	
	public int getEffortMinutes() {
		return effortMinutes;
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
	
	public void setDefinitions(Definitions d) {
		definitions = d;
	}
	
	public Definitions getDefinitions() {
		return definitions;
	}
	
	public void setStartTime(String startTime) {
        // You may want to perform additional validation here
        this.startTime = startTime;
    }
	
	// Get start time
    public String getStartTime() {
        return startTime;
    }

    // Set stop time
    public void setStopTime(String stopTime) {
        // You may want to perform additional validation here
        this.stopTime = stopTime;
    }
    
    public String getStopTime() {
        return stopTime;
    }
}
