package com.example.midterm.Free;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    public void start(Stage stage) throws IOException {

        CreateTable();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 433, 384);

        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("Hello!");

        stage.centerOnScreen();

        stage.show();
    }

    private void CreateTable() {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {
            String usersQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(100) NOT NULL," +
                    "password VARCHAR(100) NOT NULL)";
            statement.execute(usersQuery);

            String todolistQuery = "CREATE TABLE IF NOT EXISTS todolist (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "userid INT," +
                    "title VARCHAR(100) NOT NULL," +
                    "description VARCHAR(255)," +
                    "date DATE," +
                    "time TIME," +
                    "done BOOLEAN DEFAULT FALSE," +
                    "FOREIGN KEY (userid) REFERENCES users(id))";
            statement.execute(todolistQuery);


            System.out.println("Tables created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
