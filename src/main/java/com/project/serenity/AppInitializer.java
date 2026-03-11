package com.project.serenity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader load =  new FXMLLoader(AppInitializer.class.getResource("/view/login.fxml"));
        Scene scene = new Scene(load.load());
        stage.setScene(scene);
        stage.setTitle("Serenity Center");
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
