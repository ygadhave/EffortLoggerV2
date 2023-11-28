package application;

import java.util.ArrayList;


public class DefectManager {
    private Database database;

    public DefectManager(Database d) {
        this.database = d;
    }

    public void addDefectLog(DefectLog log, Project project) {
        if (project != null && log != null) {
            project.addDefectLog(log); // Add the log to the project
            database.updateProject(project); // Update the project in the database
        } else {
            System.out.println("Project or Defect Log is null in addDefectLog.");
        }
    }

    public void updateDefectLog(DefectLog oldDefect, DefectLog newDefect, Project project) {
        if (project != null && oldDefect != null && newDefect != null) {
            int index = project.findDefectLogIndex(oldDefect);
            if (index != -1) {
                project.updateDefectLog(newDefect); // Use Project's method to update
                database.updateProject(project); // Update project in database
            } else {
                System.out.println("Defect log not found for updating.");
            }
        } else {
            System.out.println("Invalid parameters in updateDefectLog.");
        }
    }


    public void clearDefectLogs(Project project) {
        if (project != null) {
        	// needs to be fixed
            project.clearDefectLogs(); 
            database.updateProject(project); 
        } else {
            System.out.println("Project not found for clearing defect logs.");
        }
    }

    public void deleteDefectLog(DefectLog log, Project project) {
        if (project != null && log != null) {
            project.deleteDefectLog(log); // Remove the log from the project
            database.updateProject(project); // Update the project in the database
        } else {
            System.out.println("Project or Defect Log is null in deleteDefectLog.");
        }
    }

    public void toggleDefectStatus(DefectLog log, boolean status, Project project) {
    	// needs to be fixed
        if (log != null && project != null) {
            log.setIsClosed(status); // Update the status of the defect log
            database.updateDefectLog(log, project); // Reflect this change in the database
        } else {
            if (log == null) {
                System.out.println("Defect Log is null in toggleDefectStatus.");
            }
            if (project == null) {
                System.out.println("Project is null in toggleDefectStatus.");
            }
        }
    }


    public ArrayList<Project> getProjects() {
        return database.getProjects();
    }
    
    public void updateProject(Project project) {
        database.updateProject(project);
    }
}
