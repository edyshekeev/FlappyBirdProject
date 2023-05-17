package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ProjectApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectApp.class.getResource("ProjectApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 558, 638);
        scene.getRoot().requestFocus();
        stage.setTitle("Project App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}