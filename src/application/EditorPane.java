// EditorPane.java
package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class EditorPane extends VBox {
    private EditorManager manager;
    private TabPane tabPane;
    private Tab consoleTab;
    private ComboBox<Project> projectComboBox;
    private ComboBox<EffortLog> effortLogEntryComboBox;
    private TextField dateTextField;
    private TextField startTimeTextField;
    private TextField stopTimeTextField;
    private ComboBox<String> lifeCycleStepComboBox;
    private ComboBox<String> effortCategoryComboBox;
    private ComboBox<String> subordinateSelectListComboBox;
    private TextField otherDetailsTextField;
    private Button updateEntryButton;
    private Button deleteEntryButton;
    private Button splitEntryButton;
    private Button clearLogButton;
    private Button toConsoleButton;
    private Text warningText;

    public EditorPane(EditorManager m, TabPane tabPane, Tab consoleTab) {
        this.manager = m;
        this.tabPane = tabPane;
        this.consoleTab = consoleTab;
        initializeUI();
    }
  

	public void loadData() {
        loadProjectData();
        loadLifeCycleStepData();
        loadEffortCategoryData();
        loadEffortPlan();
    }
    
    private void loadProjectData() {
        List<Project> projects = manager.getProjects();
        projectComboBox.getItems().setAll(projects);

        projectComboBox.valueProperty().addListener((obs, oldVal, newVal) -> loadEffortLogData(newVal));
    }
    
    private void loadEffortLogData(Project project) {
        if (project != null) {
            List<EffortLog> effortLogs = manager.getEffortLogs(project);
            effortLogEntryComboBox.getItems().clear();
            effortLogEntryComboBox.getItems().addAll(effortLogs);
        }
    }
    
    private void loadLifeCycleStepData() {

        List<String> lifeCycleSteps = manager.getLifeCycleSteps();
        lifeCycleStepComboBox.getItems().setAll(lifeCycleSteps);
    }

    private void loadEffortCategoryData() {
        // Assuming you have a method in EditorManager for this
        List<String> effortCategories = manager.getEffortCategories();
        effortCategoryComboBox.getItems().setAll(effortCategories);
    }
    
    private void loadEffortPlan() {
        // Assuming you have a method in EditorManager for this
        List<String> effortPlan = manager.getEffortPlan();
        subordinateSelectListComboBox.getItems().setAll(effortPlan);
    }
    

    private void initializeUI() {

        projectComboBox = new ComboBox<>();
        effortLogEntryComboBox = new ComboBox<>();
        dateTextField = new TextField();
        startTimeTextField = new TextField();
        stopTimeTextField = new TextField();
        lifeCycleStepComboBox = new ComboBox<>();
        effortCategoryComboBox = new ComboBox<>();
        subordinateSelectListComboBox = new ComboBox<>();
        otherDetailsTextField = new TextField();
        updateEntryButton = new Button("Update This Entry");
        deleteEntryButton = new Button("Delete This Entry");
        splitEntryButton = new Button("Split This Entry");
        clearLogButton = new Button("Clear This Effort Log");
        toConsoleButton = new Button("Proceed to the Effort Log Console");
        warningText = new Text();


        updateEntryButton.setOnAction(event -> handleUpdateEntry());
        deleteEntryButton.setOnAction(event -> handleDeleteEntry());
        splitEntryButton.setOnAction(event -> handleSplitEntry());
        clearLogButton.setOnAction(event -> handleClearLog());
        toConsoleButton.setOnAction(event -> handleToConsole());


        VBox inputSection = new VBox(
                new Label("Project: "), projectComboBox,
                new Label("Effort Log Entry: "), effortLogEntryComboBox,
                new Label("Date: "), dateTextField,
                new Label("Start Time: "), startTimeTextField,
                new Label("Stop Time: "), stopTimeTextField,
                new Label("Life Cycle Step: "), lifeCycleStepComboBox,
                new Label("Effort Category: "), effortCategoryComboBox,
                new Label("Plan: "), subordinateSelectListComboBox,
                new Label("Other Details / User Defined Details: "), otherDetailsTextField
        );

        HBox buttonSection = new HBox(
                updateEntryButton, deleteEntryButton, splitEntryButton, clearLogButton, toConsoleButton
        );

        getChildren().addAll(
                inputSection,
                buttonSection,
                warningText
        );

        setSpacing(10);
        setPadding(new Insets(10));
    }
    
    private void handleToConsole() {
        if (tabPane != null && consoleTab != null) {
            tabPane.getSelectionModel().select(consoleTab);
        }
    }


    private void handleUpdateEntry() {
        EffortLog selectedLog = effortLogEntryComboBox.getValue();
        Project selectedProject = projectComboBox.getValue();

        if (selectedLog != null && selectedProject != null) {
            try {

                selectedLog.setStartTime(startTimeTextField.getText());
                selectedLog.setStopTime(stopTimeTextField.getText());
                selectedLog.setLifeCycleStep(lifeCycleStepComboBox.getValue());
                selectedLog.setEffortCategory(effortCategoryComboBox.getValue());

                manager.updateEffortLogEntry(selectedLog, selectedLog, selectedProject); 
                warningText.setText("Entry updated successfully.");
            } catch (Exception e) {
                warningText.setText("Error updating entry: " + e.getMessage());
            }
        } else {
            warningText.setText("No project or log entry selected.");
        }
    }

    private void handleDeleteEntry() {
        EffortLog selectedLog = effortLogEntryComboBox.getValue();
        Project selectedProject = projectComboBox.getValue();

        if (selectedLog != null && selectedProject != null) {
            manager.deleteEntry(selectedLog);
            loadEffortLogData(selectedProject); 
            warningText.setText("Entry deleted successfully.");
            clearFields();
        } else {
            warningText.setText("No project or log entry selected.");
        }
    }

    private void handleSplitEntry() {
        EffortLog originalLog = effortLogEntryComboBox.getValue();
        Project selectedProject = projectComboBox.getValue();

        if (originalLog != null && selectedProject != null) {
            String midPointTime = calculateMidPoint(originalLog);
            if (midPointTime != null) {
                manager.splitEffortLogEntry(originalLog, midPointTime, selectedProject);
                loadEffortLogData(selectedProject);
                warningText.setText("Entry split successfully.");
            } else {
                warningText.setText("Invalid start or stop time. Cannot calculate midpoint.");
            }
        } else {
            warningText.setText("No project or log entry selected.");
        }
    }



    private String calculateMidPoint(EffortLog log) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String startTimeStr = log.getStartTime();
        String stopTimeStr = log.getStopTime();

        if (startTimeStr == null || startTimeStr.isEmpty() || stopTimeStr == null || stopTimeStr.isEmpty()) {
            return null;
        }

        try {
            Date startDate = sdf.parse(startTimeStr);
            Date stopDate = sdf.parse(stopTimeStr);

            long midpointMillis = startDate.getTime() + (stopDate.getTime() - startDate.getTime()) / 2;
            return sdf.format(new Date(midpointMillis));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


	private void handleClearLog() {
        Project selectedProject = projectComboBox.getValue();

        if (selectedProject != null) {
            manager.clearLog(selectedProject);
            loadEffortLogData(selectedProject);
            warningText.setText("Effort log cleared successfully.");
            clearFields();
        } else {
            warningText.setText("No project selected.");
        }
    }


    private void clearFields() {

        dateTextField.clear();
        startTimeTextField.clear();
        stopTimeTextField.clear();
        lifeCycleStepComboBox.getSelectionModel().clearSelection();
        effortCategoryComboBox.getSelectionModel().clearSelection();
        subordinateSelectListComboBox.getSelectionModel().clearSelection();
        otherDetailsTextField.clear();
        warningText.setText("");
    }
}
