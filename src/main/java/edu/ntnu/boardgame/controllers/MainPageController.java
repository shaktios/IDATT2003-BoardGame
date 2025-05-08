package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.BoardgameApp;
import edu.ntnu.boardgame.BoardGameFactory;
import edu.ntnu.boardgame.view.common.MainPage;
import edu.ntnu.boardgame.view.laddergame.LadderGameScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.controllers.LadderGameController;

public class MainPageController {

    private final Stage stage;
    private final MainPage mainPage;

    public MainPageController(Stage stage) {
        this.stage = stage;

        // Build main page and connect game selection
        this.mainPage = new MainPage(selectedGame -> {
            switch (selectedGame) {
                case "Liten Stigespill" -> {
                    Boardgame game = BoardGameFactory.createMiniGame();
                    LadderGameScreen view = new LadderGameScreen();
                    LadderGameController controller = new LadderGameController(game, view);
                    Scene scene = view.createScene(stage, game, game.getBoard(), game.getPlayers());
                    stage.setScene(scene);
                }

                case "Stort Stigespill" -> {
                    Boardgame game = BoardGameFactory.createClassicGame();
                    LadderGameScreen view = new LadderGameScreen();
                    LadderGameController controller = new LadderGameController(game, view);
                    Scene scene = view.createScene(stage, game, game.getBoard(), game.getPlayers());
                    stage.setScene(scene);
                }

                case "Tic Tac Toe" -> {
                    BoardgameApp.openTicTacToe(stage); // You must implement this method
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
}
