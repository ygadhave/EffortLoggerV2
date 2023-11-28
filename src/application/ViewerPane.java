package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class ViewerPane extends VBox {
	private ViewerManager manager;
	private Project selectedProject;
	
	private ComboBox<Project> projectsDropdown;
    private TableView<EffortLog> effortLogsTable;
    private TableView<DefectLog> defectLogsTable;
    
    public ViewerPane(ViewerManager m) {
        manager = m;
        selectedProject = manager.getProject(0);
        
        // Initialize UI elements
        updateProjectsDropdown();
		projectsDropdown.setOnAction(new ProjectsDropdownHandler());
        updateTables(selectedProject);

        // Add components to pane
        this.getChildren().addAll(projectsDropdown, effortLogsTable, defectLogsTable);
    }
    
    public void updateProjectsDropdown() {
    	ObservableList<Project> projectsList = FXCollections.observableArrayList(manager.getProjects());
		projectsDropdown = new ComboBox<Project>(projectsList);
		try {
			projectsDropdown.setValue(selectedProject);
		}
		catch (Exception e) {
			System.out.println("Error setting selected project after updating projects dropdown. Setting to first in list.");
			projectsDropdown.setValue(manager.getProject(0));
		}
    }
    
    public void updateTables(Project project) {
    	System.out.println("Updating tables for project " + project);
    	effortLogsTable = manager.setupEffortLogsTable(project);
    	defectLogsTable = manager.setupDefectLogsTable(project);
    }
    
    public Project getSelectedProject() {
    	return selectedProject;
    }
    
    // Handles updating the project dropdown
 	private class ProjectsDropdownHandler implements EventHandler<ActionEvent> {
 		public void handle(ActionEvent event) {
 			try {
 				System.out.println("Project selected: " + projectsDropdown.getValue());
 				selectedProject = projectsDropdown.getValue();
 				updateTables(selectedProject);
 			}
 			catch (Exception exception) {
 				System.out.println(exception.getMessage());
 			}
 		}
 	}
}
