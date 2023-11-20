package application;

import javafx.application.Platform;
import java.util.function.Consumer;
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

public class AuthenticationPane extends VBox {
    private AuthenticationManager manager;
    private Account loggedInAccount = null;
    private Button btnRegister = new Button("Register");
    private Button btnLogin = new Button("Login");
    private Button btnLogout = new Button("Logout");
    private Button btnTest = new Button("Test");
    private Label lblAccountInfo = new Label("");
    private Consumer<Account> visibilityUpdater;
    private Runnable logoutHandler;

    public AuthenticationPane(AuthenticationManager manager, Consumer<Account> visibilityUpdater) {
        this.manager = manager;
        this.visibilityUpdater = visibilityUpdater;
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

            boolean registrationSuccessful = manager.registerAccount(username, password);

            if (registrationSuccessful) {
                // Notify the user of successful registration
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Registration Successful");
                alert.setContentText("You have successfully registered.");
                alert.showAndWait();

                registerStage.close();
            } else {
                // Notify the user of registration failure
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Registration Failed");
                alert.setContentText("Username already exists or registration failed.");
                alert.showAndWait();
            }
        });

        registerStage.showAndWait();
    }


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

            manager.login(username, password).ifPresentOrElse(account -> {
                loggedInAccount = account;
                getChildren().clear();
                getChildren().addAll(lblAccountInfo, btnLogout, btnTest);
                lblAccountInfo.setText("Logged in as: " + loggedInAccount.getUsername() + " (ID: " + loggedInAccount.getId() + ")");
                visibilityUpdater.accept(loggedInAccount); // Update tab visibility
                loginStage.close();
            }, () -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Incorrect username or password.");
                alert.showAndWait();
            });
        });

        loginStage.showAndWait();
    }

    public void setLogoutHandler(Runnable logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    // This method only performs the logout logic without triggering the handler
    public void performLogout() {
        loggedInAccount = null;
        // Remove the layout intended for logged in users
        getChildren().clear();
        // Add the layout intended for logged out users
        getChildren().addAll(btnRegister, btnLogin);
        // Cancel the AFK timer
        Main.stopAfkTimer();
        visibilityUpdater.accept(null); // Update tab visibility for logout
    }

    // This method is called externally and triggers the logout handler
    public void logout() {
        performLogout(); // Perform actual logout
        if (logoutHandler != null) {
            logoutHandler.run(); // Trigger additional actions
        }
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
    }
}