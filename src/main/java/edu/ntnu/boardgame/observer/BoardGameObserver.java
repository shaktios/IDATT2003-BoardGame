package edu.ntnu.boardgame.observer;

import edu.ntnu.boardgame.Player;


public interface BoardGameObserver {

    void onPlayerMove(Player player);
    void onGameWon(Player winner);
}
