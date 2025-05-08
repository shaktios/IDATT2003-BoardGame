package edu.ntnu.boardgame.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application class that launches the board game system.
 */
public class ApplicationController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MainPageController controller = new MainPageController(stage);
        Scene scene = controller.getMainScene();

        stage.setTitle("BoardGame");
        stage.setScene(scene);
        stage.setMaximized(true); // Optional: full screen
        stage.show();
    }
}
