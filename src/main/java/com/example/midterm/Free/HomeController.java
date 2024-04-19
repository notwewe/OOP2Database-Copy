package com.example.midterm.Free;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HomeController {

    @FXML
    private ToggleButton darkModeToggle;

    @FXML
    private AnchorPane pnLogout;

    @FXML
    private Button Logoutbtn;

    @FXML
    void initialize() {
    }

    @FXML
    void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have been logged out successfully!");

        alert.setOnCloseRequest(dialogEvent -> {
            try {
                Parent loginScene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                pnLogout.getChildren().clear();
                pnLogout.getChildren().add(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        alert.showAndWait();
    }

    @FXML
    void toggleDarkMode(ActionEvent event) {
        if (darkModeToggle.isSelected()) {
            String css = getClass().getResource("homedarkmode.css").toExternalForm();
            pnLogout.getScene().getStylesheets().add(css);
        } else {
            String css = getClass().getResource("homedarkmode.css").toExternalForm();
            pnLogout.getScene().getStylesheets().remove(css);
        }
    }

}
