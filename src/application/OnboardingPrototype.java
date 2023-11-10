package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;

//OnboardingPrototype class written by Yashwant Gadhave

public class OnboardingPrototype extends Application {

    private int currentStep = 1;
    private String username;
    private String projectName;

    private Stage primaryStage;
    private Stage userOrientationStage;
    
    private EffortLog effortLog; // To store EffortLog
    //private int pointsPerHour;
    
    public OnboardingPrototype(Stage primaryStage) {
    	this.primaryStage = primaryStage;
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
        	showRegistrationStep(root);
        	
        } else if (currentStep == 3) {
        	showProjectCreationStep(root);
        } else if (currentStep == 4) {
        	showStoryPointsStep(root);
        	
        } else if (currentStep == 5) {    
        	showLearnAboutFeaturesStep(root);
        }
        else if (currentStep == 6) {    
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
        Label preferencesLabel = new Label("Step 1: Set Your Preferences");
        
        Label languageLabel = new Label("Select your preferred language:");
        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "Spanish", "French", "German");
        languageComboBox.setValue("English");
        
        //Label themeLabel = new Label("Choose your theme color:");
        //ColorPicker colorPicker = new ColorPicker(Color.BLUE);
        
        Button saveButton = new Button("Save Preferences");

        saveButton.setOnAction(e -> {

            String selectedLanguage = languageComboBox.getValue();
            //Color selectedColor = colorPicker.getValue();
            
            // I plan on saving these preferences to our database but for now just printing them
            System.out.println("Selected Language: " + selectedLanguage);
            //System.out.println("Selected Theme Color: " + selectedColor);
            
            currentStep = 2;
            updateUI(root);
        });

        root.getChildren().addAll(preferencesLabel, languageLabel, languageComboBox, saveButton);
    }

    private void showRegistrationStep(VBox root) {
        Label registrationLabel = new Label("Step 2: Register an Account");
        Label registrationLabel1 = new Label ("Create a username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        Label registrationLabel2 = new Label ("Create a password");
        TextField usernameField1 = new TextField();
        usernameField.setPromptText("Enter a password");
        Button registrationButton = new Button("Register");

        registrationButton.setOnAction(e -> {
            username = usernameField.getText();
            currentStep = 3;
            updateUI(root);
        });

        root.getChildren().addAll(registrationLabel, registrationLabel1, usernameField, registrationLabel2, usernameField1, registrationButton);
    }

    private void showProjectCreationStep(VBox root) {
        Label projectLabel = new Label("Step 3: Create a Project");
        Label projectLabel1 = new Label ("Enter a name for the Project");
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Enter your project name");
        Button projectButton = new Button("Create Project");

        projectButton.setOnAction(e -> {
            projectName = projectNameField.getText();
            currentStep = 4;
            updateUI(root);
        });

        root.getChildren().addAll(projectLabel, projectLabel1, projectNameField, projectButton);
    }
    
    private void showStoryPointsStep(VBox root) {
        Label storyPointsLabel = new Label("Step 4: Calculate Story Points");

        // Input fields for EffortLog
        Label descriptionLabel = new Label("Enter hours:");
        TextField hoursField = new TextField();
        hoursField.setPromptText("Enter hours");
        Label descriptionLabel2 = new Label("Enter minutes:");
        TextField minutesField = new TextField();
        minutesField.setPromptText("Enter minutes");

        Button storyPointsButton = new Button("Calculate");

        storyPointsButton.setOnAction(e -> {
            if (!hoursField.getText().isEmpty() && !minutesField.getText().isEmpty()) {
                int hours = Integer.parseInt(hoursField.getText());
                int minutes = Integer.parseInt(minutesField.getText());
                effortLog = new EffortLog(hours, minutes);
                calculateStoryPoints(effortLog, minutes);
                currentStep = 5; 
                updateUI(root);
            }
        });

        root.getChildren().addAll(storyPointsLabel,descriptionLabel, hoursField, descriptionLabel2, minutesField, storyPointsButton);
    }
    
    private void showLearnAboutFeaturesStep(VBox root) {
        Label featuresLabel = new Label("Step 5: Learn more About Features before getting started");

        Label feature1Label = new Label("Feature 1: Task Management");
        Label feature1Description = new Label("EffortLogger V2 helps you manage your tasks efficiently.");

        Label feature2Label = new Label("Feature 2: Planning Poker");
        Label feature2Description = new Label("Planning Poker is one of the essential features that has been in the new EffortLogger V2");
        Label featureDescription = new Label("and will help streamline the process of setting up and estimating story points");
        
        Label feature3Label = new Label("Feature 3: Time Tracking");
        Label feature3Description = new Label("Track the time you spend on each task.");
        

        Button selectButton = new Button("Next");

        selectButton.setOnAction(e -> {
        	updateUI(root);
        	
        });
        
        currentStep = 6;
       

        root.getChildren().addAll(featuresLabel, feature1Label, feature1Description, feature2Label, feature2Description, featureDescription, feature3Label, feature3Description, selectButton);
    }


    private void showWelcomeStep(VBox root) {
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        Label projectInfoLabel = new Label("Project created by the name: " + projectName);
       
        String storyPointsText = (effortLog != null) ? "Story Points calculated: " + effortLog.getStoryPoints() : "";
        Label storyPointsLabel = new Label(storyPointsText);
        
        Button startButton = new Button("Get Started");

        startButton.setOnAction(e -> {
            if (userOrientationStage != null) {
                userOrientationStage.close();
            }
            
            primaryStage.show();
        });


        root.getChildren().addAll(welcomeLabel, projectInfoLabel, storyPointsLabel, startButton);
    }

    public static void main(String[] args) {
        //launch(args);
    }
}
