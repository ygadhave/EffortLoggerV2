// EditorManager.java
package application;

import java.util.Arrays;
import java.util.List;

public class EditorManager {
	private Database database;

    public EditorManager(Database d) {
        this.database = d;
    }

	public void updateEffortLogEntry(EffortLog oldEntry, EffortLog newEntry, Project project) {
		int index = project.getEffortLogs().indexOf(oldEntry);
		if (index != -1) {
			project.getEffortLogs().set(index, newEntry);
		}
	}

	public void splitEffortLogEntry(EffortLog originalEntry, String midPointTime, Project project) {
	    int index = project.getEffortLogs().indexOf(originalEntry);
	    if (index != -1) {
	    	
	        EffortLog firstPart = new EffortLog(); 
	        EffortLog secondPart = new EffortLog();


	        firstPart.copyPropertiesFrom(originalEntry);
	        secondPart.copyPropertiesFrom(originalEntry);

	        firstPart.setStopTime(midPointTime);
	        secondPart.setStartTime(midPointTime);

	        project.getEffortLogs().add(index, firstPart); 
	        project.getEffortLogs().add(index + 1, secondPart); 

	        project.getEffortLogs().remove(originalEntry);
	    }
	}

    


	public void clearLog(Project project) {
		database.clearEffortLogs(project);
	}

	public void navigateToConsole() {
		
	}
	
	public List<Project> getProjects() {
	    return database.getProjects();
	}
	
	public List<EffortLog> getEffortLogs(Project project) {
        return database.getEffortLogs(project);
    }
	
	public void deleteEntry(EffortLog selectedEffortLog) {
		database.deleteEffortLog(selectedEffortLog);
	}
	
	public List<String> getLifeCycleSteps() {
        return Arrays.asList("Planning", "Development", "Testing", "Deployment"); 
	}
	
	public List<String> getEffortCategories() {
        return Arrays.asList("Design", "Coding", "Testing", "Documentation");
    }
	
	public List<String> getEffortPlan() {
        return Arrays.asList("Project Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
    }
	
}

