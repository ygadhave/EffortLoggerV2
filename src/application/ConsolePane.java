package application;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

//written by Jaylene Nunez
public class ConsolePane extends VBox {

    private ConsoleManager manager;
    private Label resultText;
    private Button startButton;
    private Button stopButton;

    private Timeline timeline;
    private long startTime;
    private Label remainingTimeLabel;

    // Constructor
    public ConsolePane(ConsoleManager m) {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), this::updateClock));
        timeline.setCycleCount(Animation.INDEFINITE);

        manager = m;

        ComboBox<Project> comboBox1 = new ComboBox<>();
        if (manager != null && manager.getDatabase() != null) {
            comboBox1.getItems().addAll(manager.getDatabase().getProjects());
            comboBox1.setPromptText("Select a project");

            comboBox1.setOnAction(event -> {
                manager.setSelectedProject(comboBox1.getValue());
                startButton.setDisable(manager.getSelectedProject() == null);
            });
        }

        Button newProjectButton = new Button("New Project");
        newProjectButton.setOnAction(event -> createNewProject(comboBox1));

        FlowPane comboBoxPane = new FlowPane();
        comboBoxPane.setAlignment(javafx.geometry.Pos.CENTER);
        comboBoxPane.setHgap(10); // Set horizontal gap between nodes

        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll("Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
        comboBox2.setPromptText("Select Life Cycle Step");

        ComboBox<String> comboBox3 = new ComboBox<>();
        ComboBox<String> comboBox4 = new ComboBox<>();
        comboBox3.getItems().addAll("Plans", "Deliverables", "Interruptions", "Defects");
        comboBox3.setPromptText("Select Effort Category");
        comboBox4.getItems().addAll("Project Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
        comboBox4.setPromptText("Select Plan");

        comboBoxPane.getChildren().addAll(comboBox2, comboBox3, comboBox4);

        HBox toolBar = new HBox(25);
        toolBar.setSpacing(10); // Set spacing between elements
        startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green;");
        stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: red;");
        toolBar.setAlignment(javafx.geometry.Pos.CENTER);

        remainingTimeLabel = new Label("Clock: 00:00");
        remainingTimeLabel.setStyle("-fx-font-size: 20px;");

        resultText = new Label("");

        startButton.setDisable(true); // Disable the "Start" button initially

        startButton.setOnAction(new StartButtonHandler());
        stopButton.setOnAction(new StopButtonHandler());

        Pane space = new Pane();
        space.setMinHeight(20);
        HBox.setHgrow(space, Priority.ALWAYS);

        toolBar.getChildren().addAll(comboBox1, newProjectButton, startButton, stopButton);
        this.getChildren().addAll(resultText, remainingTimeLabel, toolBar, comboBoxPane);
        this.setAlignment(javafx.geometry.Pos.CENTER);
    }

    // Start button handler
    private class StartButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (manager.getSelectedProject() != null && !timeline.getStatus().equals(Animation.Status.RUNNING)) {
                startTime = System.currentTimeMillis();
                timeline.playFromStart();
                resultText.setText("Clock started.");
                resultText.setStyle("-fx-font-size: 20px; -fx-background-color: green;");
            } else {
                resultText.setText("Cannot start the clock without a selected project or clock is already running.");
                resultText.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
            }
        }
    }

    // Stop button handler
    private class StopButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
                timeline.stop();
                long elapsedTime = System.currentTimeMillis() - startTime;
                long seconds = (elapsedTime / 1000) % 60;
                long milliseconds = elapsedTime % 1000;

                remainingTimeLabel.setText(String.format("Elapsed Time: %02d:%03d", seconds, milliseconds));
                resultText.setText("Clock stopped.");
                resultText.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
            } else {
                resultText.setText("Clock is not running.");
                resultText.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
            }
        }
    }

    // Create new project
    private void createNewProject(ComboBox<Project> comboBox1) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Project");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the name of the new project:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(projectName -> {
            // Create a new project and add it to the ComboBox
            Project newProject = new Project(projectName);
            manager.getDatabase().addProject(newProject);
            comboBox1.getItems().add(newProject);
        });
    }

    private void updateClock(ActionEvent event) {
        // Update the clock if needed
    }
}
