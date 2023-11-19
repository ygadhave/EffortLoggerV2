package application;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

//PlanningPokerProjectInfoWindow class written by Donovan Harp

public class PlanningPokerProjectInfoWindow extends VBox {

	// References
	private PlanningPokerPane pane;
	private PlanningPokerManager manager;
	
	// Project Info
	private Project selectedProject;
	private String selectedProjectName;
	private String selectedProjectNumber;
	private String userStoryName;
	private String userStoryDescription;
	
	// UI Elements
	private ComboBox<Project> projectsDropdown;
	private Label userStoryNameLabel;
	private TextField userStoryNameField;
	private Label userStoryDescriptionLabel;
	private TextArea userStoryDescriptionArea;
	private Button saveChangesButton;
	
	public PlanningPokerProjectInfoWindow(PlanningPokerPane p, PlanningPokerManager m, Project project) {
		// Get References
		pane = p;
		manager = m;
		
		// Get project info
		selectedProject = project;
		selectedProjectName = selectedProject.getProjectName();
		selectedProjectNumber = selectedProject.getProjectNumber();
		userStoryName = selectedProject.getUserStoryName();
		userStoryDescription = selectedProject.getUserStoryDescription();
		
		// Initialize UI Elements
		ObservableList<Project> projectsList = FXCollections.observableArrayList(manager.getProjects());
		projectsDropdown = new ComboBox<Project>(projectsList);
		projectsDropdown.setValue(selectedProject);
		projectsDropdown.setOnAction(new ProjectsDropdownHandler());
		userStoryNameLabel = new Label("User Story Name:");
		userStoryNameField = new TextField();
		userStoryNameField.setText(selectedProject.getUserStoryName());
		userStoryDescriptionLabel = new Label("User Story Description:");
		userStoryDescriptionArea = new TextArea();
		userStoryDescriptionArea.setText(selectedProject.getUserStoryDescription());
		saveChangesButton = new Button("Save Changes");
		saveChangesButton.setOnAction(new SaveChangesButtonHandler());
		
		// Add UI Elements to VBox
		this.getChildren().addAll(projectsDropdown, userStoryNameLabel, userStoryNameField);
		this.getChildren().addAll(userStoryDescriptionLabel, userStoryDescriptionArea, saveChangesButton);
	}
	
	// Handles clicking the update project info button
	private class ProjectsDropdownHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				selectedProject = projectsDropdown.getValue();
				userStoryNameField.setText(selectedProject.getUserStoryName());
				userStoryDescriptionArea.setText(selectedProject.getUserStoryDescription());
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	// Handles clicking the update project info button
	private class SaveChangesButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				selectedProject = projectsDropdown.getValue();
				selectedProject.setUserStoryName(userStoryNameField.getText());
				selectedProject.setUserStoryDescription(userStoryDescriptionArea.getText());
				pane.updateProjectInfo(selectedProject);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
}
