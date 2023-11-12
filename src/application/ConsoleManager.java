package application;

// Console Manager class by Jaylene Nunez

    public class ConsoleManager {
        private Database database;
        private Project selectedProject;

     // Getter and Setter for selectedProject
     public Project getSelectedProject() {
         return selectedProject;
     }

     public void setSelectedProject(Project selectedProject) {
         this.selectedProject = selectedProject;
     }

        // Constructor
        public ConsoleManager(Database d) {
            database = d;
        }

        public Database getDatabase() {
            return database;
        }

        // Create a new effort log in the database
        public boolean CreateNewEffortLog(int hours, int minutes) {
            if (selectedProject != null) {
                EffortLog newLog = new EffortLog(hours, minutes);
                return database.addLog(newLog, selectedProject);
            } else {
                // Handle the case where no project is selected
                return false;
            }
        }

        // Overloaded method call for testing purposes
        public boolean CreateNewEffortLog(int hours, int minutes, double weight, int bias) {
            EffortLog newLog = new EffortLog(hours, minutes, weight, bias);
            return database.addLog(newLog, database.getProject(0));
        }

        // Clear all effort logs in the database
        public void ClearEffortLogs() {
            database.clearEffortLogs(database.getProject(0));
        }
    }
