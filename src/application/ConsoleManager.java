package application;

public class ConsoleManager {
	private Database database;
	
	public ConsoleManager(Database d) {
		database = d;
	}
	
	public boolean CreateNewEffortLog(int hours, int minutes) {
		EffortLog newLog = new EffortLog(hours, minutes);
		if (database.AddLog(newLog)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Overloaded method call for testing purposes
	public boolean CreateNewEffortLog(int hours, int minutes, double weight, int bias) {
		EffortLog newLog = new EffortLog(hours, minutes, weight, bias);
		if (database.AddLog(newLog)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void ClearEffortLogs() {
		database.ClearEffortLogs();
	}
}
