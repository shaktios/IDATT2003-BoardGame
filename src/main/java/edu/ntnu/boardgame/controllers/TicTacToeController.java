package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.TicTacToe;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.view.tictactoegame.TicTacToeGameScreen;
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

    view.updateMessage("Tur: " + logic.getCurrentPlayer().getName()
                + " (" + logic.getCurrentPlayer().getToken() + ")");
  }

  /**
   * Handles when a player clicks on a specific tile. Checks if the move is
   * valid, updates the view, and checks for win/draw.
   *
   * @param tile the tile that was clicked
   */
  public void handleMove(Tile tile) {
    int position = tile.getPosition(); // 1–9
    int row = (position - 1) / 3;
    int col = (position - 1) % 3;

    if (!logic.makeMove(row, col)) {
      view.updateMessage("Ugyldig trekk - feltet er opptatt.");
      return;
    }

    view.updateTile(tile, logic.getCell(row, col));

    String winner = logic.checkWinner();
    if (winner != null) {
      showWinnerMessage(logic.getCurrentPlayer().getName(), null); // stage not used
      return;
    }

    if (logic.isBoardFull()) {
      showDrawMessage(null);
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
    view.updateMessage("Tur: " + logic.getCurrentPlayer().getName()
            + " (" + logic.getCurrentPlayer().getToken() + ")");
  }

  /**
   * Displays a popup alert indicating that a player has won the game.
   *
   * @param winnerName the name of the winning player
   * @param stage unused, kept for compatibility
   */
  private void showWinnerMessage(String winnerName, Stage stage) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Vi har en vinner!");
    alert.setHeaderText(winnerName + " har vunnet spillet!");
    alert.setContentText("Trykk OK for å fortsette.");
    alert.show();
  }

  
  /**
   * Displays a popup alert indicating that the game ended in a draw.
   *
   * @param stage the current JavaFX stage
   *              (not used in this implementation but kept for consistency)
   */
  private void showDrawMessage(Stage stage) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Uavgjort");
    alert.setHeaderText("Ingen vant – brettet er fullt.");
    alert.setContentText("Trykk OK for å fortsette.");
    alert.show();
  }
}
