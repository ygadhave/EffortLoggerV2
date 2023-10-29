package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;


public class Main extends Application {
	
	private Database database;
	
	private Tab consoleTab;
	private ConsolePane consolePane;
	private ConsoleManager consoleManager;
	
	private Tab planningPokerTab;
	private PlanningPokerPane planningPokerPane;
	private PlanningPokerManager planningPokerManager;
	
	private Tab jaylenePrototypeTab;
	private JaylenePrototype jaylenePrototype;
	
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
			jaylenePrototypeTab = new Tab("Jaylene Prototype");
			jaylenePrototype = new JaylenePrototype();
			jaylenePrototypeTab.setContent(jaylenePrototype);
			
			// Add tabs
			root.getTabs().addAll(consoleTab, planningPokerTab, jaylenePrototypeTab);
			
			// Setup the main scene
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// Setup the user orientation button
		    Button userOrientationButton = new Button("User Orientation");
		    userOrientationButton.setOnAction(e -> {
		        OnboardingPrototype userOrientationPrototype = new OnboardingPrototype(primaryStage);
		        Stage userOrientationStage = new Stage();
		        userOrientationPrototype.start(userOrientationStage);
		    });

		    // Add the user orientation button
		    // Extra comment
		    VBox mainContainer = new VBox(root, userOrientationButton);
		    Scene mainScene = new Scene(mainContainer, 800, 600);
			
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
