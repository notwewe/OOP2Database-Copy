package com.example.midterm.Free;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private final CRUD crud = new CRUD();

    @FXML
    protected void onHelloButtonClick() throws IOException {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please enter both username and password.");
            return;
        }

        if (crud.readData(username, password)) {
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
