package application;

import javafx.application.Platform;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

public class AuthenticationPane extends VBox {
    private AuthenticationManager manager;
    private Account loggedInAccount = null;
    private Button btnRegister = new Button("Register");
    private Button btnLogin = new Button("Login");
    private Button btnLogout = new Button("Logout");
    private Button btnTest = new Button("Role Info");
    private Label lblAccountInfo = new Label("");
    private Consumer<Account> visibilityUpdater;
    private Runnable logoutHandler;
    // Modified to flag when a logout is in progress to prevent recursive calls
    private boolean isLoggingOut = false;

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
        VBox layout = new VBox(5);
        layout.setAlignment(Pos.CENTER);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> privilegeComboBox = new ComboBox<>();
        privilegeComboBox.getItems().addAll("Guest","Worker","Admin");
        privilegeComboBox.setValue("Guest"); // default value
        Button btnSubmit = new Button("Register");
        layout.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, new Label("Privilege:"), privilegeComboBox, btnSubmit);
        registerStage.setScene(new Scene(layout, 300, 200));

        btnSubmit.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String privilegeText = privilegeComboBox.getValue().trim();
            int privilege;
            switch (privilegeText) {
            case "Guest":
            	privilege = 0;
            	break;
            case "Worker":
            	privilege = 1;
            	break;
            case "Admin":
            	privilege = 2;
            	break;
            default:
            	privilege = 0; // default if it goes wrong
            	System.out.println(privilegeText);
            }

            boolean registrationSuccessful = manager.registerAccount(username, password, privilege);

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
        getChildren().clear();
        getChildren().addAll(btnRegister, btnLogin);
        Main.stopAfkTimer();
        visibilityUpdater.accept(null); // Update tab visibility for logout
    }

    // This method is called externally and triggers the logout handler
    public void logout() {
        performLogout(); // Perform actual logout
    }

    public boolean isLoggedIn() {
        return loggedInAccount != null;
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
                    message = "You're a guest, so your access is limited.";
                    break;
                case 1:
                    message = "You're a worker, so you have access to most features.";
                    break;
                case 2:
                    message = "You're an admin, so you have full access.";
                    break;
                default:
                    message = "Unknown privilege level.";
            }
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Role");
        alert.setHeaderText("Information");
        alert.setContentText(message);
        alert.showAndWait();
        
        Main.getInstance().resetAfkTimer();
    }
}