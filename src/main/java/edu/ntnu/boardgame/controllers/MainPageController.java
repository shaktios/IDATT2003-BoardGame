package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.view.common.MainPage;
import edu.ntnu.boardgame.view.common.StartScreenView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageController {

    private final Stage stage;
    private final MainPage mainPage;

    public MainPageController(Stage stage, MainPage mainPage) {
        this.stage = stage;
        this.mainPage = mainPage;

        setupActions();
    }

    private void setupActions() {
        mainPage.getStartGameButton().setOnAction(e -> openStartScreen());
        mainPage.getExitButton().setOnAction(e -> stage.close());
    }

    private void openStartScreen() {
        StartScreenView startView = new StartScreenView();
        StartScreenController startController = new StartScreenController(stage, startView);
        Scene startScene = startController.getStartScene();
        stage.setScene(startScene);
    }

    public Scene getMainScene() {
        return new Scene(mainPage.getRoot(), 600, 500); // or your preferred size
    }

}
