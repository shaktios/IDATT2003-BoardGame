package edu.ntnu.boardgame.observer;

import edu.ntnu.boardgame.Player;

public class TestObserver implements BoardGameObserver{

    public boolean playerMovedCalled = false;
    public boolean gameWonCalled = false;

    @Override
    public void onPlayerMove(Player player) {
        playerMovedCalled = true;
        System.out.println("Observer: " + player.getName() + " moved.");
    }

    @Override
    public void onGameWon(Player winner) {
        gameWonCalled = true;
        System.out.println("Observer: " + winner.getName() + " won the game!");
    }

}
