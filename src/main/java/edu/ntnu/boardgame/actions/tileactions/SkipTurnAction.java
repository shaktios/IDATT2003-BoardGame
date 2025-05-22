package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Player;

/**
 * A tile action that forces the player to skip their next turn. Does not change
 * the player's position.
 */
public class SkipTurnAction implements TileAction {

    /**
     * Marks the player to skip their next turn. Does not affect board position.
     *
     * @param player the player who landed on the tile
     * @param board the current game board (unused in this action)
     */
  @Override
  public void execute(Player player, Board board) {
    player.setSkipNextTurn(true);  
  }

    /**
     * Returns -1 since this action does not move the player.
     *
     * @return -1
     */
  @Override
  public int getDestination() {
        return -1; 
    }
}
