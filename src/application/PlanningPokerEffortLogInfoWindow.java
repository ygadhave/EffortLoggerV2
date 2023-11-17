package application;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

//PlanningPokerEffortLogInfoWindow class written by Donovan Harp

public class PlanningPokerEffortLogInfoWindow extends GridPane {

	// References
	private PlanningPokerPane pane;
	private PlanningPokerManager manager;
	
	// Project Info
	private Definitions definitions;
	private String cycleName;
    private String effortCategory;
    private String deliverable;
	
	// UI Elements
	private Label cycleTitle;
	private Label categoryTitle;
	private Label deliverableTitle;
	private Label cycleLabel;
	private Label categoryLabel;
	private Label deliverableLabel;
	
	public PlanningPokerEffortLogInfoWindow(PlanningPokerPane p, PlanningPokerManager m, EffortLog log) {
		// Get References
		pane = p;
		manager = m;
		
		// Get definitions
		definitions = log.getDefinitions();
		
		// Get information
		cycleName = definitions.getCycleName();
		effortCategory = definitions.getEffortCategory();
		deliverable = definitions.getDeliverable();
		
		// Initialize UI Elements
		cycleTitle = new Label("Cycle:");
		categoryTitle = new Label("Effort Category:");
		deliverableTitle = new Label("Deliverable:");
		cycleLabel = new Label(cycleName);
		categoryLabel = new Label(effortCategory);
		deliverableLabel = new Label(deliverable);
		
		// Add UI Elements to GridPane
		this.add(cycleTitle, 0, 0);
		this.add(categoryTitle, 0, 1);
		this.add(deliverableTitle, 0, 2);
		this.add(cycleLabel, 1, 0);
		this.add(categoryLabel, 1, 1);
		this.add(deliverableLabel, 1, 2);
		this.setHgap(30);
		this.setVgap(10);
	}
}
