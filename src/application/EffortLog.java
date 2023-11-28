package application;

import java.io.Serializable;
import java.util.ArrayList;

// EffortLog class written by Donovan Harp
public class EffortLog implements Serializable {
	private static final long serialVersionUID = 1L;
	// Data attributes
	private int effortHours;
	private int effortMinutes;
	private double weight;
	private int bias;
	private int storyPoints;
	private boolean selected;
	
	private ArrayList<DefectLog> defectLogs;
	
	// Default Constructor
	public EffortLog() {
		effortHours = 0;
		effortMinutes = 0;
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
		
		defectLogs = new ArrayList<DefectLog>();
	}
	
	// Basic Constructor
	public EffortLog(int hours, int minutes) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = 1;
		bias = 0;
		storyPoints = -1;
		selected = true;
		
		defectLogs = new ArrayList<DefectLog>();
	}
	
	// Advanced Constructor
	public EffortLog(int hours, int minutes, double w, int b) {
		effortHours = Math.abs(hours);
		effortMinutes = Math.abs(minutes);
		weight = w;
		bias = b;
		storyPoints = -1;
		selected = true;
		
		defectLogs = new ArrayList<DefectLog>();
	}
	
	public void setTime(int hours, int minutes) {
		effortHours = hours;
		effortMinutes = minutes;
	}
	
	public int getHours() {
		return effortHours;
	}
	
	public int getMinutes() {
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
	
	public ArrayList<DefectLog> getDefectLogs() {
		return defectLogs;
	}
	
	public void addDefectLog(DefectLog newLog) {
		defectLogs.add(newLog);
	}
}
