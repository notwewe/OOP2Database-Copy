package com.example.midterm.Free;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.String.*;

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
        System.out.println("Logout successful!");

        alert.setOnCloseRequest(dialogEvent -> redirectToLoginPage());

        alert.showAndWait();
    }

    @FXML
    private TextField currentUsernameField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    void handleEditAccount(ActionEvent event) {
        String newUsername = currentUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please enter both new password and confirm password.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password Mismatch", "New password and confirm password do not match.");
            return;
        }

        if (updateAccount(newUsername, newPassword)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account Updated", "Your account has been successfully updated.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Update Failed", "Failed to update account. Please try again.");
        }
    }
    @FXML
    void saveChanges(ActionEvent event) {
        String newUsername = currentUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password Mismatch", "New password and confirm password do not match.");
            return;
        }
        if (updateAccount(newUsername, newPassword)) {
            System.out.println("Account update successful!");
            editAccountForm.setVisible(false);
            currentUsernameField.clear();
            newPasswordField.clear();
            confirmPasswordField.clear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account Updated", "Your account has been successfully updated.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Update Failed", "Failed to update account. Please try again.");
        }
    }

    private boolean updateAccount(String newUsername, String newPassword) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String username = CurrentUser.getCurrentUser();
            if (username == null) {
                System.err.println("No current user set.");
                return false;
            }

            String updateQuery = "UPDATE users SET username = ?, password = ? WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newUsername);
                preparedStatement.setString(2, newPassword);
                preparedStatement.setString(3, username);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    CurrentUser.setCurrentUser(newUsername);
                    return true;
                } else {
                    System.err.println("No rows affected. User not found or update failed.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    void handleDeleteAccount(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Account");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete your account? This action cannot be undone.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String username = CurrentUser.getCurrentUser();
                if (username == null) {

                    System.err.println("No current user set.");
                    return;
                }

                boolean success = deleteAccountFromDatabase(username);
                if (success) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Account Deleted");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Your account has been successfully deleted.");
                    successAlert.showAndWait();

                    handleLogout(event);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Failed to delete account. Please try again.");
                    errorAlert.showAndWait();
                }
            }
        });
    }

    private boolean deleteAccountFromDatabase(String username) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String deleteAccountQuery = "DELETE FROM users WHERE username = ?";
            try (PreparedStatement deleteAccountStatement = connection.prepareStatement(deleteAccountQuery)) {
                deleteAccountStatement.setString(1, username);
                int rowsAffected = deleteAccountStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                } else {
                    System.err.println("No rows affected. User not found or deletion failed.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    @FXML
    private VBox editAccountForm;

    @FXML
    void toggleEditAccount() {
        editAccountForm.setVisible(!editAccountForm.isVisible());
    }

    private void redirectToLoginPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent loginScene = fxmlLoader.load();
            pnLogout.getScene().setRoot(loginScene);
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
