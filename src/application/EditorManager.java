// EditorManager.java
package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.List;

public class EditorManager {
	private Database database;

	public EditorManager(Database d) {
		database = d;
	}

	public void updateEffortLogEntry(EffortLog oldEntry, EffortLog newEntry, Project project) {
		int index = project.getEffortLogs().indexOf(oldEntry);
		if (index != -1) {
			project.getEffortLogs().set(index, newEntry);
		}
	}

	public void splitEffortLogEntry(EffortLog originalEntry, Project project) {
        int index = project.getEffortLogs().indexOf(originalEntry);
        if (index != -1) {
            EffortLog newEntry = createSplitEffortLog(originalEntry);
            String midPointTime = calculateMidPoint(originalEntry);
            
            originalEntry.setStopTime(midPointTime);
            newEntry.setStartTime(midPointTime);

            project.getEffortLogs().add(index + 1, newEntry);
        }
    }

    private EffortLog createSplitEffortLog(EffortLog originalEntry) {
 
        EffortLog newEntry = new EffortLog();
        newEntry.setStartTime(originalEntry.getStartTime());
        newEntry.setWeight(originalEntry.getWeight());
        newEntry.setBias(originalEntry.getBias());
        newEntry.setStoryPoints(-1);
        newEntry.setSelected(originalEntry.getSelected());
        newEntry.setDefinitions(originalEntry.getDefinitions());

        return newEntry;
    }

    private String calculateMidPoint(EffortLog originalEntry) {

        String startTime = originalEntry.getStartTime();
        String stopTime = originalEntry.getStopTime();


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date startDate = sdf.parse(startTime);
            Date stopDate = sdf.parse(stopTime);


            long midpointMillis = startDate.getTime() + (stopDate.getTime() - startDate.getTime()) / 2;

            return sdf.format(new Date(midpointMillis));
        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00:00";
        }
    }


	public void clearLog(Project project) {
		database.clearEffortLogs(project);
	}

	public void navigateToConsole() {
		//Application.getNavigationManager().navigateToEffortLogConsole();
	}
	
	public void deleteEntry(EffortLog selectedEffortLog) {
		database.deleteEffortLog(selectedEffortLog);
	}
}

