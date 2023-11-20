package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//User Orientation Prototype by Yashwant Gadhave
public class OnboardingPrototype extends Application {

    private int currentStep = 1;
    private String username;
    private String projectName;

    private Stage primaryStage;
    private Stage userOrientationStage;

    private EffortLog effortLog;

    private TaskState task1State = TaskState.NOT_COMPLETED;
    private TaskState task2State = TaskState.NOT_COMPLETED;
    private TaskState task3State = TaskState.NOT_COMPLETED;

    public OnboardingPrototype(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public enum TaskState {
        NOT_COMPLETED, COMPLETED
    }

    @Override
    public void start(Stage primaryStage) {
        this.userOrientationStage = primaryStage;
        primaryStage.setTitle("EffortLogger V2 - Onboarding Prototype");

        VBox root = new VBox(10);

        Scene scene = new Scene(root, 600, 400);

        updateUI(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateUI(VBox root) {
        root.getChildren().clear();

        if (currentStep == 1) {
            showSetPreferencesStep(root);
        } else if (currentStep == 2) {
            showLearnAboutFeaturesStep(root);
        } else if (currentStep == 3) {
            showRegistrationStep(root);
        } else if (currentStep == 4) {
            showProjectCreationStep(root);
        } else if (currentStep == 5) {
            showStoryPointsStep(root);
        } else if (currentStep == 6) {
            showWelcomeStep(root);
        }
    }

    public void calculateStoryPoints(EffortLog log, int pointsPerHour) {
        int hours = log.getHours();
        int minutes = log.getMinutes();
        int points = (hours * pointsPerHour) + (minutes * (pointsPerHour / 60));
        log.setStoryPoints(points);
    }

    private void showSetPreferencesStep(VBox root) {
        Label preferencesLabel = new Label("Set Your Preferences");

        Label languageLabel = new Label("Select your preferred language:");
        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English");
        languageComboBox.setValue("English");

        Button saveButton = new Button("Save Preferences");

        saveButton.setOnAction(e -> {

            String selectedLanguage = languageComboBox.getValue();

            System.out.println("Selected Language: " + selectedLanguage);

            currentStep = 2;
            updateUI(root);
        });

        root.getChildren().addAll(preferencesLabel, languageLabel, languageComboBox, saveButton);
    }

    private void showLearnAboutFeaturesStep(VBox root) {
        Stage learnFeaturesStage = new Stage();
        VBox learnFeaturesRoot = new VBox(10);

        Label featuresLabel = new Label("Learn about how to get started and features");

        Label feature1Label = createTaskLabel("Task 1: Creating Username and Password", task1State);
        Label feature1Description = new Label("Enter a valid username without any symbols and a password to create an account");

        Label feature2Label = createTaskLabel("Task 2: Create Project", task2State);
        Label feature2Description = new Label("Select the name for the project to continue");

        Label feature3Label = createTaskLabel("Task 3: Calculate story points", task3State);
        Label feature3Description = new Label("Enter hours and minutes to see the estimation of story points");

        Button selectButton = new Button("Next");

        selectButton.setOnAction(e -> {
            learnFeaturesStage.close();
            currentStep++;
            updateUI(root);
        });

        learnFeaturesRoot.getChildren().addAll(
                featuresLabel, feature1Label, feature1Description,
                feature2Label, feature2Description,
                feature3Label, feature3Description, selectButton);

        Scene learnFeaturesScene = new Scene(learnFeaturesRoot, 600, 400);
        learnFeaturesStage.setScene(learnFeaturesScene);
        learnFeaturesStage.show();
    }

    private Label createTaskLabel(String text, TaskState state) {
        Label label = new Label(text);

        if (state == TaskState.COMPLETED) {
            label.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        } else {
            label.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        }

        return label;
    }

    private void showRegistrationStep(VBox root) {
    	Label registrationLabel = new Label("Task 1: Register an Account");
        Label fDescription = new Label("Enter a valid username without any symbols and a password to create an account");
        Label registrationLabel1 = new Label("Create a username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        Label registrationLabel2 = new Label("Create a password");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter a password");
        Button registrationButton = new Button("Register");

        registrationButton.setOnAction(e -> {
        	
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();

            if (isValidUsername(enteredUsername) && isValidPassword(enteredPassword)) {
                username = enteredUsername;
                task1State = TaskState.COMPLETED;
                showLearnAboutFeaturesStep(root);
                
            } else {
                showErrorAlert("Invalid username or password. Please try again.");
            }
        });

        root.getChildren().addAll(registrationLabel, fDescription, registrationLabel1, usernameField, registrationLabel2, passwordField, registrationButton);
    }



    private void showProjectCreationStep(VBox root) {
        Label projectLabel = new Label("Task 2: Create a Project");
        Label projectLabel1 = new Label ("Enter a name for the Project");
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Enter your project name");
        Button projectButton = new Button("Create Project");

        projectButton.setOnAction(e -> {
            String enteredProjectName = projectNameField.getText();
            if (!enteredProjectName.isEmpty()) {
                projectName = enteredProjectName;

                	task2State = TaskState.COMPLETED;
                	showLearnAboutFeaturesStep(root);

            } else {
                showErrorAlert("Project name cannot be empty. Please enter a valid project name.");
            }
        });

        root.getChildren().addAll(projectLabel, projectLabel1, projectNameField, projectButton);
    }
    
    private void showStoryPointsStep(VBox root) {
        Label storyPointsLabel = new Label("Task 3: Calculate Story Points");

        Label descriptionLabel = new Label("Enter hours:");
        TextField hoursField = new TextField();
        hoursField.setPromptText("Enter hours");
        Label descriptionLabel2 = new Label("Enter minutes:");
        TextField minutesField = new TextField();
        minutesField.setPromptText("Enter minutes");

        Button storyPointsButton = new Button("Calculate");

        storyPointsButton.setOnAction(e -> {
            try {
                int hours = Integer.parseInt(hoursField.getText());
                int minutes = Integer.parseInt(minutesField.getText());

                effortLog = new EffortLog(hours, minutes);
                calculateStoryPoints(effortLog, minutes);

                task3State = TaskState.COMPLETED;
                showLearnAboutFeaturesStep(root);
                
            } catch (NumberFormatException ex) {
                showErrorAlert("Please enter valid numeric values for hours and minutes.");
            }
        });

        root.getChildren().addAll(storyPointsLabel, descriptionLabel, hoursField, descriptionLabel2, minutesField, storyPointsButton);
    }


    private void showWelcomeStep(VBox root) {
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        Label completedTasksLabel = new Label(username + ", successfully completed all the tasks and ready to get started:");

        Label task1Label = new Label("Task 1: Creating Username and Password");
        Label task2Label = new Label("Task 2: Create Project");
        Label task3Label = new Label("Task 3: Calculate story points");
        
        Label projectInfoLabel = new Label("Project name created during user orientation: " + projectName);
        String storyPointsText = (effortLog != null) ? "Story points calculated during user orientation: " + effortLog.getStoryPoints() : "";
        Label storyPointsLabel = new Label(storyPointsText);
        
        
        Label startLabel = new Label("Great Job!, " + username);
        task1Label.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

        task2Label.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

        task3Label.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

        Button startButton = new Button("Get Started");

        startButton.setOnAction(e -> {
            if (userOrientationStage != null) {
                userOrientationStage.close();
            }

            primaryStage.show();
        });

        root.getChildren().addAll(
                welcomeLabel, completedTasksLabel, task1Label, task2Label, projectInfoLabel, 
                task3Label, storyPointsLabel, startLabel, startButton);
    }

    
    private boolean isValidUsername(String username) {
        return !username.isEmpty() && !username.contains("@");
    }

    private boolean isValidPassword(String password) {
        return !password.isEmpty();
    }
    
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        //launch(args);
    }
}
