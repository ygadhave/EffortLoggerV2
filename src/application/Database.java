package application;

import java.util.ArrayList;

// Database class written by Donovan Harp

public class Database{
	// TO DO: Add separate project objects with their own effort log lists and project/task data
	
	// Effort logs list
	private ArrayList<EffortLog> effortLogsList;
	
	public Database() {
		effortLogsList = new ArrayList<EffortLog>();
	}
	
	public ArrayList<EffortLog> GetEffortLogs() {
		return effortLogsList;
	}
	
	public void ClearEffortLogs() {
		effortLogsList.clear();
	}
	
	public boolean AddLog(EffortLog log) {
		try {
			effortLogsList.add(log);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
