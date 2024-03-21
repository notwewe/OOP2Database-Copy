package com.example.midterm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane pnLogin, pnLogout;

    @FXML
    private TextField UsernameField;

    @FXML
    private TextField PasswordField;
    private final String filePath = "user_credentials.txt";


    private Map<String, String> users = new HashMap<>();

    public HelloController() {
        loadUserCredentials();
    }

    private void loadUserCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please enter both username and password.");
            return;
        }

        if (users.containsKey(username) && users.get(username).equals(password)) {
            loadHomePage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Credentials", "Incorrect username or password.");
        }
    }

    protected void loadHomePage() throws IOException {
        Parent scene = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("home-view.fxml")));
        pnLogin.getChildren().clear();
        pnLogin.getChildren().add(scene);

        UsernameField.clear();
        PasswordField.clear();
    }

    protected void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));

        pnLogout.getChildren().clear();
        pnLogout.getChildren().add(scene);
    }

    @FXML
    protected void onRegisterButtonClick() throws IOException {
        // Redirect to the welcome view (FXML) when the register button is clicked
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        UsernameField.getScene().setRoot(welcomeScene);
    }
}
