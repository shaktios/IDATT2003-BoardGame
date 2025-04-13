package edu.ntnu.boardgame;

import edu.ntnu.boardgame.gui.StartScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class BoardgameApp extends Application {

    public static void main(String[] args) {
        launch(args); // starter JavaFX
    }

    @Override
    public void start(Stage primaryStage) {
        // opprett StartScreen og vis den
        StartScreen startScreen = new StartScreen();
        primaryStage.setScene(startScreen.getScene(primaryStage));
        primaryStage.setTitle("Boardgame");
        primaryStage.show();
    }
}
