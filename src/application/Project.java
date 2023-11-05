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
	
	public String GetProjectName() {
		return projectName;
	}
	
	public void SetProjectName(String newName) {
		projectName = newName;
	}
	
	public String GetUserStoryName() {
		return userStoryName;
	}
	
	public void SetUserStoryName(String newName) {
		userStoryName = newName;
	}
	
	public String GetUserStoryDescription() {
		return userStoryDescription;
	}
	
	public void SetUserStoryDescription(String newDesc) {
		userStoryDescription = newDesc;
	}
	
	public ArrayList<EffortLog> GetEffortLogs() {
		return effortLogs;
	}
	
	public void ClearEffortLogs() {
		effortLogs.clear();
	}
	
	public void AddLog(EffortLog newLog) {
		effortLogs.add(newLog);
	}
}
