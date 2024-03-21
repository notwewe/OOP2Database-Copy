package com.example.midterm;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 433, 384);


        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Hello!");

        stage.centerOnScreen();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
