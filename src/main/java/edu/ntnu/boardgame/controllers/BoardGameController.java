package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.gui.StartScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class BoardGameController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        StartScreen startScreen = new StartScreen(); 
        primaryStage.setScene(startScreen.getScene(primaryStage)); 
        primaryStage.setTitle("BoardGame"); 
        primaryStage.show(); 
    }
}
