package edu.ntnu.boardgame;


import java.util.ArrayList;
import java.util.List;

/*
    * A class representing a board game.
    * The class has a constructor that takes a board, the number of dice and the number of sides per die as arguments.
    * The class has a method that adds a player to the game.
    * The class has a method that returns the players in the game.
    * The class has a method that returns the dice in the game.
    * The class has a method that plays the game.
 */
public class Boardgame {

  private final List<Player> players;
  private final Dice dice;
  private final Board board;

  /*
    * Constructor for the Boardgame class.
    * @param board The board in the game.
    * @param numDice The number of dice in the game.
    * @param sidesPerDie The number of sides per die.
    * @throws IllegalArgumentException if the board is null, the number of dice is less than 1 or the number of sides per die is less than 2.
   */
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

  /*
    * Adds a player to the game.
    * @param name The name of the player.
    * @throws IllegalArgumentException if the name is null or empty.
   */
  public void addPlayer(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Navnet kan ikke være tomt eller null.");
    }

    Tile startTile = board.getTile(1); 
    players.add(new Player(name, startTile));
    System.out.println("Spiller " + name + " har blitt lagt til i spillet på felt " + startTile.getPosition());
  }

  /*
    * Returns the players in the game.
    * @return The players in the game.
   */
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  /*
    * Returns the dice in the game.
    * @return The dice in the game.
   */
  public Dice getDice() {
    return dice;
  }

  /*
    * Plays the game.
   */
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
        System.out.println("Player " + player.getName() + " on tile " + player.getCurrentTile().getPosition());

        if (player.getCurrentTile().getPosition() == board.getSize()) {
          System.out.println("\nAnd the winner is: " + player.getName() + "!");
          gameOver = true;
          break;
        }
      }
      round++;
    }
  }
}
