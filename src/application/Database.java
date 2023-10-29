package application;

import java.util.ArrayList;

public class Database{
	
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
