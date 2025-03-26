package edu.ntnu.boardgame;


import java.util.ArrayList;
import java.util.List;

import edu.ntnu.boardgame.observer.BoardGameObserver;

public class Boardgame {

  private final List<Player> players;
  private final Dice dice;
  private final Board board;
  private final List<BoardGameObserver> observers = new ArrayList<>(); 

  public Boardgame(Board board, int numDice, int sidesPerDie) {
    if (board == null) {
      throw new IllegalArgumentException("Board kan ikke være null");
    }
    if (numDice < 1) {
      throw new IllegalArgumentException("Kan ikke være færre enn 1 terning");
    }
    if (sidesPerDie < 2) {
      throw new IllegalArgumentException("Terningen må ha minst 2 sider");
    }

    this.board = board;
    this.players = new ArrayList<>();
    this.dice = new Dice(sidesPerDie, numDice);
  }

  public void addPlayer(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Navnet kan ikke være tomt eller null.");
    }

    Tile startTile = board.getTile(1); 
    players.add(new Player(name, startTile));
    System.out.println("Spiller " + name + " har blitt lagt til i spillet på felt " + startTile.getPosition());
  }

  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  public Dice getDice() {
    return dice;
  }

    public void registerObserver(BoardGameObserver observer){
      if (observer != null && !observers.contains(observer)){
        observers.add(observer);
      }
    }

    public void notifyPlayerMoved(Player player){
      for(BoardGameObserver observer : observers){
        observer.onPlayerMove(player);
      }
    }

    public void notifyGameWon(Player player){
      for(BoardGameObserver observer : observers){
        observer.onGameWon(player);
      }
    }



  public void playGame() {
    System.out.println("\nThe following players are playing the game:");
    for (Player player : players) {
      System.out.println("Name: " + player.getName());
    }

    boolean gameOver = false;
    int round = 1;

    while (!gameOver) {
      System.out.println(); // Tom linje, bedre lesbarhet
      System.out.println("Round number " + round);

      for (Player player : players) {
        int roll = dice.roll();
        player.move(roll, board);
        notifyPlayerMoved(player);
        System.out.println("Player " + player.getName() + " on tile " + player.getCurrentTile().getPosition());

        if (player.getCurrentTile().getPosition() == board.getSize()) {
          notifyGameWon(player);
          System.out.println("\nAnd the winner is: " + player.getName() + "!");
          gameOver = true;
          break;
        }
      }
      round++;
    }
  }
}
