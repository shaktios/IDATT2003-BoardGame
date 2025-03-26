package edu.ntnu.boardgame.observer;

import edu.ntnu.boardgame.constructors.Player;

/*
    * A class that observes the board game
    * It implements the BoardGameObserver interface
    * It has two methods that are called when a player moves or wins the game
 */
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
