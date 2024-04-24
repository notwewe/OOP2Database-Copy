package com.example.midterm.Free;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ToDoListController {

    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private TableColumn<Task, String> dateColumn;

    @FXML
    private TableColumn<Task, String> timeColumn;

    @FXML
    private Button addTaskBtn;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    private int getUserIdByUsername(String username) {

        return CRUDTodo.getUserIdByUsername(username);
    }

    @FXML
    void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        loadUserTasks();
    }

    @FXML
    void handleAddTask() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addTask.fxml"));
            Parent root = fxmlLoader.load();
            AddTaskController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            Task newTask = controller.getTask();
            if (newTask != null) {
                tasks.add(newTask);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserTasks() {

        CRUDTodo crudTodo = new CRUDTodo();
        tasks.clear();
        tasks.addAll(crudTodo.readTasks(getUserIdByUsername(CurrentUser.getCurrentUser())));
        taskTableView.setItems(tasks);
    }

    @FXML
    void handleEditTask() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edittask.fxml"));
                Parent root = fxmlLoader.load();
                EditTaskController controller = fxmlLoader.getController();
                controller.setTask(selectedTask);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Task");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                loadUserTasks();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Task Selected", "Please select a task to edit.");
        }
    }

    @FXML
    void handleDeleteTask() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Task");
            alert.setContentText("Are you sure you want to delete this task?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                boolean deleted = CRUDTodo.deleteTask(selectedTask.getId());
                if (deleted) {

                    showAlert("Task Deleted", "The selected task has been deleted.");
                    loadUserTasks();
                } else {
                    showAlert("Deletion Failed", "Failed to delete the selected task.");
                }
            }
        } else {
            showAlert("No Task Selected", "Please select a task to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}