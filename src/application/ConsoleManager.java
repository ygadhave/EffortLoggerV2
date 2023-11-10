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
	public boolean createNewEffortLog(int hours, int minutes) {
		EffortLog newLog = new EffortLog(hours, minutes);
		
		// TEMPORARY CODE FOR TESTING WITH DEFECT LOGS
		// DefectLog testDefect1 = new DefectLog(hours, 0, 1, 0);
		// DefectLog testDefect2 = new DefectLog(hours, 30, 1, 0);
		// newLog.AddDefectLog(testDefect1);
		// newLog.AddDefectLog(testDefect2);
		
		if (database.addLog(newLog, database.getProject(0))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Overloaded method call for testing purposes
	public boolean createNewEffortLog(int hours, int minutes, double weight, int bias) {
		EffortLog newLog = new EffortLog(hours, minutes, weight, bias);
		
		if (database.addLog(newLog, database.getProject(0))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Clear all effort logs in the database
	public void clearEffortLogs() {
		database.clearEffortLogs(database.getProject(0));
	}
}
