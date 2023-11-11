package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// made by Troy Reiling

public class DefinitionsManager {
    private Database database;

    public DefinitionsManager(Database d) {
        database = d;
        
        // create a default definition for example
        Definitions defaultDefinition = new Definitions("Default Cycle", "Plans", "Project Plan");
        database.addDefinition(defaultDefinition);
    }
    
    public void addProject(Project project) {
        database.addProject(project);
    }

    // Would probably be good to have a check to make sure the user doesn't delete all projects
    // At least one is needed at all times
    public void deleteProject(Project project) {
        database.deleteProject(project);
    }

    public ObservableList<Project> getProjects() {
        return FXCollections.observableArrayList(database.getProjects());
    }

    public ObservableList<Definitions> getDefinitions() {
    	return FXCollections.observableArrayList(database.getDefinitions());
    }

    public void addDefinition(Definitions definition) {
        database.addDefinition(definition);
    }

    public void deleteDefinition(Definitions definition) {
        database.deleteDefinition(definition);
    }

    public void updateDefinition(Definitions oldDefinition, Definitions newDefinition) {
        int index = database.getDefinitions().indexOf(oldDefinition);
        if (index != -1) {
        	database.getDefinitions().set(index, newDefinition);
        }
    }
}
