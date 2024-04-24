package com.example.midterm.Free;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDoList {

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
    private TableColumn<Task, Boolean> doneColumn;

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
        doneColumn.setCellValueFactory(new PropertyValueFactory<>("done"));

        loadTasksFromDatabase();
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

    private void loadTasksFromDatabase() {

        String currentUsername = CurrentUser.getCurrentUser();
        if (currentUsername == null) {
            System.out.println("No current user set.");
            return;
        }

        int userId = getUserIdByUsername(currentUsername);
        if (userId == -1) {
            System.out.println("Failed to retrieve user ID.");
            return;
        }

        CRUDTodo crudTodo = new CRUDTodo();
        tasks.clear();
        tasks.addAll(crudTodo.readTasks(userId));
    }


}

