package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/* Made by Troy Reiling, member of Team Tu12 in CSE360 Fall 2023
This is an authentication prototype intended to demonstrate one way of
how a proper authentication system can be built for EffortLoggerV2.

It does not use a database, nor does it save the accounts to file as the
scope does not extend that far. This prototype simply shows how accounts
can be used to check if someone has the privilege to do something.
*/

public class AuthenticationPane extends VBox {
	private AuthenticationManager manager;
    private ObservableList<Account> accounts = FXCollections.observableArrayList();
    private Account loggedInAccount = null;
    private Button btnRegister = new Button("Register");
    private Button btnLogin = new Button("Login");
    private Button btnLogout = new Button("Logout");
    private Button btnTest = new Button("Test");
    private Timer timer = new Timer(true);
    private Label lblAccountInfo = new Label("");

    public AuthenticationPane(AuthenticationManager m) {
    	manager = m;
        initializeUI();
    }

    private void initializeUI() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(btnRegister, btnLogin);

        btnRegister.setOnAction(e -> register());
        btnLogin.setOnAction(e -> login());
        btnLogout.setOnAction(e -> logout());
        btnTest.setOnAction(e -> test());
    }

    // Create a popup window to register through
    private void register() {
        Stage registerStage = new Stage();
        registerStage.initModality(Modality.APPLICATION_MODAL);
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button btnSubmit = new Button("Register");
        layout.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, btnSubmit);
        registerStage.setScene(new Scene(layout, 300, 200));

        btnSubmit.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (accounts.stream().noneMatch(account -> account.getUsername().equals(username))) {
                Account newAccount = new Account(accounts.size(), username, password, 1);
                accounts.add(newAccount);

                // Notify the user of successful registration
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Registration Successful");
                alert.setContentText("You have successfully registered.");
                alert.showAndWait();

                registerStage.close();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Registration Failed");
                alert.setContentText("Username already exists.");
                alert.showAndWait();
            }
        });

        registerStage.showAndWait();
    }

    // Create a popup window to login through
    private void login() {
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button btnSubmit = new Button("Login");
        layout.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, btnSubmit);
        loginStage.setScene(new Scene(layout, 300, 200));

        btnSubmit.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Account match = accounts.stream().filter(account -> account.getUsername().equals(username) && account.getPassword().equals(password)).findFirst().orElse(null);

            if (match != null) {
                loggedInAccount = match;
                // Remove the layout intended for logged out users
                getChildren().clear();
                // Add the layout intended for logged in users
                getChildren().addAll(lblAccountInfo, btnLogout, btnTest);
                lblAccountInfo.setText("Logged in as: " + loggedInAccount.getUsername() + " (ID: " + loggedInAccount.getId() + ")");
                // Start the AFK timer
                resetTimer();

                loginStage.close();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Incorrect username or password.");
                alert.showAndWait();
            }
        });

        loginStage.showAndWait();
    }

    // Log out of the account
    private void logout() {
        loggedInAccount = null;
        // Remove the layout intended for logged in users
        getChildren().clear();
        // Add the layout intended for logged out users
        getChildren().addAll(btnRegister, btnLogin);
        // Cancel the AFK timer
        timer.cancel();
    }

 // Test how account privilege works
    private void test() {
        String message;
        // This should never happen, but handle it just in case
        if (loggedInAccount == null) {
            message = "You must login first.";
        } else {
            switch (loggedInAccount.getPrivilege()) {
                case 0:
                    message = "You're a guest, so you're not authorized to use this test.";
                    break;
                case 1:
                    message = "You're a worker, so you have authorization to use this test. Congratulations!";
                    break;
                case 2:
                    message = "You're an admin, so you have authorization to use this test. Congratulations!";
                    break;
                default:
                    message = "Unknown privilege level.";
            }
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Test");
        alert.setHeaderText("Test Result");
        alert.setContentText(message);
        alert.showAndWait();
        // User is active, reset the AFK timer
        resetTimer();
    }

    // Timer to kick out inactive logged in users
    private void resetTimer() {
        timer.cancel();
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                	logout();
                	Alert alert = new Alert(AlertType.INFORMATION);
                	alert.setTitle("Logged Out");
                	alert.setHeaderText("Automatic Logout");
                	alert.setContentText("You have been logged out for inactivity.");
                	alert.showAndWait();
                });
            }
        }, 5 * 60 * 1000); // 5 minutes
    }
}