package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;

/**
 * A tile action that moves the player backwards to a specified destination
 * tile. Typically used for "snakes" or negative effects in the game.
 */
public class BackAction implements TileAction {

  private final int destination;
  private Boardgame boardgame;

    /**
     * Creates a new BackAction that moves the player to the given destination
     * tile.
     *
     * @param destination the tile number the player should move to
     */
  public BackAction(int destination) {
        this.destination = destination;
    }

    /**
     * Sets the boardgame instance so the action can trigger win checking.
     *
     * @param boardgame the boardgame context
     */
  public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

    /**
     * Moves the player to the destination tile and calls checkForWin if
     * boardgame is set.
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
     * Returns the destination tile number the player will be moved to.
     *
     * @return the destination tile number
     */
  @Override
  public int getDestination() {
        return destination;
    }
}
