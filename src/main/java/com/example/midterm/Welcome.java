package com.example.midterm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Welcome {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;


    private final String filePath = "user_credentials.txt";

    // Map to store usernames and passwords
    private Map<String, String> users = new HashMap<>();

    public Welcome() {
        loadUserCredentials();
    }

    @FXML
    void redirectToLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent loginPage = fxmlLoader.load();

        Scene scene = new Scene(loginPage);

        Stage stage = (Stage) usernameField.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void register(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please enter both username and password.");
            return;
        }

        if (users.containsKey(username)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username Exists", "Username already exists. Please choose another one.");
            return;
        }

        users.put(username, password);
        saveUserCredentials();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Successful", "You have successfully registered.");
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

    private void saveUserCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
