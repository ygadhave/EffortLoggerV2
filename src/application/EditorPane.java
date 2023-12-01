// EditorPane.java
package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class EditorPane extends VBox {
    private EditorManager manager;

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

    public EditorPane(EditorManager m) {
        manager = m;
        initializeUI();
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
                new Label("Subordinate Select List: "), subordinateSelectListComboBox,
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

    private void handleUpdateEntry() {
    	Main.getInstance().resetAfkTimer();
        EffortLog selectedLog = effortLogEntryComboBox.getValue();
        Project selectedProject = projectComboBox.getValue();
        EffortLog updatedLog = createEffortLogFromFields();

        if (selectedProject != null && selectedLog != null && updatedLog != null) {
            manager.updateEffortLogEntry(selectedLog, updatedLog, selectedProject);
            clearFields();

        }
    }

    private void handleDeleteEntry() {
    	Main.getInstance().resetAfkTimer();
        EffortLog selectedLog = effortLogEntryComboBox.getValue();
        Project selectedProject = projectComboBox.getValue();

        if (selectedLog != null && selectedProject != null) {
            manager.deleteEntry(selectedLog);
            clearFields();

        }
    }

    private void handleSplitEntry() {
    	Main.getInstance().resetAfkTimer();
        EffortLog originalLog = effortLogEntryComboBox.getValue();
        Project selectedProject = projectComboBox.getValue();

        if (originalLog != null && selectedProject != null) {
            manager.splitEffortLogEntry(originalLog, selectedProject);
            clearFields();

        }
    }

    private void handleClearLog() {
    	Main.getInstance().resetAfkTimer();
        Project selectedProject = projectComboBox.getValue();

        if (selectedProject != null) {
            manager.clearLog(selectedProject);
            clearFields();
        }
    }

    private void handleToConsole() {
    	Main.getInstance().resetAfkTimer();
        manager.navigateToConsole();
    }

    private EffortLog createEffortLogFromFields() {
    	Main.getInstance().resetAfkTimer();
       
        EffortLog newLog = new EffortLog();
        newLog.setStartTime(startTimeTextField.getText());
        newLog.setStopTime(stopTimeTextField.getText());

        return newLog;
    }

    private void clearFields() {
    	Main.getInstance().resetAfkTimer();

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
