package application;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	private String projectName;
    private String projectNumber;
	private String userStoryName;
	private String userStoryDescription;
	private ArrayList<EffortLog> effortLogs;
	private ArrayList<DefectLog> defectLogs;
    private List<String> lifeCycleSteps;
    private Map<String, Definitions> lifeCycleDefinitions;
	
	public Project() {
		projectName = "";
        projectNumber = ""; // Initialize with an empty string
		userStoryName = "";
		userStoryDescription = "";
		effortLogs = new ArrayList<EffortLog>();
		defectLogs = new ArrayList<DefectLog>();
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
		defectLogs = new ArrayList<DefectLog>();
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
		defectLogs = new ArrayList<DefectLog>();
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

	public ArrayList<DefectLog> getDefectLogs() {
        	return defectLogs;
    }
    
    public void clearDefectLogs() {
        defectLogs.clear();
    }
    
    public void addDefectLog(DefectLog defectLog) {
        defectLogs.add(defectLog);
    }


    public void deleteDefectLog(DefectLog defectLog) {
        defectLogs.remove(defectLog);
    }

    public int findDefectLogIndex(DefectLog defectLog) {
        return defectLogs.indexOf(defectLog);
    }

    public void updateDefectLog(DefectLog updatedDefect) {
        for (int i = 0; i < defectLogs.size(); i++) {
            DefectLog existingDefect = defectLogs.get(i);
            if (existingDefect.getDefectName().equals(updatedDefect.getDefectName())) {
                // assumes that the defect names are unique
                defectLogs.set(i, updatedDefect);
                return;
            }
        }
	    // i still have some debug and stuff that i need to clean up
        System.out.println("Defect log not found for updating.");
    }

    public void updateDefectLog(int index, DefectLog newDefect) {
        // Replace the defect log at the specified index
        if (index >= 0 && index < defectLogs.size()) {
            defectLogs.set(index, newDefect);
        } else {
            System.out.println("Index out of bounds for defect logs.");
        }
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
