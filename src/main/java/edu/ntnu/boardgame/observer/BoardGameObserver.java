package edu.ntnu.boardgame.observer;

import edu.ntnu.boardgame.constructors.Player;


/*
    * Interface for observing the board game
 */
public interface BoardGameObserver {

    void onPlayerMove(Player player);
    void onGameWon(Player winner);
}
