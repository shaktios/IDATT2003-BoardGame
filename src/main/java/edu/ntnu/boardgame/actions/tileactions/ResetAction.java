package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;


/**
 * A tile action that sends the player back to the starting tile (tile 1). Often
 * used as a penalty action in the game.
 */
public class ResetAction implements TileAction {

  private final int destination = 1;  // Startfeltet
  private Boardgame boardgame;

  /**
   * Sets the boardgame instance to allow win condition checking after the
   * action.
   *
   * @param boardgame the boardgame context
   */
  public void setBoardgame(Boardgame boardgame) {
    this.boardgame = boardgame;
  }

  /**
   * Executes the reset action by sending the player back to tile 1 and
   * optionally calling checkForWin if a Boardgame is present.
   *
   * @param player the player affected by the action
   * @param board the game board
   */
  @Override
  public void execute(Player player, Board board) {
    player.setPosition(destination, board);
    if (boardgame != null) {
      boardgame.checkForWin(player);
    }
  }

  /**
   * Returns the fixed destination for this action, which is always tile 1.
   *
   * @return 1
   */
  @Override
  public int getDestination() {
    return destination;
  }
}
