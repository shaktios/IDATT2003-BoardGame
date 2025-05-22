package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;


/**
 * A tile action that teleports the player to a random tile on the board.
 */
public class TeleportRandomAction implements TileAction {

  private Boardgame boardgame;

    /**
     * Executes the teleport action by moving the player to a random tile
     * between 1 and the total size of the board. Also triggers win check if a
     * boardgame is set.
     *
     * @param player the player who landed on the tile
     * @param board the board used to determine the tile range
     */
  @Override
  public void execute(Player player, Board board) {
    int boardSize = board.getSize();
    int randomDestination = (int) (Math.random() * boardSize) + 1;

    player.setPosition(randomDestination, board);

    if (boardgame != null) {
      boardgame.checkForWin(player);
    }
  }

    /**
     * Returns -1 because this action has no fixed destination.
     *
     * @return -1
     */
  @Override
  public int getDestination() {
        return -1;
    }

    /**
     * Sets the boardgame instance for triggering a win condition after
     * teleporting.
     *
     * @param boardgame the boardgame context
     */
  public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }
}
