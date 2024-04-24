package com.example.midterm.Free;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddTaskController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private Button saveButton;

    private Task task;

    private CRUDTodo crudTodo = new CRUDTodo();

    @FXML
    void handleSave(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionField.getText();
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();
        if (title.isEmpty() || description.isEmpty() || date == null || time.isEmpty()) {
            return;
        }
        String currentUser = CurrentUser.getCurrentUser();
        if (currentUser == null) {
            System.out.println("No current user set.");
            return;
        }
        int userId = getUserIdByUsername(currentUser);
        if (userId == -1) {
            System.out.println("Failed to retrieve user ID.");
            return;
        }
        // Insert the task into the database with the current user's ID
        boolean inserted = crudTodo.insertTask(userId, title, description, date.toString(), time);
        if (inserted) {
            task = new Task(title, description, date.toString(), time);
            closeWindow();
        }
    }

    private int getUserIdByUsername(String username) {
        return CRUDTodo.getUserIdByUsername(username);
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public Task getTask() {
        return task;
    }
}
