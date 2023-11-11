package application;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
	private String projectName;
    private String projectNumber;
	private String userStoryName;
	private String userStoryDescription;
	private ArrayList<EffortLog> effortLogs;
    private List<String> lifeCycleSteps;
    private Map<String, Definitions> lifeCycleDefinitions;

	
	public Project() {
		projectName = "";
        projectNumber = ""; // Initialize with an empty string
		userStoryName = "";
		userStoryDescription = "";
		effortLogs = new ArrayList<EffortLog>();
        lifeCycleSteps = new ArrayList<>();
        lifeCycleDefinitions = new HashMap<>();
        // Initialize with some default steps for demonstration
        lifeCycleSteps.add("Step 1");
        lifeCycleSteps.add("Step 2");
        // ... add other steps as needed
	}
	
	public Project(String name) {
		projectName = name;
        projectNumber = ""; // Initialize with an empty string
		userStoryName = "";
		userStoryDescription = "";
		effortLogs = new ArrayList<EffortLog>();
        lifeCycleSteps = new ArrayList<>();
        lifeCycleDefinitions = new HashMap<>();
        // Initialize with some default steps for demonstration
        lifeCycleSteps.add("Step 1");
        lifeCycleSteps.add("Step 2");
        // ... add other steps as needed
	}
	
	public Project(String name, String storyName, String storyDesc) {
		projectName = name;
		projectNumber = "";
		userStoryName = storyName;
		userStoryDescription = storyDesc;
		effortLogs = new ArrayList<EffortLog>();
        lifeCycleSteps = new ArrayList<>();
        lifeCycleDefinitions = new HashMap<>();
        // Initialize with some default steps for demonstration
        lifeCycleSteps.add("Step 1");
        lifeCycleSteps.add("Step 2");
        // ... add other steps as needed
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String newName) {
		projectName = newName;
	}
	
    public String getProjectNumber() {
        return projectNumber;
    }
    
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
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
	
    public ObservableList<String> getLifeCycleSteps() {
        return FXCollections.observableArrayList(lifeCycleSteps);
    }

    public Definitions getDefinedLifeCycleForStep(String step) {
        return lifeCycleDefinitions.getOrDefault(step, null);
    }

    public void setDefinedLifeCycleForStep(String step, Definitions definitions) {
        lifeCycleDefinitions.put(step, definitions);
    }
}
