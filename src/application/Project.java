package application;

import java.util.ArrayList;

public class Project {
	private String projectName;
	private String userStoryName;
	private String userStoryDescription;
	private ArrayList<EffortLog> effortLogs;
	
	public Project() {
		projectName = "";
		userStoryName = "";
		userStoryDescription = "";
		effortLogs = new ArrayList<EffortLog>();
	}
	
	public Project(String name) {
		projectName = name;
		userStoryName = "";
		userStoryDescription = "";
		effortLogs = new ArrayList<EffortLog>();
	}
	
	public Project(String name, String storyName, String storyDesc) {
		projectName = name;
		userStoryName = storyName;
		userStoryDescription = storyDesc;
		effortLogs = new ArrayList<EffortLog>();
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String newName) {
		projectName = newName;
	}
	
	public String getUserStoryName() {
		return userStoryName;
	}
	
	public void setUserStoryName(String newName) {
		userStoryName = newName;
	}
	
	public String getUserStoryDescription() {
		return userStoryDescription;
	}
	
	public void setUserStoryDescription(String newDesc) {
		userStoryDescription = newDesc;
	}
	
	public ArrayList<EffortLog> getEffortLogs() {
		return effortLogs;
	}
	
	public void clearEffortLogs() {
		effortLogs.clear();
	}
	
	public void addLog(EffortLog newLog) {
		effortLogs.add(newLog);
	}
}
