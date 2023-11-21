package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
//import java.util.ArrayList;

public class DefectPane extends BorderPane {
	
    private DefectManager defectManager;
    private Database database;
    
    private ComboBox<Project> projectComboBox;
    private ComboBox<DefectLog> defectComboBox;
    private ComboBox<String> defectCategoryComboBox;
    private ComboBox<String> stepWhenInjectedComboBox;
    private ComboBox<String> stepWhenRemovedComboBox;
    private ComboBox<String> fixComboBox;
    private TextField defectNameField;
    private TextArea defectSymptomsArea;
    private Button createDefectButton, clearDefectsButton, updateDefectButton, deleteDefectButton, proceedToEffortLogButton;
    private ToggleButton closeDefectButton, reopenDefectButton;
    private Label statusLabel;

    public DefectPane(DefectManager m, Database db) {
        this.defectManager = m;
        this.database = db;
        initializeComponents();
        layoutComponents();
    }
    
    

    private void initializeComponents() {
    	projectComboBox = new ComboBox<>();
        projectComboBox.setPromptText("Select Project");
        projectComboBox.getItems().addAll(database.getProjects());
        projectComboBox.setOnAction(e -> {
            Project selectedProject = projectComboBox.getValue();
            if (selectedProject != null) {
                defectComboBox.getItems().clear();
                defectComboBox.getItems().addAll(selectedProject.getDefectLogs());
            }
        });
        
        defectComboBox = new ComboBox<>();
        defectComboBox.setPromptText("- new defect -");

        
        stepWhenInjectedComboBox = new ComboBox<>();
        stepWhenInjectedComboBox.setPromptText("Step when injected");

        stepWhenRemovedComboBox = new ComboBox<>();
        stepWhenRemovedComboBox.setPromptText("Step when removed");
        
        fixComboBox = new ComboBox<>();
        fixComboBox.setPromptText("                         Fix         "
        		+ "        ");
        
        defectCategoryComboBox = new ComboBox<>();
        defectCategoryComboBox.setPromptText("Defect Category");
        
        defectNameField = new TextField();
        defectNameField.setPromptText("Defect Name");

        defectSymptomsArea = new TextArea();
        defectSymptomsArea.setPromptText("Defect Symptoms or Cause/Resolution");
        
        statusLabel = new Label("Status: Open");
        
        createDefectButton = new Button("Create a New Defect");
        clearDefectsButton = new Button("Clear this Defect Log");
        updateDefectButton = new Button("Update the Current Defect");
        deleteDefectButton = new Button("Delete the Current Defect");
        proceedToEffortLogButton = new Button("Proceed to the Effort Log Console");

        closeDefectButton = new ToggleButton("Close");
        reopenDefectButton = new ToggleButton("Reopen");

        statusLabel = new Label("Status: Open");
        
        createDefectButton.setOnAction(e -> createDefectLog());
        clearDefectsButton.setOnAction(e -> clearDefects());
        updateDefectButton.setOnAction(e -> {
            DefectLog selectedDefect = defectComboBox.getValue(); 
            Project currentProject = projectComboBox.getValue(); 

            if (selectedDefect != null && currentProject != null) {
                DefectLog updatedDefect = new DefectLog();
                updatedDefect.setDefectName(defectNameField.getText());
                updatedDefect.setDefectCategory(defectCategoryComboBox.getValue());
                updatedDefect.setStepWhenInjected(stepWhenInjectedComboBox.getValue());
                updatedDefect.setStepWhenRemoved(stepWhenRemovedComboBox.getValue());
                updatedDefect.setDefectSymptoms(defectSymptomsArea.getText());
                updatedDefect.setFix(fixComboBox.getValue());
                updatedDefect.setIsClosed(selectedDefect.getIsClosed()); 

                defectManager.updateDefectLog(selectedDefect, updatedDefect, currentProject); 
            } else {
            	statusLabel.setText("Please select both a defect and a project.");
            }
        });

        deleteDefectButton.setOnAction(e -> deleteDefect());
        proceedToEffortLogButton.setOnAction(e -> proceedToEffortLog());
        closeDefectButton.setOnAction(e -> toggleDefectStatus(true));
        reopenDefectButton.setOnAction(e -> toggleDefectStatus(false));
        
        
        
        // Listener for defect selection
        defectComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldDefect, newDefect) -> {
            if (newDefect != null) {
                // Update UI with selected defect
                defectNameField.setText(newDefect.getDefectName());
                defectCategoryComboBox.setValue(newDefect.getDefectCategory());
                stepWhenInjectedComboBox.setValue(newDefect.getStepWhenInjected());
                stepWhenRemovedComboBox.setValue(newDefect.getStepWhenRemoved());
                defectSymptomsArea.setText(newDefect.getDefectSymptoms());
                fixComboBox.setValue(newDefect.getFix());
                // ... update other fields as needed ...
            } else {
                // Clear UI
                defectNameField.clear();
                defectCategoryComboBox.getSelectionModel().clearSelection();
                stepWhenInjectedComboBox.getSelectionModel().clearSelection();
                stepWhenRemovedComboBox.getSelectionModel().clearSelection();
                defectSymptomsArea.clear();
                fixComboBox.getSelectionModel().clearSelection();
                // ... clear other fields as needed ...
            }
        });

        // Update defect
        updateDefectButton.setOnAction(e -> {
            DefectLog selectedDefect = defectComboBox.getValue();
            Project currentProject = projectComboBox.getValue();
            if (selectedDefect != null && currentProject != null) {
            	DefectLog updatedDefect = new DefectLog();
            	selectedDefect.setDefectName(defectNameField.getText());
                selectedDefect.setDefectCategory(defectCategoryComboBox.getValue());
                selectedDefect.setStepWhenInjected(stepWhenInjectedComboBox.getValue());
                selectedDefect.setStepWhenRemoved(stepWhenRemovedComboBox.getValue());
                selectedDefect.setDefectSymptoms(defectSymptomsArea.getText());
                selectedDefect.setFix(fixComboBox.getValue());
                selectedDefect.setIsClosed(closeDefectButton.isSelected());
                
                defectManager.updateDefectLog(selectedDefect, updatedDefect, currentProject);
            } else {
                statusLabel.setText("Please select both a defect and a project.");
            }
        });

        createDefectButton.setOnAction(e -> createDefectLog());

        
        
        layoutComponents();
        
    }
    
    private void layoutComponents() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i < 10; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 10);
            grid.getColumnConstraints().add(colConst);
        }

        grid.add(new Label("1. Select the Project"), 0, 0, 3, 1); 
        grid.add(projectComboBox, 1, 1, 6, 1); 
        grid.add(new Label("2.a. Clear this Project's Defect Log."), 4, 0, 6, 1); 
        grid.add(clearDefectsButton, 5, 1, 7, 1); 

        grid.add(new Label("2.b. Select one of the following to make it the "
        		+ "Current Defect or press 'Create New Defect'."), 0, 2, 9, 1); 
        grid.add(defectComboBox, 1, 3, 6, 1); 
        grid.add(createDefectButton, 5, 3, 7, 1); 

        grid.add(new Label("3. Define or update the following Current Defect attributes:"), 0, 4, 6, 1); 
        grid.add(new Label("Num"), 0, 5); 
        grid.add(new Label("#"), 0, 6);
        grid.add(new Label("Defect Name"), 1, 5, 3, 1); 
        grid.add(defectNameField, 1, 6, 3, 1); 
        grid.add(new Label("Status: Closed"), 5, 5, 3, 1); 
        grid.add(new HBox(10, closeDefectButton, reopenDefectButton), 5, 6, 7, 7); 

        grid.add(new Label("Defect Symptoms or Cause/Resolution"), 0, 7, 4, 1); 
        grid.add(defectSymptomsArea, 0, 8, 6, 1); 

        grid.add(new Label("Defect Category:"), 6, 9, 3, 1); 
        grid.add(defectCategoryComboBox, 6, 10, 3, 1); 
        grid.add(new Label("Step when injected:"), 0, 9, 3, 1); 
        grid.add(stepWhenInjectedComboBox, 0, 10, 3, 1); 
        grid.add(new Label("Step when removed:"), 3, 9, 3, 1); 
        grid.add(stepWhenRemovedComboBox, 3, 10, 3, 1); 

        grid.add(new Label("These attributes have not been saved."), 0, 11, 4, 1); 
        grid.add(new Label("Fix: "), 3, 11, 1, 1); 
        grid.add(fixComboBox, 4, 11, 6, 1); 
        
        grid.add(new Label("4. Press 'Update the Current Defect' to save changes made above."), 0, 12, 10, 1);
        grid.add(updateDefectButton, 0, 13, 3, 1); 
        grid.add(deleteDefectButton, 3, 13, 3, 1); 
        grid.add(proceedToEffortLogButton, 6, 13, 3, 1); 

	
        stepWhenInjectedComboBox.getItems().clear();
        stepWhenInjectedComboBox.getItems().addAll(
                "Planning",
                "Information Gathering",
                "Information Understanding",
                "Verifying", "Outlining"
            );

        stepWhenRemovedComboBox.getItems().clear();
        stepWhenRemovedComboBox.getItems().addAll(
                "Planning",
                "Information Gathering",
                "Information Understanding",
                "Verifying", "Outlining"
            );

        defectCategoryComboBox.getItems().clear();
        defectCategoryComboBox.getItems().addAll(
                "Not specified",
                "10 Documentation",
                "20 Syntax",
                "30 Build, Package",
                "40 Assignment"
            );
                
	     this.setCenter(grid);     	
    }
        
    public void createDefectLog() {
        Project selectedProject = projectComboBox.getValue();
        if (selectedProject != null) {
            DefectLog newDefect = new DefectLog();
            newDefect.setDefectName(defectNameField.getText());
            newDefect.setDefectCategory(defectCategoryComboBox.getValue());
            newDefect.setStepWhenInjected(stepWhenInjectedComboBox.getValue());
            newDefect.setStepWhenRemoved(stepWhenRemovedComboBox.getValue());
            newDefect.setDefectSymptoms(defectSymptomsArea.getText());
            newDefect.setFix(fixComboBox.getValue());
            newDefect.setIsClosed(closeDefectButton.isSelected());

            defectManager.addDefectLog(newDefect, selectedProject);

            defectComboBox.getItems().add(newDefect);
            defectComboBox.getSelectionModel().select(newDefect);

            clearDefectInputFields();

            statusLabel.setText("New defect created and added to the project.");
        } else {
            statusLabel.setText("Please select a project to add a new defect.");
        }
    }

    public void clearDefectInputFields() {
        defectNameField.clear();
        defectCategoryComboBox.getSelectionModel().clearSelection();
        stepWhenInjectedComboBox.getSelectionModel().clearSelection();
        stepWhenRemovedComboBox.getSelectionModel().clearSelection();
        defectSymptomsArea.clear();
        fixComboBox.getSelectionModel().clearSelection();
        closeDefectButton.setSelected(false);
        reopenDefectButton.setSelected(true);
    }

    
    
    public void updateDefectLog() {
        DefectLog selectedDefect = defectComboBox.getValue();
        Project currentProject = projectComboBox.getValue();

        if (selectedDefect != null && currentProject != null) {
            DefectLog updatedDefect = new DefectLog();
            updatedDefect.setDefectName(defectNameField.getText());
            updatedDefect.setDefectCategory(defectCategoryComboBox.getValue());
            updatedDefect.setStepWhenInjected(stepWhenInjectedComboBox.getValue());
            updatedDefect.setStepWhenRemoved(stepWhenRemovedComboBox.getValue());
            updatedDefect.setDefectSymptoms(defectSymptomsArea.getText());
            updatedDefect.setFix(fixComboBox.getValue());
            updatedDefect.setIsClosed(closeDefectButton.isSelected());

            defectManager.updateDefectLog(selectedDefect, updatedDefect, currentProject);

            defectComboBox.getItems().setAll(currentProject.getDefectLogs());
            defectComboBox.getSelectionModel().select(updatedDefect);

            statusLabel.setText("Defect updated successfully.");
        } else {
            statusLabel.setText("Please select both a defect and a project.");
        }
    }


    

    private void clearDefects() {
        defectComboBox.getSelectionModel().clearSelection();
        defectCategoryComboBox.getSelectionModel().clearSelection();
        stepWhenInjectedComboBox.getSelectionModel().clearSelection();
        stepWhenRemovedComboBox.getSelectionModel().clearSelection();
        fixComboBox.getSelectionModel().clearSelection();
        defectNameField.clear();
        defectSymptomsArea.clear();
        statusLabel.setText("Status: Cleared");
    }


    private void deleteDefect() {
        DefectLog selectedDefect = defectComboBox.getValue();
        if (selectedDefect != null) {
            Project selectedProject = projectComboBox.getValue();
            if (selectedProject != null) {
                defectManager.deleteDefectLog(selectedDefect, selectedProject);
                defectComboBox.getItems().remove(selectedDefect);
                statusLabel.setText("Defect deleted successfully.");
            } else {
                statusLabel.setText("Selected project is null.");
            }
        } else {
            statusLabel.setText("Please select a defect to delete.");
        }
    }


    private void proceedToEffortLog() {
        System.out.println("Proceed to Effort Log button clicked");
    }

    private void toggleDefectStatus(boolean isClosed) {
        DefectLog selectedDefect = defectComboBox.getValue();
        Project selectedProject = projectComboBox.getValue();
        if (selectedDefect != null && selectedProject != null) {
        	DefectLog updatedDefect = new DefectLog();
            selectedDefect.setIsClosed(isClosed);
            defectManager.updateDefectLog(selectedDefect,updatedDefect,  selectedProject); 
            statusLabel.setText("Defect status changed to " + (isClosed ? "Closed" : "Open"));
        } else {
            if (selectedDefect == null) {
                statusLabel.setText("Please select a defect to change its status.");
            } else {
                statusLabel.setText("Please select a project first.");
            }
        }
    }
	
    
    // temp getter methods for testing
    public ComboBox<Project> getProjectComboBox() {
        return projectComboBox;
    }

    public ComboBox<DefectLog> getDefectComboBox() {
        return defectComboBox;
    }

    public TextField getDefectNameField() {
        return defectNameField;
    }

    public ComboBox<String> getDefectCategoryComboBox() {
        return defectCategoryComboBox;
    }

    public TextArea getDefectSymptomsArea() {
        return defectSymptomsArea;
    }

    public Button getCreateDefectButton() {
        return createDefectButton;
    }

    public Button getUpdateDefectButton() {
        return updateDefectButton;
    }
    
}
