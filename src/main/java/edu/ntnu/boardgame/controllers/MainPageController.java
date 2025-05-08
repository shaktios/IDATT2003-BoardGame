package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.BoardgameApp;
import edu.ntnu.boardgame.view.common.MainPage;
import edu.ntnu.boardgame.view.common.StartScreenView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageController {

    private final Stage stage;
    private final MainPage mainPage;

    public MainPageController(Stage stage) {
        this.stage = stage;

        this.mainPage = new MainPage(selectedGame -> {
            switch (selectedGame) {
                case "Stigespill" -> {
                    StartScreenView startView = new StartScreenView();
                    startView.setSelectedGameVariant("Liten Stigespill"); //første alternativet som dukker opp
                    StartScreenController controller = new StartScreenController(stage, startView);
                    Scene scene = controller.getStartScene();
                    stage.setScene(scene);
                }

                case "Tic Tac Toe" -> {
                    BoardgameApp.openTicTacToe(stage);
                }

                default -> {
                    System.err.println("Ukjent spill valgt: " + selectedGame);
                }
            }
        });

        setupActions();
    }

    private void setupActions() {
        mainPage.getExitButton().setOnAction(e -> stage.close());
    }

    public Scene getMainScene() {
        return new Scene(mainPage.getRoot(), 1280, 800); // Correct place to set size
    }

    public MainPage getMainPage() {
        return mainPage;
    }
}
