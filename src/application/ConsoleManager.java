package application;

// ConsoleManager class by Donovan Harp

public class ConsoleManager {
	private Database database;
	
	// NOTE: The 0 in GetProject(0) will be replaced with whatever the index corresponding
	//       with the current selection from the projects dropdown is
	
	// Constructor
	public ConsoleManager(Database d) {
		database = d;
	}
	
	// Create a new effort log in the database
	public boolean CreateNewEffortLog(int hours, int minutes) {
		EffortLog newLog = new EffortLog(hours, minutes);
		if (database.AddLog(newLog, database.GetProject(0))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Overloaded method call for testing purposes
	public boolean CreateNewEffortLog(int hours, int minutes, double weight, int bias) {
		EffortLog newLog = new EffortLog(hours, minutes, weight, bias);
		if (database.AddLog(newLog, database.GetProject(0))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Clear all effort logs in the database
	public void ClearEffortLogs() {
		database.ClearEffortLogs(database.GetProject(0));
	}
}
