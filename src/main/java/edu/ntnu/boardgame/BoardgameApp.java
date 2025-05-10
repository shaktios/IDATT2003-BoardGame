package edu.ntnu.boardgame;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.controllers.MainPageController;
import edu.ntnu.boardgame.controllers.TicTacToeController;
import edu.ntnu.boardgame.view.common.MainPage;
import edu.ntnu.boardgame.view.tictactoegame.TicTacToeGameScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the Boardgame application.
 */
public class BoardgameApp extends Application {

    /**
     * Launches the application by displaying the start screen.
     *
     * @param primaryStage the primary JavaFX window
     */
    @Override
    public void start(Stage primaryStage) {
        MainPageController controller = new MainPageController(primaryStage);
        Scene scene = controller.getMainScene();

        primaryStage.setTitle("BoardGame");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Optional: full screen
        primaryStage.show();
    }

    /**
     * Creates a new, fully wired-up start screen with view and controller.
     *
     * @param stage the current stage to bind the scene to
     * @return a fresh start screen scene
     */
    public static Scene createFreshStartScene(Stage stage) {
        MainPage mainPage = new MainPage(gameName -> {
            switch (gameName) {
                case "Liten Stigespill" -> openMiniGame();
                case "Stort Stigespill" -> openClassicGame();
                case "Tic Tac Toe" -> openTicTacToe(stage);
            }
        });

        Scene scene = new Scene(mainPage.getRoot(), 1280, 720);
        stage.setScene(scene);
        stage.setFullScreen(true); // Optional for fullscreen
        return null;
    }

        public static void main(String[] args) {
        launch(args);
    }

    public static void openTicTacToe(Stage stage) {
        Tile dummyTile = new Tile(1); // must be positive
        Player player1 = new Player("Spiller 1", dummyTile,20); //lagt til noen randomme aldere fordi tictactoe ikke har noe med å gjøre med alderen til spilleren
        Player player2 = new Player("Spiller 2", dummyTile,21); //lagt til noen randomme aldere fordi tictactoe ikke har noe med å gjøre med alderen til spilleren

        TicTacToeGameScreen view = new TicTacToeGameScreen();
        TicTacToeController controller = new TicTacToeController(player1, player2, view);
        view.setController(controller); // if your view has setController()

        Scene scene = new Scene(view.getRoot(), 600, 700);
        stage.setScene(scene);
        stage.setTitle("Tic Tac Toe");
    }


    public static void openClassicGame() {
        BoardGameFactory.createClassicGame();
    }

    public static void openMiniGame() {
        BoardGameFactory.createMiniGame();
    }

}
