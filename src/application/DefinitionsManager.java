package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// made by Troy Reiling

public class DefinitionsManager {
    private Database database;
    private ObservableList<Definitions> definitionsList;

    public DefinitionsManager(Database d) {
        database = d;
        definitionsList = FXCollections.observableArrayList();
        
        // create a default definition for example
        Definitions defaultDefinition = new Definitions("Default Cycle", "Plans", "Project Plan");
        definitionsList.add(defaultDefinition);
    }
    
    public void addProject(Project project) {
        database.addProject(project);
    }

    public void deleteProject(Project project) {
        database.deleteProject(project);
    }

    public ObservableList<Project> getProjects() {
        return FXCollections.observableArrayList(database.getProjects());
    }

    public ObservableList<Definitions> getDefinitions() {
        return definitionsList;
    }

    public void addDefinition(Definitions definition) {
        definitionsList.add(definition);
    }

    public void deleteDefinition(Definitions definition) {
        definitionsList.remove(definition);
    }

    public void updateDefinition(Definitions oldDefinition, Definitions newDefinition) {
        int index = definitionsList.indexOf(oldDefinition);
        if (index != -1) {
            definitionsList.set(index, newDefinition);
        }
    }
}
