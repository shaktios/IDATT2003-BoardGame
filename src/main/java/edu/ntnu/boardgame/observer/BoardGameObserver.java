package edu.ntnu.boardgame.observer;

import edu.ntnu.boardgame.constructors.Player;

/**
 * Interface for observing key events in a board game. Implementations of this
 * interface can be registered to listen for player movement and game win
 * conditions.
 */
public interface BoardGameObserver {

  /**
   * Called whenever a player moves to a new tile on the board.
   *
   * @param player the player who just moved
   */
  void onPlayerMove(Player player);
    
  /**
   * Called when a player wins the game by reaching the final tile.
   *
   * @param winner the player who won the game
   */
  void onGameWon(Player winner);
}
