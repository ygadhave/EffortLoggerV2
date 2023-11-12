package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

//ConsolePane class written by Jaylene Nunez

public class ConsolePane extends VBox {

    // Manager
    private ConsoleManager manager;

    // UI Elements
    private TextField hoursField;
    private TextField minutesField;
    private Label resultText;
    private Button startButton;
    private Button stopButton;

    // Constructor
    public ConsolePane(ConsoleManager m) {
        // Initialize manager and UI elements
        VBox root = new VBox(10);
        HBox displayBar = new HBox(10);

        Pane spacer = new Pane();
        spacer.setMinHeight(10);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox newBar = new HBox(15);
        newBar.setAlignment(javafx.geometry.Pos.CENTER);

        manager = m;
        ComboBox<Project> comboBox1 = new ComboBox<>();
        if (manager != null && manager.getDatabase() != null) {
            
            comboBox1.getItems().addAll(manager.getDatabase().getProjects());
            comboBox1.setPromptText("Select a project");

            // Set the selected project in the ConsoleManager
            comboBox1.setOnAction(event -> manager.setSelectedProject(comboBox1.getValue()));
        }

        // Set the selected project in the ConsoleManager
        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll("Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
        comboBox2.setPromptText("Select Life Cycle Step");

        newBar.getChildren().addAll(comboBox1, comboBox2);

        Pane spaces = new Pane();
        spaces.setMinHeight(10);
        HBox.setHgrow(spaces, Priority.ALWAYS);

        HBox newBars = new HBox(20);
        ComboBox<String> comboBox3 = new ComboBox<>();
        ComboBox<String> comboBox4 = new ComboBox<>();
        newBars.setAlignment(javafx.geometry.Pos.CENTER);
        comboBox3.getItems().addAll("Plans", "Deliverables", "Interruptions", "Defects", "");
        comboBox3.setPromptText("Select Effort Category");
        comboBox4.getItems().addAll("Project Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
        comboBox4.setPromptText("Select Plan");
        newBars.getChildren().addAll(comboBox3, comboBox4);

        Pane spacious = new Pane();
        spacious.setMinHeight(10);
        HBox.setHgrow(spacious, Priority.ALWAYS);

        // ToolBar
        HBox toolBar = new HBox(25);
        startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green;");
        stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: red;");
        toolBar.setAlignment(javafx.geometry.Pos.CENTER);

        Pane space = new Pane();
        space.setMinHeight(20);
        HBox.setHgrow(space, Priority.ALWAYS);
     

        hoursField = new TextField();
        minutesField = new TextField();
        resultText = new Label("");

        // Bind Buttons
        startButton.setOnAction(new StartButtonHandler(resultText));
        stopButton.setOnAction(new StopButtonHandler(resultText));

        // Add UI elements to pane
        toolBar.getChildren().addAll(startButton, stopButton);
        this.getChildren().addAll(resultText, space, toolBar);
        this.setAlignment(javafx.geometry.Pos.CENTER);
        root.getChildren().addAll(displayBar, spacer, newBar, spaces, newBars, spacious, toolBar, space);

        VBox layout = new VBox();
        layout.getChildren().addAll(root);
        this.getChildren().addAll(layout);
    }

    private class StartButtonHandler implements EventHandler<ActionEvent> {
        private Label label;

        public StartButtonHandler(Label label) {
            this.label = label;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                // Check if a project is selected
                if (manager.getSelectedProject() != null) {
                    int hours = Integer.parseInt(hoursField.getText());
                    int minutes = Integer.parseInt(minutesField.getText());

                    // Call CreateNewEffortLog in ConsoleManager
                    boolean success = manager.CreateNewEffortLog(hours, minutes);
                    if (success) {
                        label.setText("Effort log created successfully.");
                        label.setStyle("-fx-font-size: 20px; -fx-background-color: green;");
                    } else {
                        label.setText("Failed to create effort log.");
                        label.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
                    }
                } else {
                    label.setText("Please select a project first.");
                    label.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
                }
            } catch (NumberFormatException e) {
                label.setText("Clock is running");
                label.setStyle("-fx-font-size: 20px; -fx-background-color: green;");
            }
        }
    }

    private class StopButtonHandler implements EventHandler<ActionEvent> {
        private Label label1;

        public StopButtonHandler(Label label) {
            this.label1 = label;
        }

        @Override
        public void handle(ActionEvent event) {
            label1.setText("Clock is stopped");
            label1.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
        }
    }
}
