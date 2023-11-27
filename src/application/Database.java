package application;

import java.util.ArrayList;

// Database class written by Donovan Harp and Troy Reiling and Yashwant Gadhave

public class Database{
	// TO DO: Add separate project objects with their own effort log lists and project/task data
	
	// Effort logs list
	private ArrayList<Project> projects;
	private ArrayList<Definitions> definitions;
	private ArrayList<EffortLog> effortlog;
    private int projectCount = 0;
	
	public Database() {
		projects = new ArrayList<Project>();
		definitions = new ArrayList<Definitions>();
		effortlog = new ArrayList<EffortLog>();
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	public ArrayList<Definitions> getDefinitions() {
		return definitions;
	}
	
	public Definitions getDefinition(int index) {
		return definitions.get(index);
	}
	
	public Project getProject(int index) {
		return projects.get(index);
	}
	
	public void addProject(Project p) {
        projectCount++;
        p.setProjectNumber("" + projectCount);
		projects.add(p);
	}
	
	public void addDefinition(Definitions d) {
		definitions.add(d);
	}
	
	public void deleteDefinition(Definitions d) {
		definitions.remove(d);
	}
	
	public void deleteEffortLog(EffortLog e) {
		effortlog.remove(e);
	}
	
	public void deleteProject(Project p) {
        // I wanted to let deleted project #s be reused, but I've decided it's not worth it
		projects.remove(p);
	}
	
	public void clearDefinitions() {
		definitions.clear();
	}
	
	public void clearProjects() {
		projects.clear();
	}
	
	public ArrayList<EffortLog> getEffortLogs(Project p) {
		return p.getEffortLogs();
	}
	
	public void clearEffortLogs(Project p) {
		p.clearEffortLogs();
	}
	
	public boolean addLog(EffortLog log, Project p) {
		try {
			p.addLog(log);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public ArrayList<DefectLog> getDefectLogs(Project p) {
		return p.getDefectLogs();
	}
	
	public void clearDefectLogs(Project p) {
		p.clearDefectLogs();
	}
	
	public void addDefectLog(DefectLog log, Project p) {
		if (p != null) {
            p.addDefectLog(log);
            updateProject(p);
        } else {
            System.out.println("Project is null in addDefectLog");
        	}
	}
	
	public void updateDefectLog(DefectLog log, Project p) {
	    if (p != null) {
	        p.updateDefectLog(log);
	        updateProject(p);
	    } else {
	        System.out.println("Project is null in updateDefectLog");
	    }
	}

	
	public void updateProject(Project p) {
	    int projectIndex = findProjectIndex(p);
	    if (projectIndex != -1) {
	        projects.set(projectIndex, p);
	    } else {
	        System.out.println("Project not found for updating.");
	    }
	}

    private int findProjectIndex(Project p) {
        return projects.indexOf(p);
    }
}
