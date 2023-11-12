package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

// ConsolePane class written by Jaylene Nunez

public class ConsolePane extends VBox {

	// Manager
	private ConsoleManager manager;
	
	// UI Elements
//	private Label hoursLabel;
	private TextField hoursField;
//	private Label minutesLabel;
	private TextField minutesField;
//	private Button generateLogButton;
	private Label resultText;
//	private Button clearLogsButton;
	 private Button startButton;
	 private Button stopButton;
	
	// Constructor
	public ConsolePane(ConsoleManager m) {
		// Initialize manager and UI elements
		
		VBox root = new VBox(10);
		
		HBox displayBar = new HBox(10);
//		Label statusLabel1 = new Label("Clock is Stopped");
//		Label statusLabel2 = new Label("Clocl is Running");
//      displayBar.setAlignment(javafx.geometry.Pos.CENTER);
//      displayBar.getChildren().addAll(statusLabel1);
//      statusLabel1.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
        
        Pane spacer = new Pane();
        spacer.setMinHeight(10);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox newBar = new HBox(15);
        newBar.setAlignment(javafx.geometry.Pos.CENTER);
        
        ComboBox<String> comboBox1 = new ComboBox<>();
        comboBox1.getItems().addAll("Option 1", "Option 2", "Option 3");
        comboBox1.setPromptText("Select a project");

        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll("Item A", "Item B", "Item C");
        comboBox2.setPromptText("Select Life Cycle Step");

        newBar.getChildren().addAll(comboBox1, comboBox2);
        
        Pane spaces = new Pane();
        spaces.setMinHeight(10);
        HBox.setHgrow(spaces, Priority.ALWAYS);
        
        HBox newBars = new HBox(20);
        ComboBox<String> comboBox3 = new ComboBox<>();
        newBars.setAlignment(javafx.geometry.Pos.CENTER);
        comboBox3.getItems().addAll("Select X", "Select Y", "Select Z");
        comboBox3.setPromptText("Select Effort Category");
        newBars.getChildren().addAll(comboBox3);
        
        Pane spacious = new Pane();
        spacious.setMinHeight(10);
        HBox.setHgrow(spacious, Priority.ALWAYS);
       
        //ToolBar
        HBox toolBar = new HBox(25);
        startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green;");
        stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: red;");
        toolBar.setAlignment(javafx.geometry.Pos.CENTER);
   
        Pane space = new Pane();
        space.setMinHeight(20);
        HBox.setHgrow(space, Priority.ALWAYS);
		manager = m;
		
//		hoursLabel = new Label("Set hours:");
		hoursField = new TextField();
//		minutesLabel = new Label("Set minutes:");
		minutesField = new TextField();
//		generateLogButton = new Button("Generate Effort Log");
		resultText = new Label("");
//		clearLogsButton = new Button("Clear Logs");
//		
//		//Bind Buttons
		startButton.setOnAction(new StartButtonHandler(resultText));
		stopButton.setOnAction(new StopButtonHandler(resultText));
//		clearLogsButton.setOnAction(new ClearLogsButtonHandler());
//		
//		// Add UI elements to pane
		toolBar.getChildren().addAll(startButton, stopButton);
		this.getChildren().addAll(resultText, space, toolBar);
		this.setAlignment(javafx.geometry.Pos.CENTER);
		root.getChildren().addAll( displayBar, spacer, newBar, spaces, newBars, spacious, toolBar, space);
		
		
		//this.getChildren().addAll(hoursLabel, hoursField, minutesLabel, minutesField, generateLogButton, resultText, clearLogsButton);
		 VBox layout = new VBox();
	        layout.getChildren().addAll(root);

	        this.getChildren().addAll(layout);
	}
	
	// Handles clicking the generate log button
//	private class GenerateLogButtonHandler implements EventHandler<ActionEvent> {
//		public void handle(ActionEvent event) {
//			String hoursString = hoursField.getText();
//			String minutesString = minutesField.getText();
//			
//			try {
//				//Check if either of the fields is empty.
//				if ((hoursString.compareTo("") == 0) || (minutesString.compareTo("") == 0)) {
//					throw new Exception("At least one field is empty.");
//				}
//				
//				//Parse hours and minutes.
//				int hours = Integer.parseInt(hoursString);
//				int minutes = Integer.parseInt(minutesString);
//				
//				if (manager.CreateNewEffortLog(hours, minutes)) {				
//					System.out.println("Successfully created new effort log.");
//					resultText.setText("Successfully created new effort log.");
//				}
//				else {
//					System.out.println("Effort log creation failed.");
//					resultText.setText("Effort log creation failed.");
//				}
//			}
//			catch (NumberFormatException exception) {
//				System.out.println("Hours and minutes must be integers.");
//				resultText.setText("Hours and minutes must be integers.");
//			}
//			catch (Exception exception) {
//				System.out.println(exception.getMessage());
//				resultText.setText(exception.getMessage());
//			}
//		}
//	}
	
  //Handles clicking the clear logs button
	private class StartButtonHandler implements EventHandler<ActionEvent> {
		 private Label label;

	        public StartButtonHandler(Label label) {
	            this.label = label;
	        }

	        public void handle(ActionEvent event) {
	            label.setText("Clock is running");
	            //label.setAlignment(javafx.geometry.Pos.CENTER);
	            label.setStyle("-fx-font-size: 20px; -fx-background-color: green;");
	            
	            // You can add additional logic or modify the label as needed.
	        }
	}	
	
	private class StopButtonHandler implements EventHandler<ActionEvent> {
		private Label label1; 
		public StopButtonHandler(Label label) {
	            this.label1 = label;
	        }

	        public void handle(ActionEvent event) {
	            label1.setText("Clock is stopped");
	            //label1.setAlignment(javafx.geometry.Pos.CENTER);
	            label1.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
	            // You can add additional logic or modify the label as needed.
	        }
    }
}
