package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// UserInterfacePrototype class writen by Jaylene Nunez

public class UserInterfacePrototype extends VBox {

    public UserInterfacePrototype() {

        VBox root = new VBox(10);

        // Status bar (Placeholder labels)
        VBox statusBar = new VBox(5);
        Label statusLabelRunning = new Label("Stopped");
        Label statusLabelTime = new Label("Next: 08:27");
        ProgressBar progressBar = new ProgressBar(0.8);
        statusBar.getChildren().addAll(statusLabelRunning, statusLabelTime, progressBar);

        HBox displayBar = new HBox(10);
        Label statusLabel1 = new Label("Clock is Stopped");
        displayBar.setAlignment(javafx.geometry.Pos.CENTER);
        displayBar.getChildren().addAll(statusLabel1);
        statusLabel1.setStyle("-fx-font-size: 20px; -fx-background-color: red;");
        
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
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green;");
        Button stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: red;");
        toolBar.setAlignment(javafx.geometry.Pos.CENTER);
   
        Pane space = new Pane();
        space.setMinHeight(20);
        HBox.setHgrow(space, Priority.ALWAYS);
        
        HBox buttonBar = new HBox(30);
        buttonBar.setAlignment(javafx.geometry.Pos.CENTER);
        Button logButton = new Button("EffortLog Editor");
        Button editButton = new Button("DefectLog Console");
        Button createButton = new Button("Definitions"); 
        Button newButton = new Button("Effor and Defect Logs"); 
        toolBar.getChildren().addAll(startButton, stopButton); 
        buttonBar.getChildren().addAll(logButton, editButton, createButton, newButton);

        root.getChildren().addAll(statusBar, displayBar, spacer, newBar, spaces, newBars, spacious, toolBar, space, buttonBar);
  
        statusLabelRunning.setLayoutX(10);
        statusLabelRunning.setLayoutY(10);

        statusLabelTime.setLayoutX(10);
        statusLabelTime.setLayoutY(40);

        progressBar.setLayoutX(10);
        progressBar.setLayoutY(70);
        
        // MenuBar
        Menu fileMenu = new Menu("File");
        MenuItem logMenuItem = new MenuItem("Read Current Log");
        MenuItem newMenuItem = new MenuItem("Edit Current Effort");
        MenuItem createMenuItem = new MenuItem("Create New Effortlog");
        fileMenu.getItems().addAll(logMenuItem, newMenuItem, createMenuItem);

        Menu helpMenu = new Menu("Help");
        MenuItem helpMenuItem = new MenuItem("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        helpMenu.getItems().addAll(helpMenuItem, aboutMenuItem);
        
        Menu effortConsole = new Menu("Effort Console");
        MenuItem effortConsoleItem = new MenuItem("Info");
        MenuItem neweffortConsole = new MenuItem("Effort Console");
        effortConsole.getItems().addAll(effortConsoleItem, neweffortConsole);
        
        Menu effortEdit = new Menu("Effort Editor");
        MenuItem effortEditItem = new MenuItem("Info");
        MenuItem effortEditConsole = new MenuItem("Effort Editor");
        effortEdit.getItems().addAll(effortEditItem, effortEditConsole);
        
        Menu defectConsole = new Menu("Defect Console");
        MenuItem defectConsoleItem = new MenuItem("Info");
        MenuItem defectConsoleConsole = new MenuItem("Defect Console");
        defectConsole.getItems().addAll(defectConsoleItem, defectConsoleConsole);
        
        Menu logViewer = new Menu("Log Viewer");
        MenuItem logViewerItem = new MenuItem("Info");
        MenuItem logViewerConsole = new MenuItem("Log Viewer");
        logViewer.getItems().addAll(logViewerItem, logViewerConsole);
        
        Menu definitions = new Menu("Definitions");
        MenuItem definitonsItem = new MenuItem("Info");
        MenuItem definitonsConsole = new MenuItem("definitons");
        definitions.getItems().addAll(definitonsItem, definitonsConsole);
        
        Menu planningPoker = new Menu("Planning Poker");
        MenuItem planningPokerItem = new MenuItem("Info");
        MenuItem planningPokerConsole = new MenuItem("Planning Poker");
        planningPoker.getItems().addAll(planningPokerItem, planningPokerConsole);


        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu, effortConsole, effortEdit, defectConsole, logViewer, definitions, planningPoker);

        // Setting up the Scene
        VBox layout = new VBox();
        layout.getChildren().addAll(menuBar, root);

        this.getChildren().addAll(layout);
    }
}