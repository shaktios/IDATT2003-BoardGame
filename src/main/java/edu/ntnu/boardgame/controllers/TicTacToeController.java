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

  private final Player player1;
  private final Player player2;
  private final TicTacToe logic;
  private final TicTacToeGameScreen view;

  /**
   * Constructs the controller.
   *
   * @param player1 the first player
   * @param player2 the second player
   * @param view the view (screen) associated with the game
   */
  public TicTacToeController(Player player1, Player player2, TicTacToeGameScreen view) {
    this.player1 = player1;
    this.player2 = player2;
    this.logic = new TicTacToe(player1, player2);
    this.view = view;

    player1.setToken("X");
    player2.setToken("O");

    // Assign user-friendly names if not already set
    if (player1.getName() == null || player1.getName().isEmpty()) player1.setName("Spiller 1");
    if (player2.getName() == null || player2.getName().isEmpty()) player2.setName("Spiller 2");

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
