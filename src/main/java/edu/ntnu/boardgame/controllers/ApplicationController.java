package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.view.common.StartScreenView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main application class that launches the board game system.
 */
public class ApplicationController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StartScreenView startScreenView = new StartScreenView();
        StartScreenController startScreenController = new StartScreenController(primaryStage, startScreenView);

        primaryStage.setScene(startScreenController.getStartScene());
        primaryStage.setTitle("BoardGame");
        primaryStage.show();
    }
}
