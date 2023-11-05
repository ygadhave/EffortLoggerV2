package application;

import java.util.ArrayList;

// Database class written by Donovan Harp

public class Database{
	// TO DO: Add separate project objects with their own effort log lists and project/task data
	
	// Effort logs list
	private ArrayList<Project> projects;
	private Definitions definitions;
	
	public Database() {
		projects = new ArrayList<Project>();
		definitions = new Definitions();
	}
	
	public ArrayList<Project> GetProjects() {
		return projects;
	}
	
	public Project GetProject(int index) {
		return projects.get(index);
	}
	
	public void AddProject(Project p) {
		projects.add(p);
	}
	
	public void ClearProjects() {
		projects.clear();
	}
	
	public ArrayList<EffortLog> GetEffortLogs(Project p) {
		return p.GetEffortLogs();
	}
	
	public void ClearEffortLogs(Project p) {
		p.ClearEffortLogs();
	}
	
	public boolean AddLog(EffortLog log, Project p) {
		try {
			p.AddLog(log);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
