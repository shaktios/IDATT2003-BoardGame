package edu.ntnu.boardgame.actions.puzzleactions;

import edu.ntnu.boardgame.actions.tileactions.TileAction;
import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.controllers.ChessPuzzleViewController;

/**
 * A tile action that triggers a chess puzzle interface when the player lands on
 * the tile. If the puzzle is solved, the provided callback is executed and the
 * game checks for a win.
 */
public class ChessPuzzleAction implements PuzzleTileAction, TileAction {

  private Boardgame boardgame;

    /**
     * Constructs a ChessPuzzleAction linked to the main Boardgame.
     *
     * @param boardgame the Boardgame instance used to check for win after
     * puzzle is completed
     */
  public ChessPuzzleAction(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

    /**
     * Executes the chess puzzle view without any specific callback. This
     * overload simply launches the puzzle screen.
     *
     * @param player the player who landed on the tile
     * @param board the game board
     */
  @Override
  public void execute(Player player, Board board) {
    execute(player, board, () -> {
      });
  }

    /**
     * Executes the chess puzzle view and runs a callback once the puzzle is
     * solved. After completion, the game checks if the player has won.
     *
     * @param player the player who landed on the tile
     * @param board the game board
     * @param onPuzzleComplete a callback to run after the puzzle is solved
     */
  @Override
  public void execute(Player player, Board board, Runnable onPuzzleComplete) {
    Runnable callback = () -> {
      onPuzzleComplete.run();
      boardgame.checkForWin(player);
    };
    ChessPuzzleViewController controller = new ChessPuzzleViewController(
      player,
      board,
      callback,
      boardgame
      );
    controller.startPuzzle();
  }

    /**
     * Returns -1 because this action does not result in a fixed movement.
     *
     * @return -1
     */
  @Override
  public int getDestination() {
        return -1;
    }

      /**
     * Sets the Boardgame instance after deserialization.
     *
     * @param boardgame the Boardgame to assign
     */
  public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }
}
