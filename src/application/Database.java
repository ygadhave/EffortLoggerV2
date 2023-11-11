package application;

import java.util.ArrayList;

// Database class written by Donovan Harp and Troy Reiling

public class Database{
	// TO DO: Add separate project objects with their own effort log lists and project/task data
	
	// Effort logs list
	private ArrayList<Project> projects;
	private Definitions definitions;
    private int projectCount = 0;
	
	public Database() {
		projects = new ArrayList<Project>();
		definitions = new Definitions();
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	public Project getProject(int index) {
		return projects.get(index);
	}
	
	public void addProject(Project p) {
        projectCount++;
        p.setProjectNumber("" + projectCount);
		projects.add(p);
	}
	
	public void deleteProject(Project p) {
        // I wanted to let deleted project #s be reused, but I've decided it's not worth it
		projects.remove(p);
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
}
