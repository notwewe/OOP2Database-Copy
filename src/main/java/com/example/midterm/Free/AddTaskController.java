package com.example.midterm.Free;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

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

    private TableView<Task> taskTableView;

    public void setTaskTableView(TableView<Task> taskTableView) {
        this.taskTableView = taskTableView;
    }
    private Task task;

    private CRUDTodo crudTodo = new CRUDTodo();

    private ToDoListController parentController;

    public void setParentController(ToDoListController parentController) {
        this.parentController = parentController;
    }

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

        boolean inserted = crudTodo.insertTask(userId, title, description, date.toString(), time);
        if (inserted) {
            // Task inserted successfully
            System.out.println("Task inserted successfully!");

            // Reload tasks for the current user
            List<Task> userTasks = crudTodo.readTasks(userId);

            // Update the table view items directly
            taskTableView.getItems().setAll(userTasks);

            // Close the window
            closeWindow();
        } else {
            // Failed to insert task
            System.out.println("Failed to insert task");
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
