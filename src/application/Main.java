package application;
	
import java.util.Timer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Timer;
import java.util.TimerTask;

// Main class written by Donovan Harp, Yashwhat Gadhave, and Troy Reiling

public class Main extends Application {
	
	// Database
	private Database database;
	
	// Effort Console Tab
	private Tab consoleTab;
	private ConsolePane consolePane;
	private ConsoleManager consoleManager;
	
	// Effort Log Editor Tab
	private Tab editorTab;
	private EditorPane editorPane;
	private EditorManager editorManager;
	
	// Defect Log Tab
	private Tab defectTab;
	private DefectPane defectPane;
	private DefectManager defectManager;
	
	// Effort Log Viewer Tab
	private Tab logsTab;
	private LogsPane logsPane;
	private LogsManager logsManager;
	
	// Definitions Tab
	private Tab definitionsTab;
	private DefinitionsPane definitionsPane;
	private DefinitionsManager definitionsManager;
	
	// Planning Poker Tab
	private Tab planningPokerTab;
	private PlanningPokerPane planningPokerPane;
	private PlanningPokerManager planningPokerManager;
	
	// User Interface Tab
	private Tab userInterfacePrototypeTab;
	private UserInterfacePrototype userInterfacePrototype;
	
	// Onboarding Tab
	private Button onboardingButton;
	private OnboardingPrototype onboardingPrototype;
	
	// ----------Troy's Code----------
	// Authentication Tab
	private Tab authenticationTab;
	private AuthenticationPane authenticationPane;
	private AuthenticationManager authenticationManager;
	
    // Declare root as a class-level field
    private TabPane root;
	
    // AFK Timer for the entire application
    private static Timer afkTimer;
	// -------------------------------
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new TabPane();
			
			// Initialize the database
			database = new Database();
			
			// NOTE: There will always be exactly 10 projects created, and these can simply
			//       be modified to fit the user's ends. These 10 will be created when the user
			//       first boots up the program and all be empty.
			
			// Create a single test project
			database.addProject(new Project("Default Project"));
			
			// Setup the console tab
			consoleTab = new Tab("Effort Console");
			consoleManager = new ConsoleManager(database);
			consolePane = new ConsolePane(consoleManager);
			consoleTab.setContent(consolePane);
			
			// Setup the editor tab
			editorTab = new Tab("Effort Log Editor");
			editorManager = new EditorManager(database);
			editorPane = new EditorPane(editorManager);
			editorTab.setContent(editorPane);
			
			// Setup the defect tab
			defectTab = new Tab("Defect Console");
			defectManager = new DefectManager(database);
			defectPane = new DefectPane(defectManager);
			defectTab.setContent(defectPane);
			
			// Setup the logs tab
			logsTab = new Tab("Logs");
			logsManager = new LogsManager(database);
			logsPane = new LogsPane(logsManager);
			logsTab.setContent(logsPane);
			
			// Setup the definitions tab
			definitionsTab = new Tab("Definitions");
			definitionsManager = new DefinitionsManager(database);
			definitionsPane = new DefinitionsPane(definitionsManager);
			definitionsTab.setContent(definitionsPane);
			
			// Setup the planning poker tab
			planningPokerTab = new Tab("Planning Poker");
			planningPokerManager = new PlanningPokerManager(database);
			planningPokerPane = new PlanningPokerPane(planningPokerManager);
			planningPokerTab.setContent(planningPokerPane);
			
			// Setup Jaylene's prototype
			userInterfacePrototypeTab = new Tab("UI Prototype");
			userInterfacePrototype = new UserInterfacePrototype();
			userInterfacePrototypeTab.setContent(userInterfacePrototype);
			
            // Setup authentication tab
            authenticationTab = new Tab("Authentication");
            authenticationManager = new AuthenticationManager(database);
            authenticationPane = new AuthenticationPane(authenticationManager, this::updateTabVisibility);
            authenticationTab.setContent(authenticationPane);
            
            authenticationPane.setLogoutHandler(() -> {
                logoutUser();
                showLogoutAlert();
            });

            // Add tabs
            root.getTabs().addAll(authenticationTab); // Initially, only the authentication tab is visible
			
			// Setup the main scene
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// ----------Yashwhat's Code----------
			// Setup the user orientation button
		    onboardingButton = new Button("User Orientation");
		    onboardingButton.setOnAction(e -> {
		        onboardingPrototype = new OnboardingPrototype(primaryStage);
		        Stage userOrientationStage = new Stage();
		        onboardingPrototype.start(userOrientationStage);
		    });

		    // Add the user orientation button
		    VBox mainContainer = new VBox(root, onboardingButton);
		    Scene mainScene = new Scene(mainContainer, 800, 600);
		    // ----------------------------------
		    
            // Setup the AFK Timer
            setupAfkTimer();

            // Show the scene
            primaryStage.setScene(mainScene);
            primaryStage.show();
           
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	
    private void setupAfkTimer() {
        if (afkTimer != null) {
            afkTimer.cancel();
        }
        afkTimer = new Timer(true);
        afkTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    logoutUser();
                    showLogoutAlert();
                });
            }
        }, 5 * 60 * 1000); // 5 minutes
    }

    private void logoutUser() {
        // Logic to handle user logout
        authenticationPane.logout();
        // Reset the timer to null or cancel it
        if (afkTimer != null) {
            afkTimer.cancel();
        }
    }

    private void showLogoutAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Logged Out");
        alert.setHeaderText("Automatic Logout");
        alert.setContentText("You have been logged out due to inactivity.");
        alert.showAndWait();
    }

    public void resetAfkTimer() {
        setupAfkTimer();
    }
    
    public static void stopAfkTimer() {
       afkTimer.cancel();
    }

    // Method to update tab visibility based on user status
    public void updateTabVisibility(Account account) {
        root.getTabs().clear();
        root.getTabs().add(authenticationTab);

        if (account != null) {
            int privilege = account.getPrivilege();
            if (privilege == 0) {
                root.getTabs().add(planningPokerTab);
            } else if (privilege == 1 || privilege == 2) {
                root.getTabs().addAll(consoleTab, editorTab, defectTab, logsTab, definitionsTab, planningPokerTab, userInterfacePrototypeTab);
            }
        }

        resetAfkTimer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
