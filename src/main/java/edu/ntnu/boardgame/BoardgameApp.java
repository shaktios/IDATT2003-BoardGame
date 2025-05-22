package edu.ntnu.boardgame;

import edu.ntnu.boardgame.constructors.BoardGameFactory;
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
 * Entry point for the board game application. Responsible for initializing and
 * displaying the main menu and launching individual games.
 */
public class BoardgameApp extends Application {

    /**
     * Starts the JavaFX application by showing the main page.
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
     * Creates a new main menu scene with game options.
     *
     * @param stage the stage to attach the new scene to
     * @return the main menu scene
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
        stage.setResizable(false); 
        stage.setTitle("BoardGame");
        stage.sizeToScene(); 
        return scene;
    }

    /**
     * Launches the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Initializes and displays the Tic Tac Toe game screen.
     *
     * @param stage the current JavaFX stage to load the game into
     */
    public static void openTicTacToe(Stage stage) {
        Tile dummyTile = new Tile(1); // must be positive
        Player player1 = new Player("Spiller 1", dummyTile, 20);
        Player player2 = new Player("Spiller 2", dummyTile, 21);

        TicTacToeGameScreen view = new TicTacToeGameScreen(stage);
        TicTacToeController controller = new TicTacToeController(player1, player2, view);
        view.setController(controller);

        Scene scene = new Scene(view.getRoot(), 600, 700);
        scene.getStylesheets().add(BoardgameApp.class.getResource("/styles/tictactoe.css").toExternalForm()); 

        stage.setScene(scene);
        stage.setTitle("Tic Tac Toe");

        
        stage.setResizable(false);
        stage.sizeToScene(); 
    }


    /**
     * Launches the classic board game (full version).
     */
    public static void openClassicGame() {
        BoardGameFactory.createClassicGame();
    }

    /**
     * Launches the mini version of the board game.
     */
    public static void openMiniGame() {
        BoardGameFactory.createMiniGame();
    }

}
