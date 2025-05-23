package edu.ntnu.boardgame.observer;

import edu.ntnu.boardgame.constructors.Player;

/**
 * A test implementation of the {@link BoardGameObserver} interface.
 * <p>
 * This class is primarily used for testing or debugging. It sets flags when
 * `onPlayerMove` and `onGameWon` are called, and prints relevant messages.
 */
public class TestObserver implements BoardGameObserver{
  /**
   * Flag indicating whether onPlayerMove has been called.
   */
  public boolean playerMovedCalled = false;

  /**
   * Flag indicating whether onGameWon has been called.
   */
  public boolean gameWonCalled = false;

  /**
   * Called when a player moves during the game. Sets
   * {@code playerMovedCalled} to true and prints a message.
   *
   * @param player the player who moved
   */
  @Override
  public void onPlayerMove(Player player) {
    playerMovedCalled = true;
    System.out.println("Observer: " + player.getName() + " moved.");
  }

  /**
   * Called when a player wins the game. Sets {@code gameWonCalled} to true
   * and prints a message.
   *
   * @param winner the player who won the game
   */
  @Override
  public void onGameWon(Player winner) {
    gameWonCalled = true;
    System.out.println("Observer: " + winner.getName() + " won the game!");
  }
}
