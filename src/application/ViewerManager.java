package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewerManager {
	Database database;
	
	public ViewerManager(Database d) {
		database = d;
	}
	
	public TableView<EffortLog> setupEffortLogsTable(Project project) {
		// Get the list of effort logs from the database as an observable list
		ObservableList<EffortLog> effortLogsList = FXCollections.observableArrayList(getEffortLogs(project));
		
		// Initialize the TableView
		TableView<EffortLog> effortLogsTable = new TableView<>();
		
		// Setup the hours column
        TableColumn<EffortLog, Integer> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("effortHours"));

        // Setup the minutes column
        TableColumn<EffortLog, Integer> minutesColumn = new TableColumn<>("Minutes");
        minutesColumn.setCellValueFactory(new PropertyValueFactory<>("effortMinutes"));

        // Add the columns and fill the table
        effortLogsTable.getColumns().addAll(hoursColumn, minutesColumn);
        effortLogsTable.setItems(effortLogsList);
        
        return effortLogsTable;
    }
	
	public TableView<DefectLog> setupDefectLogsTable(Project project) {
		// Get the list of effort logs from the database as an observable list
		ObservableList<DefectLog> defectLogsList = FXCollections.observableArrayList(getDefectLogs(project));
		
		// Initialize the TableView
		TableView<DefectLog> defectLogsTable = new TableView<>();
		
		// Setup the hours column
        TableColumn<DefectLog, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("defectName"));

        // Add the columns and fill the table
        defectLogsTable.getColumns().addAll(nameColumn);
        defectLogsTable.setItems(defectLogsList);
        
        return defectLogsTable;
    }
	
	public ArrayList<Project> getProjects() {
		return database.getProjects();
	}
	
	public Project getProject(int index) {
		return database.getProject(index);
	}
	
	public ArrayList<EffortLog> getEffortLogs(Project project) {
		return project.getEffortLogs();
	}
	
	public ArrayList<DefectLog> getDefectLogs(Project project) {
		return project.getDefectLogs();
	}
}
