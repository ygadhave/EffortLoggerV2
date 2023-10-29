package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;

// Main class written by Donovan Harp and Yashwhat Gadhave

public class Main extends Application {
	
	// Database
	private Database database;
	
	// Console Tab
	private Tab consoleTab;
	private ConsolePane consolePane;
	private ConsoleManager consoleManager;
	
	// Planning Poker Tab
	private Tab planningPokerTab;
	private PlanningPokerPane planningPokerPane;
	private PlanningPokerManager planningPokerManager;
	
	// Jaylene's Prototype
	private Tab userInterfacePrototypeTab;
	private UserInterfacePrototype userInterfacePrototype;
	
	// Yashwhat's Prototype
	private Button onboardingButton;
	private OnboardingPrototype onboardingPrototype;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = new TabPane();
			
			// Initialize the database
			database = new Database();
			
			// Setup the console tab
			consoleTab = new Tab("Main");
			consoleManager = new ConsoleManager(database);
			consolePane = new ConsolePane(consoleManager);
			consoleTab.setContent(consolePane);
			
			// Setup the planning poker tab
			planningPokerTab = new Tab("Planning Poker");
			planningPokerManager = new PlanningPokerManager(database);
			planningPokerPane = new PlanningPokerPane(planningPokerManager);
			planningPokerTab.setContent(planningPokerPane);
			
			// Setup Jaylene's prototype
			userInterfacePrototypeTab = new Tab("UI Prototype");
			userInterfacePrototype = new UserInterfacePrototype();
			userInterfacePrototypeTab.setContent(userInterfacePrototype);
			
			// Add tabs
			root.getTabs().addAll(consoleTab, planningPokerTab, userInterfacePrototypeTab);
			
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
		    
		    // Show the scene
			primaryStage.setScene(mainScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
