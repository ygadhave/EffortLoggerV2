package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;

// made by Troy Reiling

public class DefinitionsPane extends VBox {
    private DefinitionsManager manager;
    private TableView<Definitions> definitionsTable;
    private TableView<Project> projectsTable;
    private Button addButton, editButton, deleteButton;
    private Button addProjectButton, editProjectButton, deleteProjectButton;

    public DefinitionsPane(DefinitionsManager m) {
        manager = m;
        setupUI();
    }

    private void setupUI() {
        // Setup Definitions Table
        definitionsTable = new TableView<>();
        setupDefinitionsTable();

        // Setup Projects Table
        projectsTable = new TableView<>();
        setupProjectsTable();

        // Setup Buttons for Definitions
        HBox buttonsHBox = new HBox();
        setupDefinitionButtons(buttonsHBox);

        // Setup Buttons for Projects
        HBox projectButtonsHBox = new HBox();
        setupProjectButtons(projectButtonsHBox);

        // Add components to pane
        this.getChildren().addAll(definitionsTable, buttonsHBox, projectsTable, projectButtonsHBox);
    }

    @SuppressWarnings("unchecked")
	private void setupDefinitionsTable() {
        TableColumn<Definitions, String> cycleNameColumn = new TableColumn<>("Cycle Name");
        cycleNameColumn.setCellValueFactory(new PropertyValueFactory<>("cycleName"));

        TableColumn<Definitions, String> effortCategoryColumn = new TableColumn<>("Effort Category");
        effortCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("effortCategory"));

        TableColumn<Definitions, String> deliverableColumn = new TableColumn<>("Deliverable");
        deliverableColumn.setCellValueFactory(new PropertyValueFactory<>("deliverable"));

        definitionsTable.getColumns().addAll(cycleNameColumn, effortCategoryColumn, deliverableColumn);
        definitionsTable.setItems(manager.getDefinitions());

        // Add listener for selection changes to enable/disable Edit and Delete buttons
        definitionsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isSelected = (newSelection != null);
            editButton.setDisable(!isSelected);
            deleteButton.setDisable(!isSelected);
        });
    }

    @SuppressWarnings("unchecked")
	private void setupProjectsTable() {
        TableColumn<Project, String> projectNumberColumn = new TableColumn<>("Project #");
        projectNumberColumn.setCellValueFactory(new PropertyValueFactory<>("projectNumber"));

        TableColumn<Project, String> projectNameColumn = new TableColumn<>("Project Name");
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));

        projectsTable.getColumns().addAll(projectNumberColumn, projectNameColumn);
        projectsTable.setItems(manager.getProjects());

        projectsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isSelected = (newSelection != null);
            editProjectButton.setDisable(!isSelected);
            deleteProjectButton.setDisable(!isSelected);
        });
    }

    private void setupDefinitionButtons(HBox hbox) {
        addButton = new Button("Add");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");

        addButton.setOnAction(event -> addDefinition());
        editButton.setOnAction(event -> editDefinition());
        deleteButton.setOnAction(event -> deleteDefinition());

        hbox.getChildren().addAll(addButton, editButton, deleteButton);
    }

    private void setupProjectButtons(HBox hbox) {
        addProjectButton = new Button("Add");
        editProjectButton = new Button("Edit");
        deleteProjectButton = new Button("Delete");

        addProjectButton.setOnAction(event -> addProject());
        editProjectButton.setOnAction(event -> editProject());
        deleteProjectButton.setOnAction(event -> deleteProject());

        hbox.getChildren().addAll(addProjectButton, editProjectButton, deleteProjectButton);
    }

    private void addDefinition() {
        // Add a new blank Defined Life Cycle named "New Cycle"
        manager.addDefinition(new Definitions());
        definitionsTable.refresh();
    }

    private void editDefinition() {
        Definitions selectedDefinition = definitionsTable.getSelectionModel().getSelectedItem();
        if (selectedDefinition != null) {
            Stage editStage = new Stage();
            VBox layout = new VBox(10);
            Scene editScene = new Scene(layout, 300, 250);

            TextField cycleNameField = new TextField(selectedDefinition.getCycleName());
            ComboBox<String> effortCategoryComboBox = new ComboBox<>();
            effortCategoryComboBox.getItems().addAll("Plans", "Documents", "Interruptions", "Defects", "Others");
            effortCategoryComboBox.setValue(selectedDefinition.getEffortCategory());

            ComboBox<String> deliverableComboBox = new ComboBox<>();
            updateDeliverableComboBox(effortCategoryComboBox, deliverableComboBox);
            deliverableComboBox.setValue(selectedDefinition.getDeliverable());

            effortCategoryComboBox.setOnAction(event -> updateDeliverableComboBox(effortCategoryComboBox, deliverableComboBox));

            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
                selectedDefinition.setCycleName(cycleNameField.getText());
                selectedDefinition.setEffortCategory(effortCategoryComboBox.getValue());
                selectedDefinition.setDeliverable(deliverableComboBox.getValue());
                definitionsTable.refresh();
                editStage.close();
            });

            layout.getChildren().addAll(new Label("Cycle Name:"), cycleNameField, 
                                        new Label("Effort Category:"), effortCategoryComboBox,
                                        new Label("Deliverable:"), deliverableComboBox,
                                        saveButton);

            editStage.setScene(editScene);
            editStage.setTitle("Edit Definition");
            editStage.show();
        }
    }
    
    private void updateDeliverableComboBox(ComboBox<String> effortCategoryComboBox, ComboBox<String> deliverableComboBox) {
        deliverableComboBox.getItems().clear();
        switch (effortCategoryComboBox.getValue()) {
            case "Plans":
                deliverableComboBox.getItems().addAll("Project Plan", "Risk Management Plan");
                break;
            case "Documents":
                deliverableComboBox.getItems().addAll("Conceptual Design", "Detailed Design");
                break;
            case "Interruptions":
                deliverableComboBox.getItems().addAll("Break", "Phone");
                break;
            case "Defects":
                deliverableComboBox.getItems().addAll("10 Documentation", "20 Syntax");
                break;
            case "Others":
                // No deliverables for Others
                break;
        }
    }

    private void deleteDefinition() {
        Definitions selectedDefinition = definitionsTable.getSelectionModel().getSelectedItem();
        if (selectedDefinition != null) {
            Alert confirmDialog = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this definition?", ButtonType.YES, ButtonType.NO);
            confirmDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    manager.deleteDefinition(selectedDefinition);
                    definitionsTable.refresh();
                }
            });
        }
    }

    private void addProject() {
        // Add a new blank Project named "New Project"
        Project newProject = new Project("New Project");
        manager.addProject(newProject);
        projectsTable.setItems(manager.getProjects());
    }

    private void editProject() {
        Project selectedProject = projectsTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            Stage editStage = new Stage();
            VBox layout = new VBox(10);
            Scene editScene = new Scene(layout, 400, 300);

            TextField projectNameField = new TextField(selectedProject.getProjectName());

            // List for Life Cycle Steps
            ListView<HBox> lifeCycleStepsListView = new ListView<>();
            ObservableList<HBox> lifeCycleStepsItems = FXCollections.observableArrayList();

            // Fetching the list of Definitions
            ObservableList<Definitions> definitionsList = manager.getDefinitions();

            for (String step : selectedProject.getLifeCycleSteps()) {
                ComboBox<Definitions> lifeCycleComboBox = new ComboBox<>(definitionsList); // Initializing with definitionsList
                lifeCycleComboBox.setCellFactory(lv -> new ListCell<Definitions>() {
                    @Override
                    protected void updateItem(Definitions item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.getCycleName());
                    }
                });
                lifeCycleComboBox.setButtonCell(new ListCell<Definitions>() {
                    @Override
                    protected void updateItem(Definitions item, boolean empty) {
                        super.updateItem(item, empty);
                        setText((item == null || empty) ? "" : item.getCycleName());
                    }
                });

                // Set the current value for the ComboBox
                lifeCycleComboBox.setValue(selectedProject.getDefinedLifeCycleForStep(step));
                lifeCycleStepsItems.add(new HBox(new Label(step + ": "), lifeCycleComboBox));
            }
            lifeCycleStepsListView.setItems(lifeCycleStepsItems);

            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
                selectedProject.setProjectName(projectNameField.getText());
                for (int i = 0; i < selectedProject.getLifeCycleSteps().size(); i++) {
                    HBox hbox = lifeCycleStepsItems.get(i);
                    ComboBox<Definitions> comboBox = (ComboBox<Definitions>) hbox.getChildren().get(1);
                    selectedProject.setDefinedLifeCycleForStep(selectedProject.getLifeCycleSteps().get(i), comboBox.getValue());
                }
                projectsTable.refresh();
                editStage.close();
            });

            layout.getChildren().addAll(new Label("Project Name:"), projectNameField, 
                                        new Label("Life Cycle Steps:"), lifeCycleStepsListView,
                                        saveButton);

            editStage.setScene(editScene);
            editStage.setTitle("Edit Project");
            editStage.show();
        }
    }

    private void deleteProject() {
        Project selectedProject = projectsTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            Alert confirmDialog = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this project?", ButtonType.YES, ButtonType.NO);
            confirmDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    manager.deleteProject(selectedProject);
                    projectsTable.setItems(manager.getProjects());
                }
            });
        }
    }
}