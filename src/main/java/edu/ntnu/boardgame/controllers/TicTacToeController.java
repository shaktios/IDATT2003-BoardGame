package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.BoardgameApp;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.TicTacToe;
import edu.ntnu.boardgame.view.tictactoegame.TicTacToeGameScreen;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controller for handling Tic Tac Toe game logic and interactions between view and model.
 */
public class TicTacToeController {

    private final Player playerX;
    private final Player playerO;
    private final TicTacToe logic;
    private final TicTacToeGameScreen view;

    /**
     * Constructs the controller.
     *
     * @param playerX the first player
     * @param playerO the second player
     * @param view the view (screen) associated with the game
     */
    public TicTacToeController(Player playerX, Player playerO, TicTacToeGameScreen view) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.logic = new TicTacToe(playerX, playerO);
        this.view = view;

        playerX.setToken("X");
        playerO.setToken("O");

        view.updateMessage("Tur: " + logic.getCurrentPlayer().getName() + " (" + logic.getCurrentPlayer().getToken() + ")");
    }

    /**
     * Handles when a player clicks on a tile at a given row and column.
     *
     * @param row the row index (0–2)
     * @param col the column index (0–2)
     * @param stage the JavaFX stage (for showing end screen)
     */
    public void handleMove(int row, int col, Stage stage) {
        if (!logic.makeMove(row, col)) {
            view.updateMessage("Ugyldig trekk – feltet er opptatt.");
            return;
        }

        view.updateTile(row, col, logic.getCell(row, col));

        String winner = logic.checkWinner();
        if (winner != null) {
            showWinnerMessage(logic.getCurrentPlayer().getName(), stage);
            return;
        }

        if (logic.isBoardFull()) {
            showDrawMessage(stage);
            return;
        }

        logic.switchTurn();
        Player next = logic.getCurrentPlayer();
        view.updateMessage("Tur: " + next.getName() + " (" + next.getToken() + ")");
    }

    /**
     * Resets the game.
     */
    public void resetGame() {
        logic.resetBoard();
        view.clearBoard();
        view.updateMessage("Tur: " + logic.getCurrentPlayer().getName() + " (" + logic.getCurrentPlayer().getToken() + ")");
    }

    private void showWinnerMessage(String winnerName, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vi har en vinner!");
        alert.setHeaderText(winnerName + " har vunnet spillet!");
        alert.setContentText("Du sendes tilbake til startsiden.");
        alert.setOnHidden(e -> {
            Scene startScene = BoardgameApp.createFreshStartScene(stage);
            stage.setScene(startScene);
        });
        alert.show();
    }

    private void showDrawMessage(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uavgjort");
        alert.setHeaderText("Ingen vant – brettet er fullt.");
        alert.setContentText("Du sendes tilbake til startsiden.");
        alert.setOnHidden(e -> {
            Scene startScene = BoardgameApp.createFreshStartScene(stage);
            stage.setScene(startScene);
        });
        alert.show();
    }
}
