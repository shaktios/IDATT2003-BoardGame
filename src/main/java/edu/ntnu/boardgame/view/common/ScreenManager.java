package edu.ntnu.boardgame.view.common;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {

    private static Stage mainStage;

    // Setter scenen som skal brukes
    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    // Bytter skjerm
    public static void setScreen(Scene scene) {
        if (mainStage != null) {
            mainStage.setScene(scene);
            mainStage.show();
        } else {
            throw new IllegalStateException("Stage er ikke satt i ScreenManager!");
        }
    }

    public static Stage getStage(){
        return mainStage;
    }
}
