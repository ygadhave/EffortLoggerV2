package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class PlanningPokerPane extends VBox {
	private PlanningPokerManager manager;
	
	private Label storyPointsLabel;
	private TextField storyPointsField;
	private Button refreshButton;
	private VBox effortLogListArea;
	private Button generateEstimateButton;
	private Label estimateLabel;
	
	public PlanningPokerPane(PlanningPokerManager m) {
		manager = m;
		
		storyPointsLabel = new Label("Story Points Per Hour:");
		storyPointsField = new TextField("60");
		
		refreshButton = new Button("Refresh Effort Log List");
		effortLogListArea = new VBox();
		generateEstimateButton = new Button("Generate Estimate");
		estimateLabel = new Label("Estimate Not Yet Generated");
		
		refreshButton.setOnAction(new RefreshButtonHandler());
		generateEstimateButton.setOnAction(new GenerateEstimateButtonHandler());
		
		this.getChildren().addAll(storyPointsLabel, storyPointsField);
		this.getChildren().addAll(refreshButton, effortLogListArea);
		this.getChildren().addAll(generateEstimateButton, estimateLabel);
	}
	
	public void UpdateListArea(ArrayList<EffortLog> newLogList) {
		effortLogListArea.getChildren().clear();
		
		if (newLogList.size() < 1) {
			return;
		}
		
		for (int i = 0; i < newLogList.size(); i++) {
			HBox effortLogDisplay = new HBox();
			
			int hours = newLogList.get(i).GetHours();
			int minutes = newLogList.get(i).GetMinutes();
			double weight = newLogList.get(i).GetWeight();
			int bias = newLogList.get(i).GetBias();
			
			Label hoursTitle = new Label("Hours: ");
			Label hoursNumber = new Label("" + hours);
			Label minutesTitle = new Label("Minutes: ");
			Label minutesNumber = new Label("" + minutes);
			TextField weightField = new TextField("" + weight);
			TextField biasField = new TextField("" + bias);
			
			effortLogDisplay.getChildren().addAll(hoursTitle, hoursNumber, minutesTitle, minutesNumber, weightField, biasField);
			
			effortLogListArea.getChildren().add(effortLogDisplay);		
		}
	}
	
	private class RefreshButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				
				ArrayList<EffortLog> newLogList = manager.GetEffortLogs();
				UpdateListArea(newLogList);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
	
	private class GenerateEstimateButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				ArrayList<EffortLog> logsToCalculate = new ArrayList<EffortLog>();
				
				int pointsPerHour = Integer.parseInt(storyPointsField.getText());
				
				for (int i = 0; i < effortLogListArea.getChildren().size(); i++) {
					HBox listElement = (HBox)effortLogListArea.getChildren().get(i);
					
					Label hoursLabel = (Label)listElement.getChildren().get(1);
					Label minutesLabel = (Label)listElement.getChildren().get(3);
					TextField weightField = (TextField)listElement.getChildren().get(4);
					TextField biasField = (TextField)listElement.getChildren().get(5);
					
					int hours = Integer.parseInt(hoursLabel.getText());
					int minutes = Integer.parseInt(minutesLabel.getText());
					double weight = Double.parseDouble(weightField.getText());
					int bias = Integer.parseInt(biasField.getText());
					
					EffortLog log = new EffortLog(hours, minutes, weight, bias);
					logsToCalculate.add(log);
				}
				
				int estimate = manager.CalculateAverage(logsToCalculate, pointsPerHour);
				estimateLabel.setText("Estimate: " + estimate);
			}
			catch (NumberFormatException exception) {
				System.out.println("Error: One of the weights or biases is in the incorrect format.");
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
}