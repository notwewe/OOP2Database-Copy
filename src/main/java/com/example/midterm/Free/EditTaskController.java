package com.example.midterm.Free;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditTaskController {

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

    private Task taskToEdit;

    private CRUDTodo crudTodo = new CRUDTodo();

    public void setTask(Task task) {
        this.taskToEdit = task;
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());

        LocalDate date = LocalDate.parse(task.getDate());
        datePicker.setValue(date);
        timeField.setText(task.getTime());
    }

    @FXML
    private void handleSave() {

        String title = titleField.getText();
        String description = descriptionField.getText();
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();
        if (title.isEmpty() || description.isEmpty() || date == null || time.isEmpty()) {
            return;
        }

        taskToEdit.setTitle(title);
        taskToEdit.setDescription(description);
        taskToEdit.setDate(date.toString());
        taskToEdit.setTime(time);

        crudTodo.updateTask(taskToEdit.getId(), taskToEdit.getTitle(), taskToEdit.getDescription(), taskToEdit.getDate(), taskToEdit.getTime());

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
