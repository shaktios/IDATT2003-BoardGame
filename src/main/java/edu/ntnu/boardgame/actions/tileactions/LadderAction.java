package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;


/**
 * A tile action that moves the player forward to a specific destination tile.
 * Used to represent "ladders" in ladder-based board games.
 */
public class LadderAction implements TileAction {

  private final int destination;
  private Boardgame boardgame; 


  /**
   * Constructs a LadderAction that moves the player to a given destination
   * tile.
   *
   * @param destination the tile number to move the player to
   */
  public LadderAction(int destination) {
    this.destination = destination;
  }


  /**
   * Injects a Boardgame instance used for win condition checking after
   * executing the action.
   *
   * @param boardgame the boardgame context
   */
  public void setBoardgame(Boardgame boardgame) {
    this.boardgame = boardgame;
  }


  /**
   * Executes the ladder action by moving the player to the destination tile
   * and optionally checking for game win.
   *
   * @param player the player landing on the tile
   * @param board the game board the player is on
   */
  @Override
  public void execute(Player player, Board board) {
    player.setPosition(destination, board);
    if (boardgame != null) {
      boardgame.checkForWin(player);
    }
  }


  /**
   * Returns the destination tile this action leads to.
   *
   * @return the destination tile number
   */
  @Override
  public int getDestination() {
    return destination;
  }
}
