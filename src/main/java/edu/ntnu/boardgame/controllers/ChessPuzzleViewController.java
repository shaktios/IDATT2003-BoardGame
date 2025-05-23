package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.model.ChessPuzzleModel;
import edu.ntnu.boardgame.view.laddergame.chesspuzzle.ChessPuzzleView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Controller responsible for coordinating the chess puzzle logic and view. When
 * a player lands on a puzzle tile, this controller is responsible for:
 * selecting a random puzzle, showing it, handling the player's response,
 * updating the game state, and triggering win checks.
 */
public class ChessPuzzleViewController {

  private final Player player;
  private final Board board;
  private final Runnable onPuzzleComplete;
  private final ChessPuzzleModel model;
  private final Boardgame boardgame;
  private ChessPuzzleView view;

  private static final int CORRECT_MOVE_FORWARD = 5;
  private static final int WRONG_MOVE_BACKWARD = 3;

  /**
   * Constructs the ChessPuzzleViewController.
   *
   * @param player the player who triggered the puzzle
   * @param board the game board
   * @param onPuzzleComplete callback to run after the puzzle interaction is
   * finished
   * @param boardgame the current game instance used for win condition checks
   */
  public ChessPuzzleViewController(Player player, Board board, Runnable onPuzzleComplete,
                                   Boardgame boardgame) {
    this.player = player;
    this.board = board;
    this.onPuzzleComplete = onPuzzleComplete;
    this.boardgame = boardgame;
    this.model = new ChessPuzzleModel();
  }

  /**
   * Starts the puzzle interaction by selecting a puzzle, initializing the
   * view, and displaying it to the user.
   */
  public void startPuzzle() {
    model.selectRandomPuzzle();
    String image = model.getSelectedImage();
    String correctMove = model.getCorrectMove();
    var options = model.generateOptions();

    // Opprett View og send inn this::handleUserMove som callback
    this.view = new ChessPuzzleView(image, correctMove, options, this::handleUserMove);
    view.show();
  }

  /**
   * Handles the logic after the player selects a move in the puzzle view.
   * Moves the player forward or backward depending on correctness, displays
   * the result, and closes the puzzle after a short delay.
   *
   * @param selectedMove the move the user selected
   */
  public void handleUserMove(String selectedMove) {
    boolean isCorrect = selectedMove.equals(model.getCorrectMove());

    if (isCorrect) {
      view.showResult("Riktig! Du går fremover " + CORRECT_MOVE_FORWARD + " felt!");
      movePlayer(CORRECT_MOVE_FORWARD);
    } else {
      view.showResult("Feil! Du går bakover " + WRONG_MOVE_BACKWARD + " felt!");
      movePlayer(-WRONG_MOVE_BACKWARD);
    }

    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
    pause.setOnFinished(e -> {
      onPuzzleComplete.run();
      view.closeStage();
    });
    pause.play();
  }

  /**
   * Moves the player by the given offset. Makes sure the new position is
   * valid. Updates the tile and checks for win condition.
   *
   * @param offset number of tiles to move (positive or negative)
   */
  private void movePlayer(int offset) {
    int newPosition = Math.max(1, Math.min(player.getPosition() + offset, board.getSize()));
    player.setPosition(newPosition, board);
    player.setCurrentTile(board.getTile(newPosition));
    boardgame.checkForWin(player);
  }
}
